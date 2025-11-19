package model;

import java.util.HashMap;
import java.util.Map;


/**
 * The RoomService class is a service class for the Room class. It holds a list
 * of Rooms for reference.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
class RoomService {

  private static final RoomService INSTANCE = new RoomService();
  private static final int IMPASSABLE = 0;
  private static final int BLOCKED = 0;

  private final Map<Integer, Room> rooms;

  /**
   * Default constructor initializes a new ArrayList.
   */
  private RoomService() {
    this.rooms = new HashMap<>();
    //add 0 as null room because it represents a wall or no passage
    this.rooms.put(IMPASSABLE, null);
  }

  /**
   * A new instance of RoomService.
   * @return The singleton instance of RoomService.
   */
  static RoomService getInstance() {
    return INSTANCE;
  }

  /**
   * Clears all rooms from the RoomService Singleton.
   */
  static void clear() {
    INSTANCE.rooms.clear();
  }

  /**
   * Checks that all check all roomNumbers in passages of
   * each room in rooms leads to another Room in rooms.
   * @return true if all roomNumbers contained in all passages Maps
   *     of Room instances in rooms lead to another Room in rooms.
   */
  boolean checkAllPassagesLeadToInstantiatedRooms() {
    for (Room room : this.rooms.values()) {
      for (int roomNumber : room.getPassages().values()) {
        if (roomNumber != IMPASSABLE && !this.rooms.containsKey(roomNumber) ) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Checks reflexivity of passages within each Room.
   * @return if all passages are reflexive, false if one passage is unidirectional.
   */
  boolean checkReflexivity() {
    for (Room room : this.rooms.values()) {
      if (room != null) {
        for (Directions direction : Directions.values()) {
          int passageRoomNumber = Math.abs(room.getPassages().get(direction));
          if (passageRoomNumber != IMPASSABLE
                  && (this.rooms.get(passageRoomNumber) == null
                  || Math.abs(this.rooms.get(passageRoomNumber).getPassages()
                  .get(direction.getOppositeDirection())) != room.getRoomNumber())) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * adds a Room to the RoomService class.
   * @param room an instance of Room.
   */
  void addRoom(Room room) {
    this.rooms.put(room.getRoomNumber(), room);
  }

  /**
   * The getter for a Room object in the rooms Map.
   *
   * @param room an instance of room.
   * @param direction a Directions enum type.
   * @return Room object referenced by room number.
   * @throws IllegalArgumentException if passage is reflexive or Room
   *     at end of passage doesn't exist.
   * @throws CannotGetRoomException if Room is blocked or 0 is passed indicating no passage.
   */
  Room getRoom(Room room, Directions direction) throws IllegalArgumentException,
          CannotGetRoomException {
    if (room == null)
      throw new IllegalArgumentException("Room cannot be null!");
    int roomNumber = room.getPassageValue(direction);
    if (roomNumber == room.getRoomNumber()) {
      throw new IllegalArgumentException("Room cannot have a passage back to itself!");
    }
    if (!this.rooms.containsKey(Math.abs(roomNumber)))
      throw new IllegalArgumentException("Room doesn't exist!");

    if (roomNumber < BLOCKED && room.getRoomEnvironmentAffector() != null
            && !room.getRoomEnvironmentAffector().isActive())
      roomNumber = Math.abs(roomNumber);

    if (roomNumber <= BLOCKED) {
      throw new CannotGetRoomException(roomNumber == IMPASSABLE ? RoomStatus.NO_PASSAGE
              : RoomStatus.BLOCKED);
    }

    return this.rooms.get(roomNumber);
  }
}