package model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The FileProcessor class takes input from an initial file and loads it into the
 * game model. It also interacts with the user to handle ongoing user input.
 * InputProcessors have a game file name, game data, game name, game version, and
 * a Map of the fields used to initialize the game model's objects.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class FileProcessor {

  private static final String WARNING_PREFIX = "Possible error in game file: ";
  // attributes
  private final String gameFileName;
  private JsonNode gameData;
  private JsonNode player;
  private String name;
  private String version;
  private String playerName;
  private Player currentPlayer;
  private final Map<String, JsonNode> elementFields;
  private boolean newGame;
  private final StringBuilder warnings;

  private Map<String, Item> items;
  private Map<String, Fixture> fixtures;
  private Map<String, Monster> monsters;
  private Map<String, Puzzle> puzzles;
  private Map<Integer, Room> rooms;
  //keeps track of unique names so that Elements cannot be redefined a second time.
  private final Set<String> uniqueElementNames;

  // constants
  private static final Integer NEW_PLAYER_START = 1;
  private static final int MATCH_GROUP = 1;
  private static final String DEFAULT_PLAYER_NAME = "Player 1";

  /**
   * The FileProcessor constructor initializes the gameFileName.
   *
   * @param gameFileName String of game's file name.
   * @param playerName  String representing Player's name.
   */
  public FileProcessor(String gameFileName, String playerName) throws IllegalArgumentException {
    this.gameFileName = gameFileName;
    this.elementFields = new HashMap<>();
    this.newGame = true;
    this.playerName = playerName;
    this.warnings = new StringBuilder();
    this.uniqueElementNames = new HashSet<>();
    this.items = new HashMap<>();
    this.fixtures = new HashMap<>();
    this.monsters = new HashMap<>();
    this.puzzles = new HashMap<>();
    this.rooms = new HashMap<>();
  }

  /**
   * The FileProcessor constructor initializes the gameFileName with a default player name
   * of Player 1.
   * @param gameFileName String of game's file name.
   */
  public FileProcessor(String gameFileName) throws IllegalArgumentException {
    this(gameFileName, DEFAULT_PLAYER_NAME);
  }

  /**
   * The getter for the game's file name.
   *
   * @return String of the game's file name.
   */
  public String getGameFileName() {
    return this.gameFileName;
  }

  /**
   * The setUpGame() method drives the game set up by taking in an input file
   * and processing it.
   *
   * @return boolean indicating if this is a new game.
   */
  public Player setUpGame() {
    this.ingestGameFile();
    this.processGameFile();
    this.buildElements();

    return this.currentPlayer;
  }

  /**
   * The ingestGameFile() method uses the gameFileName to build objects from the
   * game's model.
   */
  private void ingestGameFile() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      gameData = mapper.readTree(new FileReader(this.getGameFileName()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * The processGameFile() method takes the starting input file and parses
   * its JSON into variables and arrays.
   */
  private void processGameFile() {
    Iterator<String> keys = this.gameData.fieldNames();

    while (keys.hasNext()) {
      String currentKey = keys.next();
      if (currentKey.equalsIgnoreCase(JsonFields.NAME.getValue())) {
        this.name = this.gameData.get(currentKey).asText();
      } else if (currentKey.equalsIgnoreCase(JsonFields.VERSION.getValue())) {
        this.version = this.gameData.get(currentKey).asText();
      } else if (currentKey.equalsIgnoreCase(JsonFields.PLAYER.getValue())) {
        this.player = this.gameData.get(currentKey);
        this.newGame = false;
      } else {
        if (this.gameData.get(currentKey).isArray()) {
          this.elementFields.put(currentKey, this.gameData.get(currentKey));
        }
      }
    }
  }

  /**
   * The buildElements() method builds instantiates the game's objects from the
   * JSON input data. It builds the items, fixtures, puzzles and monsters first,
   * so that they can be checked for existence when the rooms are created that
   * they are supposed to exist in. Finally, a player is created from the input
   * data if the game is being restored from a save file or a new player is created
   * if it is a new game.
   */
  private void buildElements() {
    for (Map.Entry<String, JsonNode> field : this.elementFields.entrySet()) {
      if (field.getValue().isArray()) {
        try {
          if (field.getKey().equalsIgnoreCase(JsonFields.ITEMS.getValue())) {
            this.createItems(field.getValue());
          } else if (field.getKey().equalsIgnoreCase(JsonFields.FIXTURES.getValue())) {
            this.createFixtures(field.getValue());
          } else if (field.getKey().equalsIgnoreCase(JsonFields.MONSTERS.getValue())) {
            this.createMonsters(field.getValue());
          } else if (field.getKey().equalsIgnoreCase(JsonFields.PUZZLES.getValue())) {
            this.createPuzzles(field.getValue());
          }
        }  catch (Exception e) {
          this.addGameFileWarning("Error creating an Element: " + e.getMessage());
        }
      }
    }

    this.createRooms(this.elementFields.get(JsonFields.ROOMS.getValue()));

    if (!this.newGame) {
      this.createSavedPlayer(this.player);
    } else {
      this.createNewPlayer();
    }

  }

  /**
   * The createItems() method instantiates item objects from the input data
   * provided in JSON format. The node parameter is a JsonNode array where each
   * array element holds the initialization data for a single item.
   *
   * @param node JsonNode array of item input data.
   */
  private void createItems(JsonNode node) {
    //clear anything already in the Map
    this.items.clear();

    if (node.isArray()) {
      for (JsonNode item : node) {
        String name;
        if (item.get(ItemJsonFields.NAME.getValue()).isNull()) {
          name = null;
        } else {
          name = item.get(ItemJsonFields.NAME.getValue()).asText();
        }

        if (this.uniqueElementNames.add(name)) {
          String description;
          if (item.get(ItemJsonFields.DESCRIPTION.getValue()).isNull()) {
            description = null;
          } else {
            description = item.get(ItemJsonFields.DESCRIPTION.getValue()).asText();
          }

          double score = item.get(ItemJsonFields.VALUE.getValue()).asDouble();
          double weight = item.get(ItemJsonFields.WEIGHT.getValue()).asDouble();

          String picture;
          if (item.get(ItemJsonFields.PICTURE.getValue()) != null) {
            picture = null;
          } else {
            picture = item.get(ItemJsonFields.PICTURE.getValue()).asText();
          }

          int maxUses = item.get(ItemJsonFields.MAX_USES.getValue()).asInt();
          int usesRemaining = item.get(ItemJsonFields.USES_REMAINING.getValue()).asInt();

          String useDescription;
          if (item.get(ItemJsonFields.WHEN_USED.getValue()).isNull()) {
            useDescription = null;
          } else {
            useDescription = item.get(ItemJsonFields.WHEN_USED.getValue()).asText();
          }

          try {
            this.items.put(name.toLowerCase(), new ConcreteItem(name, description, score, weight,
                    picture, maxUses, usesRemaining, useDescription));
          } catch (Exception e) {
            this.addGameFileWarning("Error creating concrete item: " + e.getMessage());
          }
        }


      }
    }
  }

  /**
   * The createFixtures() method instantiates fixture objects from the input data
   * provided in JSON format. The node parameter is a JsonNode array where each
   * array element holds the initialization data for a single fixture.
   *
   * @param node JsonNode array of fixture input data.
   */
  private void createFixtures(JsonNode node) {
    //clear anything already in the Map
    this.fixtures.clear();

    if (node.isArray()) {
      for (JsonNode fixture : node) {
        String name;
        if (fixture.get(ItemJsonFields.NAME.getValue()).isNull()) {
          name = null;
        } else {
          name = fixture.get(FixtureJsonFields.NAME.getValue()).asText();

        }

        if (this.uniqueElementNames.add(name)) {
          String description;
          if (fixture.get(ItemJsonFields.DESCRIPTION.getValue()).isNull()) {
            description = null;
          } else {
            description = fixture.get(FixtureJsonFields.DESCRIPTION.getValue()).asText();
          }

          double weight = fixture.get(FixtureJsonFields.WEIGHT.getValue()).asDouble();

          // these may need to take specific values in a later version of the game
          Puzzle puzzle = null;
          String states = null;

          String picture;
          if (fixture.get(FixtureJsonFields.PICTURE.getValue()).isNull()) {
            picture = null;
          } else {
            picture = fixture.get(FixtureJsonFields.PICTURE.getValue()).asText();
          }

          try {
            this.fixtures.put(name.toLowerCase(), new ConcreteFixture(name, description, weight,
                    puzzle, states, picture));
          } catch (Exception e) {
            this.addGameFileWarning("Error creating fixture: " + e.getMessage());
          }
        }

      }
    }
  }

  /**
   * The createMonsters() method instantiates monster objects from the input data
   * provided in JSON format. The node parameter is a JsonNode array where each
   * array element holds the initialization data for a single monster.
   *
   * @param node JsonNode array of monster input data.
   */
  private void createMonsters(JsonNode node) {
    //clear anything already in the Map
    this.monsters.clear();

    if (node.isArray()) {
      for (JsonNode monster : node) {
        String name;
        if (monster.get(ItemJsonFields.NAME.getValue()).isNull()) {
          name = null;
        } else {
          name = monster.get(MonsterJsonFields.NAME.getValue()).asText();
        }

        if (this.uniqueElementNames.add(name)) {
          String description;
          if (monster.get(ItemJsonFields.DESCRIPTION.getValue()).isNull()) {
            description = null;
          } else {
            description = monster.get(MonsterJsonFields.DESCRIPTION.getValue()).asText();
          }

          boolean active = monster.get(MonsterJsonFields.ACTIVE.getValue()).asBoolean();
          boolean affectsTarget = monster.get(
                  MonsterJsonFields.AFFECTS_TARGET.getValue()).asBoolean();

          String target;
          if (monster.get(MonsterJsonFields.TARGET.getValue()).isNull()) {
            target = null;
          } else {
            target = monster.get(MonsterJsonFields.TARGET.getValue().toLowerCase()).asText();
          }

          boolean affectsPlayer = monster.get(
                  MonsterJsonFields.AFFECTS_PLAYER.getValue()).asBoolean();
          double score = monster.get(MonsterJsonFields.VALUE.getValue()).asDouble();

          String effects;
          if (monster.get(MonsterJsonFields.EFFECTS.getValue()).isNull()) {
            effects = null;
          } else {
            effects = monster.get(MonsterJsonFields.EFFECTS.getValue()).asText();
          }

          double damage = monster.get(MonsterJsonFields.DAMAGE.getValue()).asDouble();

          String picture;
          if (monster.get(MonsterJsonFields.PICTURE.getValue()).isNull()) {
            picture = null;
          } else {
            picture = monster.get(MonsterJsonFields.PICTURE.getValue()).asText();
          }

          boolean canAttack = monster.get(MonsterJsonFields.CAN_ATTACK.getValue()).asBoolean();

          String attackDescription;
          if (monster.get(MonsterJsonFields.ATTACK.getValue()).isNull()) {
            attackDescription = null;
          } else {
            attackDescription = monster.get(MonsterJsonFields.ATTACK.getValue()).asText();
          }

          try {
            String solution;
            if (monster.get(MonsterJsonFields.SOLUTION.getValue()).isNull()) {
              this.monsters.put(name.toLowerCase(), new ConcreteMonster(name, description, active,
                      affectsTarget, target, affectsPlayer, null, null,
                      score, effects, damage, picture, canAttack, attackDescription));
            } else {
              solution = monster.get(MonsterJsonFields.SOLUTION.getValue()).asText();
              Pattern pattern = Pattern.compile("'(.*)'");
              Matcher matcher = pattern.matcher(solution);
              if (matcher.matches()) {
                this.monsters.put(name.toLowerCase(), new ConcreteMonster(name, description, active,
                        affectsTarget, target, affectsPlayer, matcher.group(MATCH_GROUP), null,
                        score, effects, damage, picture, canAttack, attackDescription));
              } else {
                this.monsters.put(name.toLowerCase(), new ConcreteMonster(name, description, active,
                        affectsTarget, target, affectsPlayer, null, solution,
                        score, effects, damage, picture, canAttack, attackDescription));
              }
            }
          } catch (Exception e) {
            this.addGameFileWarning("Error creating monster: " + e.getMessage());
          }
        }
      }
    }
  }

  /**
   * The createPuzzles() method instantiates puzzle objects from the input data
   * provided in JSON format. The node parameter is a JsonNode array where each
   * array element holds the initialization data for a single puzzle.
   *
   * @param node JsonNode array of puzzle input data.
   */
  private void createPuzzles(JsonNode node) {
    //clear anything already in the Map
    this.puzzles.clear();

    if (node.isArray()) {
      for (JsonNode puzzleData : node) {
        String name;
        if (puzzleData.get(ItemJsonFields.NAME.getValue()).isNull()) {
          name = null;
        } else {
          name = puzzleData.get(PuzzleJsonFields.NAME.getValue()).asText();

        }

        if (this.uniqueElementNames.add(name)) {
          String description;
          if (puzzleData.get(ItemJsonFields.DESCRIPTION.getValue()).isNull()) {
            description = null;
          } else {
            description = puzzleData.get(PuzzleJsonFields.DESCRIPTION.getValue()).asText();
          }

          boolean active = puzzleData.get(PuzzleJsonFields.ACTIVE.getValue()).asBoolean();
          boolean affectsTarget = puzzleData.get(
                  PuzzleJsonFields.AFFECTS_TARGET.getValue()).asBoolean();

          String target;
          if (puzzleData.get(PuzzleJsonFields.TARGET.getValue()).isNull()) {
            target = null;
          } else {
            target = puzzleData.get(PuzzleJsonFields.TARGET.getValue().toLowerCase()).asText();
          }

          boolean affectsPlayer = puzzleData.get(
                  PuzzleJsonFields.AFFECTS_PLAYER.getValue()).asBoolean();
          double score = puzzleData.get(PuzzleJsonFields.VALUE.getValue()).asDouble();

          String effect;
          if (puzzleData.get(PuzzleJsonFields.EFFECTS.getValue()).isNull()) {
            effect = null;
          } else {
            effect = puzzleData.get(PuzzleJsonFields.EFFECTS.getValue()).asText();
          }

          double damage = 0.0;

          String picture;
          if (puzzleData.get(PuzzleJsonFields.PICTURE.getValue()).isNull()) {
            picture = null;
          } else {
            picture = puzzleData.get(PuzzleJsonFields.PICTURE.getValue()).asText();
          }

          try {
            String solution;
            if (puzzleData.get(PuzzleJsonFields.SOLUTION.getValue()).isNull()) {
              this.puzzles.put(name.toLowerCase(), new ConcretePuzzle(name, description, active,
                      affectsTarget, target, affectsPlayer, null, null, score,
                      effect, damage, picture));
            } else {
              solution = puzzleData.get(PuzzleJsonFields.SOLUTION.getValue()).asText();
              Pattern pattern = Pattern.compile("'(.*)'");
              Matcher matcher = pattern.matcher(solution);
              if (matcher.matches()) {
                this.puzzles.put(name.toLowerCase(), new ConcretePuzzle(name, description, active,
                        affectsTarget, target, affectsPlayer, matcher.group(MATCH_GROUP),
                        null, score, effect, damage, picture));
              } else {
                this.puzzles.put(name.toLowerCase(), new ConcretePuzzle(name, description, active,
                        affectsTarget, target, affectsPlayer, null, solution, score, effect,
                        damage, picture));
              }
            }
          } catch (Exception e) {
            this.addGameFileWarning("Error creating puzzle: " + e.getMessage());
          }
        }


      }
    }
  }

  /**
   * The createRooms() method instantiates room objects from the input data
   * provided in JSON format. The node parameter is a JsonNode array where each
   * array element holds the initialization data for a single room.
   *
   * @param node JsonNode array of room input data.
   */
  private void createRooms(JsonNode node) throws IllegalArgumentException {
    //clear anything already in the Map
    this.rooms.clear();

    if (node.isEmpty()) {
      throw new IllegalArgumentException("Rooms data cannot be empty");
    }

    if (node.isArray()) {
      for (JsonNode roomData : node) {
        String name;
        if (roomData.get(RoomJsonFields.ROOM_NAME.getValue()).isNull()) {
          name = null;
        } else {
          name = roomData.get(RoomJsonFields.ROOM_NAME.getValue()).asText();
        }

        if (this.uniqueElementNames.add(name)) {
          String description;
          if (roomData.get(RoomJsonFields.DESCRIPTION.getValue()).isNull()) {
            description = null;
          } else {
            description = roomData.get(RoomJsonFields.DESCRIPTION.getValue()).asText();
          }

          Integer roomNumber = roomData.get(RoomJsonFields.ROOM_NUMBER.getValue()).asInt();

          int north = roomData.get(RoomJsonFields.NORTH.getValue()).asInt();
          int south = roomData.get(RoomJsonFields.SOUTH.getValue()).asInt();
          int east = roomData.get(RoomJsonFields.EAST.getValue()).asInt();
          int west = roomData.get(RoomJsonFields.WEST.getValue()).asInt();

          Map<Directions, Integer> passages = new HashMap<>();
          passages.put(Directions.NORTH, north);
          passages.put(Directions.SOUTH, south);
          passages.put(Directions.EAST, east);
          passages.put(Directions.WEST, west);

          String[] itemsArray = roomData.get(RoomJsonFields.ITEMS.getValue()).asText().split(",");
          Map<String, Item> roomItems = new HashMap<>();
          for (String item : itemsArray) {
            if (this.items.containsKey(item.trim().toLowerCase())) {
              roomItems.put(item.trim().toLowerCase(), this.items.get(item.trim().toLowerCase()));
            }
          }

          String[] fixtures = roomData.get(RoomJsonFields.FIXTURES.getValue()).asText().split(",");
          Map<String, Fixture> roomFixtures = new HashMap<>();
          for (String fixture : fixtures) {
            if (this.fixtures.containsKey(fixture.trim().toLowerCase())) {
              roomFixtures.put(fixture.trim().toLowerCase(),
                      this.fixtures.get(fixture.trim().toLowerCase()));
            }
          }

          String monster = roomData.get(RoomJsonFields.MONSTER.getValue().toLowerCase()).asText();
          Monster roomMonster = this.monsters.getOrDefault(monster.toLowerCase(), null);

          String puzzle = roomData.get(RoomJsonFields.PUZZLE.getValue().toLowerCase()).asText();
          Puzzle roomPuzzle;
          roomPuzzle = this.puzzles.getOrDefault(puzzle.toLowerCase(), null);

          String picture;
          if (roomData.get(RoomJsonFields.PICTURE.getValue()).isNull()) {
            picture = null;
          } else {
            picture = roomData.get(RoomJsonFields.PICTURE.getValue()).asText();
          }

          try {
            this.rooms.put(roomNumber, new ConcreteRoom(name, description, roomNumber, passages,
                    roomItems, roomFixtures, roomMonster, roomPuzzle, picture));
          } catch (Exception e) {
            this.addGameFileWarning("Error creating room: " + e.getMessage());
          }
        }
      }
    }
    //After all Rooms have been instantiated, check reflexivity of passages.
    //Apparently not necessary!
    if (!ConcreteRoom.checkReflexivity()) {
      this.addGameFileWarning("One or more passages between Rooms are not reflexive!");
    }
  }

  /**
   * The createSavedPlayer() method instantiates a player object from the input
   * data provided in a JSON format save file. The node parameter is a JsonNode
   * of a single JSON object since there can only be one player.
   *
   * @param node JsonNode object of saved player input data.
   */
  private void createSavedPlayer(JsonNode node) {
    String name =  node.get(PlayerJsonFields.NAME.getValue()).asText();
    String description = node.get(PlayerJsonFields.DESCRIPTION.getValue()).asText();
    double score =   node.get(PlayerJsonFields.SCORE.getValue()).asDouble();
    double health = node.get(PlayerJsonFields.HEALTH.getValue()).asDouble();
    double maxWeight = node.get(PlayerJsonFields.MAX_WEIGHT.getValue()).asDouble();

    String[] itemList = node.get(PlayerJsonFields.INVENTORY.getValue()).asText().split(",");
    Map<String, Item> inventory = new HashMap<>();
    for (String item : itemList) {
      if (this.items.containsKey(item.trim().toLowerCase())) {
        inventory.put(item.trim().toLowerCase(), this.items.get(item.trim().toLowerCase()));
      }
    }

    int activeRoomNumber = node.get(PlayerJsonFields.ACTIVE_ROOM.getValue()).asInt();

    String[] itemsAdded = node.get(PlayerJsonFields.ITEMS_ADDED.getValue()) != null
            ? node.get(PlayerJsonFields.ITEMS_ADDED.getValue()).asText().split(",") : new String[0];
    Set<String> itemsToAdd = new HashSet<>();
    for (String added : itemsAdded) {
      itemsToAdd.add(added.trim().toLowerCase());
    }

    this.currentPlayer = new ConcretePlayer(name, description, score, health, maxWeight, inventory,
            this.rooms.get(activeRoomNumber), itemsToAdd);
  }

  /**
   * The createNewPlayer() method instantiates a player object without input data
   * for a new game.
   */
  private void createNewPlayer() {
    Map<String, Item> inventory = new HashMap<>();
    this.currentPlayer = new ConcretePlayer(
            this.playerName, inventory, this.rooms.get(NEW_PLAYER_START));
  }

  /**
   * Adds a warning when initializing Elements within the model from the data file.
   * @param message a possible warning to inform to the player about.
   */
  private void addGameFileWarning(String message) {
    this.warnings.append(WARNING_PREFIX).append(message).append("\n");
  }

  /**
   * Returns a String with all warnings accumulating from initializing Elements
   *     within the model from the data file.
   * @return a String with all warnings accumulating from initializing Elements
   *            within the model from the data file.
   */
  public String getGameFileWarnings() {
    return this.warnings.toString();
  }

  /**
   * The saveFile() method saves the current state of the game as a JSON file in the
   * file given as an argument. The JSON format is similar to an input file for a
   * new game, but adds a player element so that the player can be restored in their
   * saved state.
   *
   * @param saveFile String of file name and path to save to.
   * @throws IOException if file cannot be written to.
   */
  protected void saveGame(String saveFile) throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> allElements = new HashMap<>();
    allElements.put(JsonFields.NAME.getValue(), this.name);
    allElements.put(JsonFields.VERSION.getValue(), this.version);

    allElements.put(JsonFields.ITEMS.getValue(), formatItemsForJson());
    allElements.put(JsonFields.FIXTURES.getValue(), formatFixturesForJson());
    allElements.put(JsonFields.MONSTERS.getValue(), formatMonstersForJson());
    allElements.put(JsonFields.PUZZLES.getValue(), formatPuzzlesForJson());
    allElements.put(JsonFields.ROOMS.getValue(), formatRoomsForJson());

    allElements.put(JsonFields.PLAYER.getValue(), formatPlayerForJson());

    mapper.writeValue(new File(saveFile), allElements);
  }

  /**
   * The formatItemsForJson() method takes the game's items and puts them into an
   * ArrayList so that they can be converted to JSON for the save file.
   *
   * @return ArrayList of item Maps.
   */
  private ArrayList<Map<String, String>> formatItemsForJson() {
    ArrayList<Map<String, String>> itemList = new ArrayList<>();

    for (String keyName : this.items.keySet()) {
      Map<String, String> itemMap = new HashMap<>();

      itemMap.put(ItemJsonFields.NAME.getValue(), this.items.get(keyName).getName());
      itemMap.put(ItemJsonFields.DESCRIPTION.getValue(), this.items.get(keyName).getDescription());
      itemMap.put(ItemJsonFields.VALUE.getValue(), Double.toString(
              this.items.get(keyName).getScore()));
      itemMap.put(ItemJsonFields.WEIGHT.getValue(), Double.toString(
              this.items.get(keyName).getWeight()));

      itemMap.put(ItemJsonFields.PICTURE.getValue(), this.items.get(keyName).getPicturePath());
      itemMap.put(ItemJsonFields.MAX_USES.getValue(), Integer.toString(
              this.items.get(keyName).getMaxUses()));
      itemMap.put(ItemJsonFields.USES_REMAINING.getValue(), Integer.toString(
              this.items.get(keyName).getUsesRemaining()));
      itemMap.put(ItemJsonFields.WHEN_USED.getValue(), this.items.get(keyName).getUseDescription());

      itemList.add(itemMap);
    }

    return itemList;
  }

  /**
   * The formatFixturesForJson() method takes the game's fixtures and puts them into an
   * ArrayList so that they can be converted to JSON for the save file.
   *
   * @return ArrayList of fixture Maps.
   */
  private ArrayList<Map<String, String>> formatFixturesForJson() {
    ArrayList<Map<String, String>> fixtureList = new ArrayList<>();

    for (String keyName : this.fixtures.keySet()) {
      Map<String, String> fixtureMap = new HashMap<>();

      fixtureMap.put(FixtureJsonFields.NAME.getValue(), this.fixtures.get(keyName).getName());
      fixtureMap.put(FixtureJsonFields.DESCRIPTION.getValue(),
              this.fixtures.get(keyName).getTrueDescription());
      fixtureMap.put(FixtureJsonFields.WEIGHT.getValue(), Double.toString(
              this.fixtures.get(keyName).getWeight()));

      if (this.fixtures.get(keyName).getPuzzle() != null) {
        fixtureMap.put(FixtureJsonFields.PUZZLE.getValue(),
                this.fixtures.get(keyName).getPuzzle().getName().toLowerCase());
      } else {
        fixtureMap.put(FixtureJsonFields.PUZZLE.getValue(), null);
      }

      fixtureMap.put(FixtureJsonFields.STATES.getValue(), this.fixtures.get(keyName).getStates());
      fixtureMap.put(FixtureJsonFields.PICTURE.getValue(),
              this.fixtures.get(keyName).getPicturePath());

      fixtureList.add(fixtureMap);
    }

    return fixtureList;
  }

  /**
   * The formatMonstersForJson() method takes the game's monsters and puts them into an
   * ArrayList so that they can be converted to JSON for the save file.
   *
   * @return ArrayList of monster Maps.
   */
  private ArrayList<Map<String, String>> formatMonstersForJson() {
    ArrayList<Map<String, String>> monsterList = new ArrayList<>();

    for (String keyName : this.monsters.keySet()) {
      Map<String, String> monsterMap = new HashMap<>();

      monsterMap.put(MonsterJsonFields.NAME.getValue(), this.monsters.get(keyName).getName());
      monsterMap.put(MonsterJsonFields.DESCRIPTION.getValue(),
              this.monsters.get(keyName).getDescription());
      monsterMap.put(MonsterJsonFields.ACTIVE.getValue(),
              Boolean.toString(monsters.get(keyName).isActive()));
      monsterMap.put(MonsterJsonFields.AFFECTS_TARGET.getValue(),
              Boolean.toString(this.monsters.get(keyName).affectsTarget()));
      monsterMap.put(MonsterJsonFields.TARGET.getValue(), this.monsters.get(keyName).getTarget());
      monsterMap.put(MonsterJsonFields.AFFECTS_PLAYER.getValue(),
              Boolean.toString(this.monsters.get(keyName).affectsPlayer()));
      monsterMap.put(MonsterJsonFields.SOLUTION.getValue(),
              this.monsters.get(keyName).getSolutionItem());
      monsterMap.put(MonsterJsonFields.VALUE.getValue(),
              Double.toString(this.monsters.get(keyName).getScore()));
      monsterMap.put(MonsterJsonFields.EFFECTS.getValue(), this.monsters.get(keyName).getEffect());
      monsterMap.put(MonsterJsonFields.DAMAGE.getValue(),
              Double.toString(this.monsters.get(keyName).getDamage()));
      monsterMap.put(MonsterJsonFields.PICTURE.getValue(),
              this.monsters.get(keyName).getPicturePath());
      monsterMap.put(MonsterJsonFields.CAN_ATTACK.getValue(),
              Boolean.toString(this.monsters.get(keyName).canAttack()));
      monsterMap.put(MonsterJsonFields.ATTACK.getValue(),
              this.monsters.get(keyName).getAttackDescription());

      monsterList.add(monsterMap);
    }

    return monsterList;
  }

  /**
   * The formatPuzzlesForJson() method takes the game's puzzles and puts them into an
   * ArrayList so that they can be converted to JSON for the save file.
   *
   * @return ArrayList of puzzle Maps.
   */
  private ArrayList<Map<String, String>> formatPuzzlesForJson() {
    ArrayList<Map<String, String>> puzzleList = new ArrayList<>();

    for (String keyName : this.puzzles.keySet()) {
      Map<String, String> puzzleMap = new HashMap<>();

      puzzleMap.put(PuzzleJsonFields.NAME.getValue(), this.puzzles.get(keyName).getName());
      puzzleMap.put(PuzzleJsonFields.DESCRIPTION.getValue(),
              this.puzzles.get(keyName).getDescription());
      puzzleMap.put(PuzzleJsonFields.ACTIVE.getValue(),
              Boolean.toString(this.puzzles.get(keyName).isActive()));
      puzzleMap.put(PuzzleJsonFields.AFFECTS_TARGET.getValue(),
              Boolean.toString(this.puzzles.get(keyName).affectsTarget()));
      puzzleMap.put(PuzzleJsonFields.TARGET.getValue(), this.puzzles.get(keyName).getTarget());
      puzzleMap.put(PuzzleJsonFields.AFFECTS_PLAYER.getValue(),
              Boolean.toString(this.puzzles.get(keyName).affectsPlayer()));

      if (this.puzzles.get(keyName).getSolutionItem() != null) {
        puzzleMap.put(PuzzleJsonFields.SOLUTION.getValue(),
                this.puzzles.get(keyName).getSolutionItem());
      } else {
        puzzleMap.put(PuzzleJsonFields.SOLUTION.getValue(),
                "'" + this.puzzles.get(keyName).getSolutionText() + "'");
      }

      puzzleMap.put(PuzzleJsonFields.VALUE.getValue(),
              Double.toString(this.puzzles.get(keyName).getScore()));
      puzzleMap.put(PuzzleJsonFields.EFFECTS.getValue(), this.puzzles.get(keyName).getEffect());
      puzzleMap.put(PuzzleJsonFields.PICTURE.getValue(),
              this.puzzles.get(keyName).getPicturePath());

      puzzleList.add(puzzleMap);
    }

    return puzzleList;
  }
  /**
   * The formatRoomsForJson() method takes the game's rooms and puts them into an
   * ArrayList so that they can be converted to JSON for the save file.
   *
   * @return ArrayList of room Maps.
   */

  private ArrayList<Map<String, String>> formatRoomsForJson() {
    ArrayList<Map<String, String>> roomsList = new ArrayList<>();

    for (Integer keyNumber : this.rooms.keySet()) {
      Map<String, String> roomMap = new HashMap<>();

      roomMap.put(RoomJsonFields.ROOM_NAME.getValue(), this.rooms.get(keyNumber).getName());
      roomMap.put(RoomJsonFields.DESCRIPTION.getValue(),
              this.rooms.get(keyNumber).getTrueDescription());
      roomMap.put(RoomJsonFields.ROOM_NUMBER.getValue(),
              Integer.toString(this.rooms.get(keyNumber).getRoomNumber()));

      Map<Directions, Integer> passages = this.rooms.get(keyNumber).getPassages();
      roomMap.put(RoomJsonFields.NORTH.getValue(),
              Integer.toString(passages.get(Directions.NORTH)));
      roomMap.put(RoomJsonFields.SOUTH.getValue(),
              Integer.toString(passages.get(Directions.SOUTH)));
      roomMap.put(RoomJsonFields.EAST.getValue(),
              Integer.toString(passages.get(Directions.EAST)));
      roomMap.put(RoomJsonFields.WEST.getValue(),
              Integer.toString(passages.get(Directions.WEST)));

      ArrayList<String> itemNames = new ArrayList<>();
      for (Map.Entry<String, Item> entry : this.rooms.get(keyNumber).getItems().entrySet()) {
        itemNames.add(entry.getValue().getName());
      }
      String itemString = String.join(", ", itemNames);
      if (itemString.isEmpty()) {
        roomMap.put(RoomJsonFields.ITEMS.getValue(), null);
      } else {
        roomMap.put(RoomJsonFields.ITEMS.getValue(), itemString);
      }

      ArrayList<String> fixtureNames = new ArrayList<>();
      for (Map.Entry<String, Fixture> entry : this.rooms.get(keyNumber).getFixtures().entrySet()) {
        fixtureNames.add(entry.getValue().getName());
      }
      String fixtureString = String.join(", ", fixtureNames);
      if (fixtureString.isEmpty()) {
        roomMap.put(RoomJsonFields.FIXTURES.getValue(), null);
      } else {
        roomMap.put(RoomJsonFields.FIXTURES.getValue(), fixtureString);
      }

      if (this.rooms.get(keyNumber).getMonster() == null) {
        roomMap.put(RoomJsonFields.MONSTER.getValue(), null);
      } else {
        roomMap.put(RoomJsonFields.MONSTER.getValue(),
                this.rooms.get(keyNumber).getMonster().getName());
      }

      if (this.rooms.get(keyNumber).getPuzzle() == null) {
        roomMap.put(RoomJsonFields.PUZZLE.getValue(), null);
      } else {
        roomMap.put(RoomJsonFields.PUZZLE.getValue(),
                this.rooms.get(keyNumber).getPuzzle().getName());
      }

      roomMap.put(RoomJsonFields.PICTURE.getValue(), this.rooms.get(keyNumber).getPicturePath());

      roomsList.add(roomMap);
    }

    return roomsList;
  }

  /**
   * The formatPlayerForJson() method takes the game's player and puts it into a
   * Map so that it can be converted to JSON for the save file.
   *
   * @return Map of player's state.
   */
  private Map<String, String> formatPlayerForJson() {
    Map<String, String> playerMap = new HashMap<>();

    playerMap.put(PlayerJsonFields.NAME.getValue(), this.currentPlayer.getName());
    playerMap.put(PlayerJsonFields.DESCRIPTION.getValue(), this.currentPlayer.getDescription());
    playerMap.put(PlayerJsonFields.SCORE.getValue(),
            Double.toString(this.currentPlayer.getScore()));
    playerMap.put(PlayerJsonFields.HEALTH.getValue(),
            Double.toString(this.currentPlayer.getHealth()));
    playerMap.put(PlayerJsonFields.MAX_WEIGHT.getValue(),
            Double.toString(this.currentPlayer.getMaxWeight()));

    ArrayList<String> itemNames = new ArrayList<>();
    for (Map.Entry<String, Item> entry : this.currentPlayer.getInventory().entrySet()) {
      itemNames.add(entry.getKey());
    }
    String itemString = String.join(", ", itemNames);
    if (itemString.isEmpty()) {
      playerMap.put(PlayerJsonFields.INVENTORY.getValue(), null);
    } else {
      playerMap.put(PlayerJsonFields.INVENTORY.getValue(), itemString);
    }

    playerMap.put(PlayerJsonFields.ACTIVE_ROOM.getValue(),
            Integer.toString(this.currentPlayer.getActiveRoom().getRoomNumber()));

    if (this.currentPlayer.getItemsAdded() == null
            || this.currentPlayer.getItemsAdded().isEmpty()) {
      playerMap.put(PlayerJsonFields.ITEMS_ADDED.getValue(), null);
    } else {
      String itemsAdded  = String.join(", ", this.currentPlayer.getItemsAdded());
      playerMap.put(PlayerJsonFields.ITEMS_ADDED.getValue(), itemsAdded);
    }

    return playerMap;
  }

}
