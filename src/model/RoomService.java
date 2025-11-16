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

  private final Map<Integer, Room> rooms;

  /**
   * Default constructor initializes a new ArrayList.
   */
  private RoomService() {
    this.rooms = new HashMap<>();
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
   * Checks reflexivity of passages within each Room.
   * @return if all passages are reflexive, false if one passage is unidirectional.
   */
  boolean checkReflexivity() {
    for (Room room : this.rooms.values()) {
      if (room != null) {
        for (Directions direction : Directions.values()) {
          int passageRoomNumber = Math.abs(room.getPassages().get(direction));
          if (passageRoomNumber != 0 && this.rooms.get(passageRoomNumber).getPassages()
                  .get(direction.getOppositeDirection()) != 0 && Math.abs(this.rooms.get(passageRoomNumber).getPassages()
                  .get(direction.getOppositeDirection())) != room.getRoomNumber()) {
            int otherRoomNumber = Math.abs(this.rooms.get(passageRoomNumber).getPassages()
                    .get(direction.getOppositeDirection()));
            System.out.println("Room: " + room.getRoomNumber() + " Other room: " +  Math.abs(room.getPassages().get(direction)) + " Other room's room:" + otherRoomNumber);
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
      throw new IllegalArgumentException("room cannot be null!");
    int roomNumber = room.getPassageValue(direction);
    if (roomNumber == room.getRoomNumber()) {
      throw new IllegalArgumentException("Room cannot have a passage back to itself!");
    }
    if (roomNumber >= rooms.size())
      throw new IllegalArgumentException("room number out of range");

    if (roomNumber < 0 && room.getRoomEnvironmentEffector() != null
            && !room.getRoomEnvironmentEffector().isActive())
      roomNumber = Math.abs(roomNumber);

    if (roomNumber <= 0) {
      throw new CannotGetRoomException(roomNumber == 0 ? RoomStatus.NO_PASSAGE
              : RoomStatus.BLOCKED);
    }

    return this.rooms.get(roomNumber);
  }
}