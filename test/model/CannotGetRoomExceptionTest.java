package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * Tests the CannotGetRoomExceptionTest class.
 */
class CannotGetRoomExceptionTest {

  @Test
  void getRoomExceptionStatus() {
    try {
      throw new CannotGetRoomException(RoomStatus.BLOCKED);
    } catch (Exception e) {
      assertEquals(CannotGetRoomException.class, e.getClass());
      assertEquals(RoomStatus.BLOCKED, ((CannotGetRoomException) e).getRoomExceptionStatus());
    }
    try {
      throw new CannotGetRoomException(RoomStatus.NO_PASSAGE);
    } catch (Exception e) {
      assertEquals(CannotGetRoomException.class, e.getClass());
      assertEquals(RoomStatus.NO_PASSAGE, ((CannotGetRoomException) e).getRoomExceptionStatus());
    }
  }
}