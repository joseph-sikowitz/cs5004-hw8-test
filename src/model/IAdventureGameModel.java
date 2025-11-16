package model;

import java.util.Map;

/**
 * Model for the AdventureGame that executes commands from controller on the rest of the model.
 */
public interface IAdventureGameModel {

  /**
   * The setPlayerName() method sets the player name for the game.
   *
   * @param playerName String of player's name.
   */
  void setPlayerName(String playerName);

  /**
   * The loadGameData() method loads the JSON data into the model from the provided
   * game file name.
   */
  void loadGameData();

  /**
   * The movePlayerNorth() method moves the player into the room to the north of the
   * current active room if possible.
   *
   * @return String of room to the north description.
   */
  String movePlayerNorth();

  /**
   * The movePlayerSouth() method moves the player into the room to the south of the
   * current active room if possible.
   *
   * @return String of room to the south description.
   */
  String movePlayerSouth();

  /**
   * The movePlayerEast() method moves the player into the room to the east of the
   * current active room if possible.
   *
   * @return String of room to the east description.
   */
  String movePlayerEast();

  /**
   * The movePlayerWest() method moves the player into the room to the west of the
   * current active room if possible.
   *
   * @return String of room to west description.
   */
  String movePlayerWest();

  /**
   * The checkInventory() method returns the player's inventory to be displayed.
   */
  Map<String, Item> checkInventory();

  /**
   * The lookAround() method returns the description and other details of the active
   * room.
   *
   * @return String of the active room description.
   */
  String lookAround();

  /**
   * The useItem() method uses an item within an active room.
   *
   * @param item Item to be used within the room.
   */
  void useItem(String item);

  /**
   * The takeItem() method takes an item from the active room and adds it to the player's
   * inventory.
   *
   * @param item Item to be added to inventory.
   */
  void takeItem(String item);

  /**
   * The dropItem() method drops an item out of the player's inventory and leaves it in the
   * active room.
   *
   * @param item Item to be dropped from inventory.
   */
  void dropItem(String item);

  /**
   * The examine() method gets the description of an element.
   *
   * @param element Element to get the description of.
   * @return String description of the element being examined.
   */
  String examine(Element element);

  /**
   * The answer() method provides an answer to a puzzle within the active room
   * and returns the result.
   *
   * @param answer String of the puzzle answer.
   * @return String of the description of the solution.
   */
  String answer(String answer);

  /**
   * The saveGame() method saves the game to a file.
   */
  void saveGame();

  /**
   * The restoreGame() method restores the last saved game.
   */
  void restoreGame();


  /**
   * Returns a String with all warnings accumulating from initializing Elements
   *     within the model from the data file.
   * @return a String with all warnings accumulating from initializing Elements
   *            within the model from the data file.
   */
  public String getGameFileWarnings();

}
