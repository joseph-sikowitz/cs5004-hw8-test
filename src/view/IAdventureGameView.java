package view;



import java.io.IOException;
import java.util.List;

import controller.GameInputOutputProcessor;
import controller.GameTextInputOutputProcessor;

/**
 * An Interface the view subsystem of an AdventureGame.
 * @param <T> The type of data to be passed to the view.
 * @param <C> The type of GameInputOutputProcessor that the view will pass data back to.
 */
public interface IAdventureGameView<T, C extends GameInputOutputProcessor> {

  /**
   * Sets the controller class that interprets commands passed in through the view.
   * @param ioProcessor interprets commands.
   */
  void setEventHandler(C ioProcessor);

  /**
   * Returns a command from the User.
   * @return a String for the controller to interpret as a command.
   */
  String getCommand();

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
   * Updates the Player's inventory.
   * @param data the data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateInventory(T data) throws IOException;


  /**
   * Displays data to user and Asks user to input text.
   * @param data data to display.
   */
  void promptPlayer(String data) throws IOException;

  /**
   * Updates the list of Fixtures in the current Room.
   * @param data data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateFixtures(T data) throws IOException;


  /**
   * Updates the list of Items in the current Room.
   * @param data data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateItems(T data) throws IOException;


  /**
   * Displays the title of the game.
   * @param data data to display.
   * @throws IOException If I/O error occurs.
   */
  void updateTitle(String data) throws IOException;

}
