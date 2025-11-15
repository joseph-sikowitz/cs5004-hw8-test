package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ConcreteRoom class represents a Room in the adventure game that a player
 * enters and interacts with its elements. ConcreteRoom extends AbstractElement
 * and implements the Room interface. ConcreteRooms have a name and description
 * inherited from AbstractElement as well as a roomNumber, passages, items,
 * fixtures, a path blocker, a monster, a puzzle, and a picture path file name.
 * Finally, ConcreteRooms have a RoomService that holds all the Rooms in the
 * game for reference.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class ConcreteRoom extends AbstractElement implements Room {

  // attributes
  private final int roomNumber;
  private final Map<Directions, Integer> passages;
  private final Map<String, Item> items;
  private final Map<String, Fixture> fixtures;
  private final Puzzle roomEnvironmentEffector;
  private final Monster monster;
  private final Puzzle puzzle;
  private final String picture;
  private final RoomService roomService;

  /**
   * The ConcreteRoom constructor initializes its attributes using the parent
   * constructor as well as its own attributes. The roomService attribute is
   * initialized with a Map of room numbers and Rooms. The pathBlocker is set
   * based on whether there is a puzzle or monster present in the room blocking
   * passages.
   *
   * @param name String of room's name.
   * @param description String of room's description.
   * @param roomNumber int of room's number.
   * @param passages Map of passages from room in the four cardinal directions.
   * @param items Map of Item objects in the room with their names.
   * @param fixtures Map of Fixture objects in the room with their names.
   * @param monster Monster object in the room.
   * @param puzzle Puzzle object in the room.
   * @param picture String of picture file name path.
   * @param roomService RoomService object with all game Rooms.
   */
  private ConcreteRoom(String name, String description, int roomNumber,
                      Map<Directions, Integer> passages, Map<String, Item> items,
                      Map<String, Fixture> fixtures, Monster monster, Puzzle puzzle,
                      String picture, List<Room> roomService) {
    super(name, description);

    this.roomNumber = roomNumber;
    this.passages = passages;
    this.items = items;
    this.fixtures = fixtures;
    this.monster = monster;
    this.puzzle = puzzle;
    this.picture = picture;
    this.roomService = new RoomService(roomService);
    this.roomEnvironmentEffector = monster != null ? monster : puzzle;

  }

  /**
   * The ConcreteRoom constructor initializes its attributes using the parent
   * constructor as well as its own attributes. The roomService attribute is
   * initialized with a Map of room numbers and Rooms.
   *
   * @param name String of room's name.
   * @param description String of room's description.
   * @param roomNumber int of room's number.
   * @param passages Map of passages from room in the four cardinal directions.
   * @param items Map of Item objects in the room with their names.
   * @param fixtures Map of Fixture objects in the room with their names.
   * @param monster Monster object in the room.
   * @param picture String of picture file name path.
   * @param roomService RoomService object with all game Rooms.
   */
  public ConcreteRoom(String name, String description, int roomNumber,
               Map<Directions, Integer> passages, Map<String, Item> items,
               Map<String, Fixture> fixtures, Monster monster,
               String picture, List<Room> roomService) {
    this(name, description, roomNumber, passages, items, fixtures, monster, null, picture, roomService);
    if (monster == null) {
      throw new IllegalArgumentException("Monster is null!");
    }
  }

  /**
   * The ConcreteRoom constructor initializes its attributes using the parent
   * constructor as well as its own attributes. The roomService attribute is
   * initialized with a Map of room numbers and Rooms.
   *
   * @param name String of room's name.
   * @param description String of room's description.
   * @param roomNumber int of room's number.
   * @param passages Map of passages from room in the four cardinal directions.
   * @param items Map of Item objects in the room with their names.
   * @param fixtures Map of Fixture objects in the room with their names.
   * @param puzzle Puzzle object in the room.
   * @param picture String of picture file name path.
   * @param roomService RoomService object with all game Rooms.
   */
  public ConcreteRoom(String name, String description, int roomNumber,
                      Map<Directions, Integer> passages, Map<String, Item> items,
                      Map<String, Fixture> fixtures, Puzzle puzzle,
                      String picture, List<Room> roomService) throws IllegalArgumentException  {
    this(name, description, roomNumber, passages, items, fixtures, null, puzzle, picture, roomService);
    if (puzzle == null) {
      throw new IllegalArgumentException("Puzzle is null!");
    }
  }

  /**
   * The ConcreteRoom constructor initializes its attributes using the parent
   * constructor as well as its own attributes. The roomService attribute is
   * initialized with a Map of room numbers and Rooms.
   *
   * @param name String of room's name.
   * @param description String of room's description.
   * @param roomNumber int of room's number.
   * @param passages Map of passages from room in the four cardinal directions.
   * @param items Map of Item objects in the room with their names.
   * @param fixtures Map of Fixture objects in the room with their names.
   * @param picture String of picture file name path.
   * @param roomService RoomService object with all game Rooms.
   */
  public ConcreteRoom(String name, String description, int roomNumber,
                      Map<Directions, Integer> passages, Map<String, Item> items,
                      Map<String, Fixture> fixtures,
                      String picture, List<Room> roomService) {
    this(name, description, roomNumber, passages, items, fixtures, null, null,
            picture, roomService);
  }

  @Override
  public String getDescription() {
    if (this.roomEnvironmentEffector != null && this.roomEnvironmentEffector.isActive())
      return roomEnvironmentEffector.getEffect();
    return super.getDescription();
  }

  @Override
  public int getRoomNumber() {
    return this.roomNumber;
  }

  @Override
  public int getPassageValue(Directions direction) {
    return this.passages.get(direction);
  }

  @Override
  public void setPassageValue(Directions direction) {
    this.passages.put(direction, Math.abs(this.passages.get(direction)));
  }

  @Override
  public Fixture getFixture(String fixtureName) {
    return this.fixtures.get(fixtureName);
  }

  @Override
  public Item getItem(String itemName) {
    return this.items.get(itemName);
  }

  /**
   * Returns a safe copy of items in the Room as a hashMap.
   * @return a HashMap instance containing all items in the Room.
   */
  Map<String, Item> getItems() {
    return new HashMap<>(this.items);
  }

  @Override
  public Monster getMonster() {
    return this.monster;
  }

  @Override
  public Puzzle getPuzzle() {
    return this.puzzle;
  }

  @Override
  public Puzzle getRoomEnvironmentEffector() {
    return this.roomEnvironmentEffector;
  }

  @Override
  public void addItem(Item item) {
    this.items.put(item.getName(), item);
  }

  @Override
  public Item removeItem(String itemName) {
    return this.items.remove(itemName);
  }

  @Override
  public Room getPassageRoom(Directions direction) {
    return this.roomService.getRoom(this, direction);
  }

  @Override
  public boolean isPathBlocked() {
    return this.roomEnvironmentEffector.isActive();
  }

  @Override
  public String getPicturePath() {
    return this.picture;
  }

}
