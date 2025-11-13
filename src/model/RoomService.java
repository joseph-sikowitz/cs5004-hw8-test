package model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * The RoomService class is a service class for the Room class. It holds a list
 * of Rooms for reference.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
class RoomService {

  // attributes
  private List<Room> rooms;

  /**
   * The RoomService constructor initializes the rooms Map that holds all the
   * game's Rooms.
   *
   * @param rooms List of Rooms to hold all the game's Rooms.
   */
  RoomService(List<Room> rooms) {
    this.rooms = new ArrayList<Room>(rooms);
  }

  /**
   * Default constructor initializes a new ArrayList.
   */
  RoomService() {
    this.rooms = new ArrayList<>();
  }

  /**
   * The getter for a Room object in the rooms Map.
   *
   * @param roomNumber int of room number.
   * @return Room object referenced by room number.
   */
  Room getRoom(int roomNumber) throws IllegalArgumentException, InvalidParameterException {
    if (roomNumber >= rooms.size())
      throw new IllegalArgumentException("room number out of range");

    if (roomNumber <= 0 ) {
      throw new InvalidParameterException(roomNumber == 0 ? RoomStatus.NO_PASSAGE.getStatus()
              : RoomStatus.BLOCKED.getStatus());
    }
    //convert roomNumber to 0-indexing.
    return this.rooms.get(roomNumber - 1);
  }
}
