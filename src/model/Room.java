package model;

/**
 * The Room interface defines the Room type. Rooms can hold fixtures, items, and
 * a Monster or a Puzzle, but not both. Rooms also have numbers and passages to
 * other Rooms.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Room extends Picturable {

  /**
   * The getRoomNumber() method is the getter for a room's number.
   *
   * @return int of the room number.
   */
  int getRoomNumber();

  /**
   * The getPassageValue() method is the getter for the value of one of the
   * room's passages.
   *
   * @param direction Directions enum indicating if the passage is NORTH, SOUTH,
   *                  EAST or WEST.
   * @return int of the passage's value; negative for blocked, zero for impassable,
   * and positive for passable.
   */
  int getPassageValue(Directions direction);

  /**
   * The setPassageValue() method is the setter for a passage's value within a room.
   * Negative values can be changed to positive indicating that the passage is now
   * passable, i.e. abs(passageValue).
   *
   * @param direction Directions enum indicating if the passage is NORTH, SOUTH,
   *                  EAST or WEST.
   */
  void setPassageValue(Directions direction);

  /**
   * The getFixture() method is the getter for a Fixture stored in a Map in a Room.
   *
   * @param fixtureName String of fixture's name to return.
   * @return Fixture object.
   */
  Fixture getFixture(String fixtureName);

  /**
   * The getItem() method is the getter for an Item stored in a Map in a Room.
   *
   * @param itemName String of the item's name to return.
   * @return Item object.
   */
  Item getItem(String itemName);

  /**
   * Gets the Enemy effecting the Room environment.
   * @return an instance of an Enemy type or null.
   */
  public Enemy getRoomEnvironmentEffector();

  /**
   * The getMonster() method is the getter for a Room's Monster.
   *
   * @return Monster object.
   */
  Monster getMonster();

  /**
   * The getPuzzle() method is the getter for a Room's Puzzle.
   *
   * @return Puzzle object.
   */
  Puzzle getPuzzle();

  /**
   * The addItem() method adds an item to a Room's Item Map.
   *
   * @param itemName String of item to add to Map.
   * @param item Item object to add.
   */
  void addItem(String itemName, Item item);

  /**
   * The removeItem() method removes an item from a Room's Item Map.
   *
   * @param itemName String of the item's name to be removed.
   * @return Item object that was removed.
   */
  Item removeItem(String itemName);

  /**
   * The getPassageRoom() method gets a Room object using the roomNumber
   * from a service class that holds all Room objects.
   *
   * @param roomNumber int of the room number of the Room to get.
   * @return Room object with the room number.
   */
  Room getPassageRoom(int roomNumber);

  /**
   * The isPathBlocked() method indicates whether there is a path blocker,
   * a monster or puzzle, in the room blocking certain passages.
   *
   * @return boolean indicating if there is a path blocker in the room.
   */
  boolean isPathBlocked();
}
