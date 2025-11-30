package controller;

import java.io.IOException;
import java.util.List;

import utilities.UserCommands;

/**
 * This class type processes user input and sends output to the View.
 * It functions as an Adaptor Pattern, formatting data to pass to the Controller and to the View.
 */
public interface GameInputOutputProcessor {

  /**
   * Gets an unparsed message from the User.
   * @return a String or null.
   * @throws IOException If I/O error occurs.
   */
  String getUserMessage() throws IOException;

  /**
   * The getUserInput() method prompts the user for game actions and
   * accepts user input for game play.
   *
   * @return boolean indicating if the game is over.
   * @throws IOException if there is an error appending to output or receiving
   *                     user input.
   */
  boolean getUserInput() throws IOException;

  /**
   * The getUserInputCommand() is the getter for a command entered by the user.
   * A command is the first word or character entered by a character in an
   * input String.
   *
   * @return UserCommand corresponding to the String the user entered.
   */
  UserCommands getUserInputCommand();

  /**
   * The getUserInputCommand() is the getter for a command entered by the user.
   * A command is the first word or character entered by a character in an
   * input String.
   *
   * @return String of user command.
   */
  String getRawUserInputCommand();

  /**
   * The getUserInputArgument() method is the getter for the command argument
   * entered by the user. A command argument is any text following the first
   * word and a space in the input String.
   *
   * @return String of user argument.
   */
  String getUserInputArgument();

  /**
   * Displays message to player.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void messageToPlayer(List<String> data) throws IOException;

  /**
   * Displays message to player.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void messageToPlayer(String data) throws IOException;

  /**
   * Updates the Player's stats.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updatePlayerStats(List<String> data) throws IOException;

  /**
   * Updates the Room's name and description.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateRoom(List<String> data) throws IOException;

  /**
   * Updates the Elements that the Player can examine.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateExaminer(List<String> data) throws IOException;

  /**
   * Updates the Player's inventory.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateInventory(List<String> data) throws IOException;

  /**
   * Updates the player Affector.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updatePlayerAffector(List<String> data) throws IOException;

  /**
   * Displays data to user and Asks user to input text.
   * @param data data to display.
   * @throws IOException If I/O error occurs.
   */
  void promptPlayer(String data) throws IOException;
}
