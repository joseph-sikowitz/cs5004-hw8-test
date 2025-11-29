package model;

import java.io.FileNotFoundException;
import java.util.List;

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
   * Returns whether the Player's activeRoom has changed
   * or the activeRoom's environment has changed.
   * @return true if the Player the state of the Player's activeRoom has changed, false otherwise.
   */
  boolean roomChanged();

  /**
   * Returns the name of the Player's activeRoom.
   * @return a String with the name of the Room.
   */
  String getRoomName();

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
   * Concatenates and returns a String with the names of the Items in the Player's inventory.
   *
   * @return a String with the names of the Items in the Player's inventory.
   */
  List<String> checkInventory();

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
  String useItem(String item);

  /**
   * The takeItem() method takes an item from the active room and adds it to the player's
   * inventory.
   *
   * @param item Item to be added to inventory.
   */
  String takeItem(String item);

  /**
   * The dropItem() method drops an item out of the player's inventory and leaves it in the
   * active room.
   *
   * @param item Item to be dropped from inventory.
   */
  String dropItem(String item);

  /**
   * The examine() method gets the description of an element.
   *
   * @param element Name of element to get the description of.
   * @return String description of the element being examined.
   */
  String examine(String element);

  /**
   * The answer() method provides an answer to a puzzle within the active room
   * and returns the result.
   *
   * @param answer String of the puzzle answer.
   * @return String of the description of the solution.
   */
  String answer(String answer);

  /**
   * Returns whether the Player's health status has changed.
   * @return true if Players health status has changed
   */
  boolean changeInPlayerHealthStatus();

  /**
   * Returns the health status of the Player as a String.
   * @return A String representing the health status of the Player
   */
  String getPlayerHealthStatus();


  /**
   * Returns whether the Player's score has changed.
   * @return true if the Player's score has changed, otherwise false.
   */
  boolean changeInPlayerScore();

  /**
   * The getPlayerScore() method returns the player's score
   * from a file.
   *
   * @return double of player's score.
   */
  double getPlayerScore();

  /**
   * The getPlayerScore() method returns the player's score as a String.
   *
   * @return formatting String of player's score.
   */
  String getPlayerScoreFormatted();

  /**
   * Returns whether the Player's rank has changed.
   * @return true if the Player's rank has changed, otherwise false.
   */
  boolean changeInPlayerRank();

  /**
   * The getPlayerRank() method gets a player's rank based on their current
   * score.
   * @return String of the player's rank.
   */
  String getPlayerRank();

  /**
   * Has the Player's activeRoom's roomEnvironmentEffector perform its actions on the Player if any.
   * @return a String describing the effects of the roomEnvironmentEffector on the Player.
   */
  String affectPlayer();

  /**
   * The saveGame() method saves the game to a file.
   *
   * @param saveFile String of file name to save to.
   * @return String of game saved message.
   * @throws FileNotFoundException if file was not created.
   */
  String saveGame(String saveFile) throws FileNotFoundException;

  /**
   * The restoreGame() method restores the last saved game.
   *
   * @param saveFile String of file name to restore.
   * @return String of game restored message.
   * @throws FileNotFoundException if file was not found.
   */
  String restoreGame(String saveFile) throws FileNotFoundException;

  /**
   * Returns a String with all warnings accumulating from initializing Elements
   *     within the model from the data file.
   * @return a String with all warnings accumulating from initializing Elements
   *            within the model from the data file.
   */
  String getGameFileWarnings();

  /**
   * Prints a restore message.
   * @return A String with a restore message for the game.
   */
  String restoreMessage();

  /**
   * Prints an exit message.
   * @return A String with an exit message for the game.
   */
  String quitMessage();

  /**
   * Returns whether the game is over.
   * @return true if the game is over. False, otherwise.
   */
  boolean gameOver();

  /**
   * The getPlayerName() method returns the player's name to be used when restoring
   * from a file.
   *
   * @return String of player's name.
   */
  String getPlayerName();

}
