package model;

import java.util.HashMap;
import java.util.HashSet;
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
  private Puzzle roomEnvironmentAffector;
  private final Monster monster;
  private final Puzzle puzzle;
  private final String picture;

  // constants
  private static final RoomService ROOM_SERVICE = RoomService.getInstance();
  private static final int MIN_ROOM_NUMBER = 0;
  private static final int PASSAGE_COUNT = 4;
  private static final int IMPASSABLE = 0;

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
   */
  public ConcreteRoom(String name, String description, int roomNumber,
                      Map<Directions, Integer> passages, Map<String, Item> items,
                      Map<String, Fixture> fixtures, Monster monster, Puzzle puzzle,
                      String picture) throws IllegalArgumentException {
    super(name, description);

    if (roomNumber <= MIN_ROOM_NUMBER) {
      throw new IllegalArgumentException("Invalid room number");
    }

    this.roomNumber = roomNumber;


    if (passages == null || passages.size() != PASSAGE_COUNT) {
      throw new IllegalArgumentException("Room must have 4 passages!");
    }
    //filter out 0's and get room numbers from passages
    List<Integer> checkPassages = passages.values().stream()
            .filter((i) -> i != IMPASSABLE).toList();

    //all directions have a value of 0 or no key-value pairs in passages.
    /*
    if (checkPassages.isEmpty()) {
      throw new IllegalArgumentException("Room must have a passage to another Room!");
    }
     */

    //Ignore whether passages are blocked.
    List<Integer> checkRoomNumbers = checkPassages.stream().map(Math::abs).toList();

    //check if there is more than one passage between the same two Rooms.
    if (checkRoomNumbers.size() != new HashSet<>(checkRoomNumbers).size()) {
      throw new IllegalArgumentException("Room cannot have more than one passage to another Room!");
    }

    this.passages = passages;
    this.items = items;
    this.fixtures = fixtures;

    if (monster != null && puzzle != null) {
      throw new IllegalArgumentException("A Room may have either one Monster or one Puzzle!");
    }

    this.monster = monster;
    this.puzzle = puzzle;
    this.picture = picture;
    this.roomEnvironmentAffector = monster != null ? monster : puzzle;

    //determine if roomEnvironmentEffector effects room or fixture.
    if (this.roomEnvironmentAffector != null && this.roomEnvironmentAffector.getTarget()
            != null && !this.roomEnvironmentAffector.getTarget().contains(":")) {
      this.roomEnvironmentAffector = null;
    } else if (this.roomEnvironmentAffector != null
            && this.roomEnvironmentAffector.getTarget() != null
            && !this.roomEnvironmentAffector.getTarget().split(":")[1]
            .equalsIgnoreCase(this.getName())) {
      throw new IllegalArgumentException("Monster/Puzzle is affecting another room!"
              + "Room name: " + this.getName() + " Monster/Puzzle Target Room name: "
              + this.roomEnvironmentAffector.getTarget().split(":")[1]);
    }

    //Add this Room instance to roomService
    ConcreteRoom.ROOM_SERVICE.addRoom(this);
  }


  @Override
  public String getDescription() {
    if (this.roomEnvironmentAffector != null && this.roomEnvironmentAffector.isActive())
      return roomEnvironmentAffector.getEffect();
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
  public Fixture getFixture(String fixtureName) {
    if (this.affectorAffectsPlayer()) {
      return null;
    }
    return this.fixtures.get(fixtureName.toLowerCase());
  }

  @Override
  public Item getItem(String itemName) {
    if (this.affectorAffectsPlayer()) {
      return null;
    }
    return this.items.get(itemName.toLowerCase());
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
  public Puzzle getRoomEnvironmentAffector() {
    return this.roomEnvironmentAffector;
  }

  @Override
  public boolean affectorAffectsPlayer() {
    return this.roomEnvironmentAffector != null && roomEnvironmentAffector.isActive()
            && roomEnvironmentAffector.affectsPlayer();
  }

  @Override
  public void addItem(Item item) {
    this.items.put(item.getName().toLowerCase(), item);
  }

  @Override
  public Item removeItem(String itemName) {
    if (this.affectorAffectsPlayer()) {
      return null;
    }
    return this.items.remove(itemName.toLowerCase());
  }

  @Override
  public Room getPassageRoom(Directions direction) {
    return ConcreteRoom.ROOM_SERVICE.getRoom(this, direction);
  }

  @Override
  public Map<Directions, Integer> getPassages() {
    return new HashMap<>(this.passages);
  }

  @Override
  public Map<String, Item> getItems() {
    return new HashMap<>(this.items);
  }

  @Override
  public Map<String, Fixture> getFixtures() {
    return new HashMap<>(this.fixtures);
  }


  @Override
  public String getPicturePath() {
    return this.picture;
  }

  @Override
  public String getTrueDescription() {
    return super.getDescription();
  }

}
