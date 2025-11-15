package model;

/**
 * The Player interface defines the Player type in an adventure game. Players
 * are the way that users interact with the game and act as their avatar.
 * The Player interface extends the Activatable, Element and Scorable interfaces.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Player extends Activatable, Element, Scorable {

  /**
   * The walk() method moves a player to a new room if passage to that room is
   * allowed or indicates why the player cannot move to a room.
   *
   * @param direction Directions enum of the direction to walk.
   * @return the status of the direction the Player tried to walk to.
   */
  RoomStatus walk(Directions direction);

  /**
   * The setScore() method sets the score of the player.
   *
   * @param score double of score to set.
   */
  void setScore(double score);

  /**
   * The addToScore() method adds the given amount to the player's
   * score.
   *
   * @param score double of score to add.
   */
  void addToScore(double score);

  /**
   * The getHealth method is the getter for the player's health.
   *
   * @return double of player's current health.
   */
  double getHealth();

  /**
   * The addHealth method increases the player's health by the given amount.
   *
   * @param health double of amount to add to player's health.
   */
  void addHealth(double health);

  /**
   * The subtractHealth method decreases the player's health by the given amount.
   *
   * @param health double of amount to subtract from player's health.
   */
  void subtractHealth(double health);

  /**
   * Returns the discrete HealthState of the Player as a HealthStatus enum type.
   * @return a HealthStatus enum type.
   */
  HealthStatus getHealthStatus();

  /**
   * The useItem() method uses an item in order to affect the room the player is
   * in.
   *
   * @param item  String key of item to use in inventory.
   * @param enemy String representing an element to use the item on.
   * @return boolean indicating if item was used.
   */
  UseSuccessful useItem(String item, String enemy);

  /**
   * The takeItem() method takes an item from the room the player is currently in.
   *
   * @param item String of item to add to the player's inventory.
   * @return boolean indicating if item was taken.
   */
  boolean takeItem(String item);

  /**
   * The dropItem() method takes an item out of a player's inventory and drops it
   * in the active room.
   *
   * @param item String of the item to drop from the player's inventory.
   * @return boolean indicating if item was dropped.
   */
  boolean dropItem(String item);

  /**
   * The examine() method gets the description of an element in the player's
   * active room.
   *
   * @param element Fixture or Item in active room to return description of.
   * @return String of Element's description.
   */
  String examine(String element);

  /**
   * The answer() method provides an answer to a puzzle in the player's active
   * room in order to solve it.
   *
   * @param answer String of answer to provide to solve puzzle.
   * @return true if answer solved a Puzzle type, otherwise false.
   */
  boolean answer(String answer);

  /**
   * The getActiveRoom() method gets the player's active room, the room that the
   * player is currently in.
   *
   * @return Room object of the currently active room.
   */
  Room getActiveRoom();

  /**
   * The setActiveRoom() method sets the player's active room when the player is
   * moving to a room other than their active room.
   *
   * @param room Room object to "move" the player to.
   */
  void setActiveRoom(Room room);



}
