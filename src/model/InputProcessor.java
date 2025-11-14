package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The InputProcessor class takes input from an initial file and loads it into the
 * game model. It also interacts with the user to handle ongoing user input.
 * InputProcessors have a game file name, game data, game name, game version, and
 * a Map of the fields used to initialize the game model's objects.
 *
 * @author Joe Sikowitz
 */
public class InputProcessor {

  // attributes
  private final String gameFileName;
  private JsonNode gameData;
  private JsonNode player;
  private String name;
  private String version;
  private Map<String, JsonNode> elementFields;
  private boolean newGame;

  /**
   * The InputProcessor constructor initializes the gameFileName.
   *
   * @param gameFileName String of game's file name.
   */
  public InputProcessor(String gameFileName, Map<String, JsonNode> fields) {
    this.gameFileName = gameFileName;
    this.elementFields = fields;
    this.newGame = true;
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
  public boolean setUpGame() {
    this.ingestGameFile();
    this.processGameFile();
    this.buildElements();

    return this.newGame;
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
      throw new RuntimeException(e);
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
        // TODO: remove this print statement
        //System.out.println(field.getValue().get(0));
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
    // TODO: instantiate items
  }

  /**
   * The createFixtures() method instantiates fixture objects from the input data
   * provided in JSON format. The node parameter is a JsonNode array where each
   * array element holds the initialization data for a single fixture.
   *
   * @param node JsonNode array of fixture input data.
   */
  private void createFixtures(JsonNode node) {
    // TODO: instantiate fixtures
  }

  /**
   * The createMonsters() method instantiates monster objects from the input data
   * provided in JSON format. The node parameter is a JsonNode array where each
   * array element holds the initialization data for a single monster.
   *
   * @param node JsonNode array of monster input data.
   */
  private void createMonsters(JsonNode node) {
    // TODO: instantiate monsters
  }

  /**
   * The createPuzzles() method instantiates puzzle objects from the input data
   * provided in JSON format. The node parameter is a JsonNode array where each
   * array element holds the initialization data for a single puzzle.
   *
   * @param node JsonNode array of puzzle input data.
   */
  private void createPuzzles(JsonNode node) {
    // TODO: instantiate puzzles
    if (node.isArray()) {
      for (JsonNode puzzleData : node) {
        String name =  puzzleData.get(PuzzleJsonFields.NAME.getValue()).asText();
        String description = puzzleData.get(PuzzleJsonFields.DESCRIPTION.getValue()).asText();
        double score = puzzleData.get(PuzzleJsonFields.VALUE.getValue()).asDouble();
        String picture = puzzleData.get(PuzzleJsonFields.PICTURE.getValue()).asText();
        String answer = puzzleData.get(PuzzleJsonFields.SOLUTION.getValue()).asText();
        String effect = puzzleData.get(PuzzleJsonFields.EFFECTS.getValue()).asText();

        ConcretePuzzle puzzle = new ConcretePuzzle(name, description, score, picture,
                answer, effect, null, null);
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
  private void createRooms(JsonNode node) {
    // TODO: instantiate rooms
    // TODO: throw error if missing data for required element
  }

  /**
   * The createSavedPlayer() method instantiates a player object from the input
   * data provided in a JSON format save file. The node parameter is a JsonNode
   * of a single JSON object since there can only be one player.
   *
   * @param node JsonNode object of saved player input data.
   */
  private void createSavedPlayer(JsonNode node) {
    // TODO: initialize player from saved data
  }

  /**
   * The createNewPlayer() method instantiates a player object without input data
   * for a new game.
   */
  private void createNewPlayer() {
    // TODO: initialize new player
  }

}
