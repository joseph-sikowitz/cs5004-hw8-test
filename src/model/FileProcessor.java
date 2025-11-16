package model;

import java.io.FileReader;
import java.io.IOException;
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
  private Map<String, JsonNode> elementFields;
  private boolean newGame;
  private final Set<String> uniqueElementNames;
  private final StringBuilder warnings;

  private Map<String, Item> items;
  private Map<String, Fixture> fixtures;
  private Map<String, Monster> monsters;
  private Map<String, Puzzle> puzzles;
  private Map<Integer, Room> rooms;

  // constants
  private static final Integer NEW_PLAYER_START = 1;

  /**
   * The FileProcessor constructor initializes the gameFileName.
   *
   * @param gameFileName String of game's file name.
   */
  public FileProcessor(String gameFileName, String playerName) {
    this.gameFileName = gameFileName;
    this.elementFields = new HashMap<>();
    this.newGame = true;
    this.playerName = playerName;

    this.uniqueElementNames = new HashSet<>();
    this.warnings = new StringBuilder();
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
        if (field.getKey().equalsIgnoreCase(JsonFields.ITEMS.getValue())) {
          this.createItems(field.getValue());
        } else if (field.getKey().equalsIgnoreCase(JsonFields.FIXTURES.getValue())) {
          this.createFixtures(field.getValue());
        } else if (field.getKey().equalsIgnoreCase(JsonFields.MONSTERS.getValue())) {
          this.createMonsters(field.getValue());
        } else if (field.getKey().equalsIgnoreCase(JsonFields.PUZZLES.getValue())) {
          this.createPuzzles(field.getValue());
        }
      }
    }

    this.createRooms(this.elementFields.get(JsonFields.ROOMS.getValue()));

    if (!this.newGame) {
      this.createSavedPlayer(this.elementFields.get(JsonFields.PLAYER.getValue()));
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
    this.items = new HashMap<>();

    if (node.isArray()) {
      for (JsonNode item : node) {
        String name = item.get(ItemJsonFields.NAME.getValue()).asText();
        String description = item.get(ItemJsonFields.DESCRIPTION.getValue()).asText();
        double score = item.get(ItemJsonFields.VALUE.getValue()).asDouble();
        double weight = item.get(ItemJsonFields.WEIGHT.getValue()).asDouble();
        String picture = item.get(ItemJsonFields.PICTURE.getValue()).asText();
        int maxUses = item.get(ItemJsonFields.MAX_USES.getValue()).asInt();
        int usesRemaining = item.get(ItemJsonFields.USES_REMAINING.getValue()).asInt();
        String useDescription = item.get(ItemJsonFields.WHEN_USED.getValue()).asText();

        this.items.put(name, new ConcreteItem(name, description, score, weight, picture, maxUses,
                usesRemaining, useDescription));
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
    this.fixtures = new HashMap<>();

    if (node.isArray()) {
      for (JsonNode fixture : node) {
        String name = fixture.get(FixtureJsonFields.NAME.getValue()).asText();
        String description = fixture.get(FixtureJsonFields.DESCRIPTION.getValue()).asText();
        double weight = fixture.get(FixtureJsonFields.WEIGHT.getValue()).asDouble();
        Puzzle puzzle = null;
        String states = fixture.get(FixtureJsonFields.STATES.getValue()).asText();
        String picture = fixture.get(FixtureJsonFields.PICTURE.getValue()).asText();

        this.fixtures.put(name, new ConcreteFixture(name, description, weight, puzzle,
                states, picture));
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
    this.monsters = new HashMap<>();

    if (node.isArray()) {
      for (JsonNode monster : node) {
        String name = monster.get(MonsterJsonFields.NAME.getValue()).asText();
        String description = monster.get(MonsterJsonFields.DESCRIPTION.getValue()).asText();
        boolean active = monster.get(MonsterJsonFields.ACTIVE.getValue()).asBoolean();
        boolean affectsTarget = monster.get(
                MonsterJsonFields.AFFECTS_TARGET.getValue()).asBoolean();
        String target = monster.get(MonsterJsonFields.TARGET.getValue()).asText();
        boolean affectsPlayer = monster.get(
                MonsterJsonFields.AFFECTS_PLAYER.getValue()).asBoolean();
        double score = monster.get(MonsterJsonFields.VALUE.getValue()).asDouble();
        String effects = monster.get(MonsterJsonFields.EFFECTS.getValue()).asText();
        double damage = monster.get(MonsterJsonFields.DAMAGE.getValue()).asDouble();
        String picture = monster.get(MonsterJsonFields.PICTURE.getValue()).asText();
        boolean canAttack = monster.get(MonsterJsonFields.CAN_ATTACK.getValue()).asBoolean();
        String attackDescription = monster.get(MonsterJsonFields.ATTACK.getValue()).asText();

        String solution =  monster.get(MonsterJsonFields.SOLUTION.getValue()).asText();
        Pattern pattern = Pattern.compile("'(.*)'");
        Matcher matcher = pattern.matcher(solution);
        if (matcher.matches()) {
          this.monsters.put(name, new ConcreteMonster(name, description, active,
                  affectsTarget, target, affectsPlayer, matcher.group(1), null,
                  score, effects, damage, picture, canAttack, attackDescription));
        } else {
          this.monsters.put(name, new ConcreteMonster(name, description, active,
                  affectsTarget, target, affectsPlayer, null, solution,
                  score, effects, damage, picture, canAttack, attackDescription));
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
    this.puzzles = new HashMap<>();

    if (node.isArray()) {
      for (JsonNode puzzleData : node) {
        String name =  puzzleData.get(PuzzleJsonFields.NAME.getValue()).asText();
        String description = puzzleData.get(PuzzleJsonFields.DESCRIPTION.getValue()).asText();
        boolean active = puzzleData.get(PuzzleJsonFields.ACTIVE.getValue()).asBoolean();
        boolean affectsTarget = puzzleData.get(
                PuzzleJsonFields.AFFECTS_TARGET.getValue()).asBoolean();
        String target = puzzleData.get(PuzzleJsonFields.TARGET.getValue()).asText();
        boolean affectsPlayer = puzzleData.get(
                PuzzleJsonFields.AFFECTS_PLAYER.getValue()).asBoolean();
        double score = puzzleData.get(PuzzleJsonFields.VALUE.getValue()).asDouble();
        String effect = puzzleData.get(PuzzleJsonFields.EFFECTS.getValue()).asText();
        double damage = 0.0;
        String picture = puzzleData.get(PuzzleJsonFields.PICTURE.getValue()).asText();

        String solution = puzzleData.get(PuzzleJsonFields.SOLUTION.getValue()).asText();
        Pattern pattern = Pattern.compile("'(.*)'");
        Matcher matcher = pattern.matcher(solution);
        if (matcher.matches()) {
          this.puzzles.put(name, new ConcretePuzzle(name, description, active, affectsTarget,
                  target, affectsPlayer, matcher.group(1), null, score, effect,
                  damage, picture));
        } else {
          this.puzzles.put(name, new ConcretePuzzle(name, description, active, affectsTarget,
                  target, affectsPlayer, null, solution, score, effect,
                  damage, picture));
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
    this.rooms = new HashMap<>();

    if (node.isEmpty()) {
      throw new IllegalArgumentException("Rooms data cannot be empty");
    }

    if (node.isArray()) {
      for (JsonNode roomData : node) {
        String name = roomData.get(RoomJsonFields.ROOM_NAME.getValue()).asText();
        String description = roomData.get(RoomJsonFields.DESCRIPTION.getValue()).asText();
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

        String[] items = roomData.get(RoomJsonFields.ITEMS.getValue()).asText().split(",");
        Map<String, Item> roomItems = new HashMap<>();
        for (String item : items) {
          if (this.items.containsKey(item)) {
            roomItems.put(item, this.items.get(item));
          }
        }

        String[] fixtures = roomData.get(RoomJsonFields.FIXTURES.getValue()).asText().split(",");
        Map<String, Fixture> roomFixtures = new HashMap<>();
        for (String fixture : fixtures) {
          if (this.fixtures.containsKey(fixture)) {
            roomFixtures.put(fixture, this.fixtures.get(fixture));
          }
        }

        String monster = roomData.get(RoomJsonFields.MONSTER.getValue()).asText();
        Monster roomMonster = null;
        if (this.monsters.containsKey(monster)) {
          roomMonster = this.monsters.get(monster);
        }

        String puzzle = roomData.get(RoomJsonFields.PUZZLE.getValue()).asText();
        Puzzle roomPuzzle = null;
        if (this.puzzles.containsKey(puzzle)) {
          roomPuzzle = this.puzzles.get(puzzle);
        }

        String picture = roomData.get(RoomJsonFields.PICTURE.getValue()).asText();

        this.rooms.put(roomNumber, new ConcreteRoom(name, description, roomNumber, passages,
                roomItems, roomFixtures, roomMonster, roomPuzzle, picture));
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

    String[] items = node.get(PlayerJsonFields.INVENTORY.getValue()).asText().split(",");
    Map<String, Item> inventory = new HashMap<>();
    for (String item : items) {
      if (this.items.containsKey(item)) {
        inventory.put(item, this.items.get(item));
      }
    }

    int activeRoomNumber = node.get(PlayerJsonFields.ACTIVE_ROOM.getValue()).asInt();

    this.currentPlayer = new ConcretePlayer(name, description, score, health, maxWeight, inventory,
            this.rooms.get(activeRoomNumber));

  }

  /**
   * The createNewPlayer() method instantiates a player object without input data
   * for a new game.
   */
  public void createNewPlayer() {
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

}
