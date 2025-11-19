package model;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class RoomServiceTest {

  private Map<Directions, Integer> passages;


  @BeforeEach
  void setUp() {
    this.passages = new HashMap<>();
    for (Directions dir : Directions.values()) {
      passages.put(dir, dir.ordinal() + 2);
    }
  }

  @Test
  void testClear() {
    assertDoesNotThrow(RoomService::clear);
  }

  @Test
  void testGetInstance() {
    RoomService instance = RoomService.getInstance();
    RoomService instance2 = RoomService.getInstance();
    assertEquals(instance, instance2);
  }

  @Test
  void testAddRoom() {
    //is added in constructor of room.
    Room room1 = new ConcreteRoom("testName4", "testDescription4", 1, this.passages,
            null, null, null, null, null);
    Room room2 = new ConcreteRoom("testName4", "testDescription4", 2, this.passages,
            null, null, null, null, null);
    Room room3 = new ConcreteRoom("testName4", "testDescription4", 3, this.passages,
            null, null, null, null, null);
    Room room4 = new ConcreteRoom("testName4", "testDescription4", 4, this.passages,
            null, null, null, null, null);
    Room room5 = new ConcreteRoom("testName4", "testDescription4", 5, this.passages,
            null, null, null, null, null);

  }

  @Test
  void testGetRoom() {
    RoomService.clear();
    Map<Directions, Integer> passages2 = new HashMap<>();
    passages2.put(Directions.NORTH, -1);
    passages2.put(Directions.SOUTH, 0);
    passages2.put(Directions.EAST, 3);
    passages2.put(Directions.WEST, 2);
    Room room1 = new ConcreteRoom("testName4", "testDescription4", 1, this.passages,
            null, null, null, null, null);
    Room room2 = new ConcreteRoom("testName4", "testDescription4", 2, passages2,
            null, null, null, null, null);
    Room room3 = new ConcreteRoom("testName4", "testDescription4", 3, this.passages,
            null, null, null, null, null);
    Room room4 = new ConcreteRoom("testName4", "testDescription4", 4, this.passages,
            null, null, null, null, null);
    Room room5 = new ConcreteRoom("testName4", "testDescription4", 5, this.passages,
            null, null, null, null, null);

    for (Directions dir : Directions.values()) {
      assertDoesNotThrow(() -> room1.getPassageRoom(dir));
    }

    assertThrows(CannotGetRoomException.class, () -> room2.getPassageRoom(Directions.NORTH));
    try {
      room2.getPassageRoom(Directions.NORTH);
    } catch  (CannotGetRoomException e) {
      assertEquals(RoomStatus.BLOCKED, e.getRoomExceptionStatus());
    }
    assertThrows(CannotGetRoomException.class, () -> room2.getPassageRoom(Directions.SOUTH));
    try {
      room2.getPassageRoom(Directions.SOUTH);
    } catch  (CannotGetRoomException e) {
      assertEquals(RoomStatus.NO_PASSAGE, e.getRoomExceptionStatus());
    }
  }



  @Test
  void testCheckAllPassagesLeadToInstantiatedRooms() {
    assertTrue(RoomService.getInstance().checkAllPassagesLeadToInstantiatedRooms());
    Map<Directions, Integer> passages2 = new HashMap<>();
    passages2.put(Directions.NORTH, -1);
    passages2.put(Directions.SOUTH, 0);
    passages2.put(Directions.EAST, 20);
    passages2.put(Directions.WEST, 2);
    Room room2 = new ConcreteRoom("testName4", "testDescription4", 6, passages2,
            null, null, null, null, null);
    assertFalse(RoomService.getInstance().checkAllPassagesLeadToInstantiatedRooms());
  }

  @Test
  void testCheckReflexivity() {
    assertTrue(RoomService.getInstance().checkReflexivity());
    Map<Directions, Integer> passages2 = new HashMap<>();
    passages2.put(Directions.NORTH, -2);
    passages2.put(Directions.SOUTH, 0);
    passages2.put(Directions.EAST, 20);
    passages2.put(Directions.WEST, 3);
    Room room2 = new ConcreteRoom("testName4", "testDescription4", 7, passages2,
            null, null, null, null, null);
    assertFalse(RoomService.getInstance().checkReflexivity());
  }


}