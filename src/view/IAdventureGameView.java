package view;



import controller.GameCommandReader;

//TODO: Create Supertype for GameCommandReader called CommandInterpreter.
//TODO: make generic C able to be any subtype of CommandInterpreter.
/**
 * An Interface the view subsystem of an AdventureGame.
 * @param <T> The type of data to be passed to the view.
 * @param <C> The type of CommandInterpreter that the view will pass data back to.
 */
public interface IAdventureGameView<T, C extends GameCommandReader> {

  /**
   * Sets the controller class that interprets commands passed in through the view.
   * @param commandInterpreter interprets commands.
   */
  void setEventHandler(GameCommandReader commandInterpreter);

  /**
   * Returns a command from the model.
   * @return a String for the controller to interpret as a command.
   */
  String getCommand();

  /**
   * Displays message to player.
   * @param data the data to display.
   */
  void messageToPlayer(T data);

  /**
   * Updates the Player's stats.
   * @param data the data to display.
   */
  void updatePlayerStats(T data);

  /**
   * Updates the Room's name and description.
   * @param data the data to display.
   */
  void updateRoom(T data);

  /**
   * Updates the ELements that the Player can examine.
   * @param data the data to display.
   */
  void updateExaminer(T data);

  /**
   * Updates the Player's inventory.
   * @param data the data to display.
   */
  void updateInventory(T data);

  /**
   * Updates the player Affector.
   * @param data the data to display.
   */
  void updatePlayerAffector(T data);

}
