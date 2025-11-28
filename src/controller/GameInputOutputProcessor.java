package controller;

import java.io.IOException;

/**
 * This class type processes user input and sends output to the View.
 * @param <T> The type of input to be passed to the View through the GameInputOutputProcessor.
 */
public interface GameInputOutputProcessor<T> {

  /**
   * Gets an unparsed message from the User.
   * @return a String or null.
   * @throws IOException If I/O error occurs.
   */
  public String getUserMessage() throws IOException;

  /**
   * The getUserInput() method prompts the user for game actions and
   * accepts user input for game play.
   *
   * @return boolean indicating if the game is over.
   * @throws IOException if there is an error appending to output or receiving
   *                     user input.
   */
  public boolean getUserInput() throws IOException;

  /**
   * The getUserInputCommand() is the getter for a command entered by the user.
   * A command is the first word or character entered by a character in an
   * input String.
   *
   * @return String of user command.
   */
  public String getUserInputCommand();

  /**
   * The getUserInputArgument() method is the getter for the command argument
   * entered by the user. A command argument is any text following the first
   * word and a space in the input String.
   *
   * @return String of user argument.
   */
  public String getUserInputArgument();

  /**
   * Displays message to player.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void messageToPlayer(T data) throws IOException;

  /**
   * Updates the Player's stats.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updatePlayerStats(T data) throws IOException;

  /**
   * Updates the Room's name and description.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateRoom(T data) throws IOException;

  /**
   * Updates the ELements that the Player can examine.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateExaminer(T data) throws IOException;

  /**
   * Updates the Player's inventory.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateInventory(T data) throws IOException;

  /**
   * Updates the player Affector.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updatePlayerAffector(T data) throws IOException;
}
