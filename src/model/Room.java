package model;

import java.util.Map;

/**
 * The Room interface defines the Room type. Rooms can hold fixtures, items, and
 * a Monster or a Puzzle, but not both. Rooms also have numbers and passages to
 * other Rooms. Room extends the Element and Picturable interfaces.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Room extends Element, Picturable, EnvironmentAffectedForPlayer {

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
   * Gets the Puzzle effecting the Room environment.
   * @return an instance of a Puzzle type or null.
   */
  public Puzzle getRoomEnvironmentAffector();

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
   * @param item Item object to add.
   */
  void addItem(Item item);

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
   * @param direction a Directions enum type.
   * @return Room object with the room number.
   */
  Room getPassageRoom(Directions direction);

  /**
   * Returns the Passages with their correlated directions.
   * @return a Map with Directions enum as keys and Integers as values.
   */
  Map<Directions, Integer> getPassages();

  /**
   * Returns the Items within the Room.
   * @return a Map of Items hashed by their names.
   */
  Map<String, Item> getItems();

  /**
   * Returns the Fixtures within the Room.
   * @return a Map of Fixtures hashed by their names.
   */
  Map<String, Fixture> getFixtures();

  /**
   * The isPathBlocked() method indicates whether there is a path blocker,
   * a monster or puzzle, in the room blocking certain passages.
   *
   * @return boolean indicating if there is a path blocker in the room.
   */
  boolean isPathBlocked();


  /**
   * The getTrueDescription() method gets the Room's description regardless
   * if there is an Affector overwriting it in the output.
   *
   * @return String of the Room's true description.
   */
  String getTrueDescription();
}
