package model;

import java.util.Map;

/**
 * The RoomService class is a service class for the Room class. It holds a list
 * of Rooms for reference.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
class RoomService {

  // attributes
  private Map<Integer, Room> rooms;

  /**
   * The RoomService constructor initializes the rooms Map that holds all the
   * game's Rooms.
   *
   * @param rooms Map of Integer and Room to hold all the game's Rooms.
   */
  RoomService(Map<Integer, Room> rooms) {
    this.rooms = rooms;
  }

  /**
   * The getter for a Room object in the rooms Map.
   *
   * @param roomNumber Integer of room number.
   * @return Room object referenced by room number.
   */
  Room getRoom(Integer roomNumber) {
    return this.rooms.get(roomNumber);
  }
}
