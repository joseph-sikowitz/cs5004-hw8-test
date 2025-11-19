package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * Tests the RoomStatusEnum class.
 */
class RoomStatusTest {

  @Test
  void getStatus() {
    assertEquals("Passage to Room is blocked!", RoomStatus.BLOCKED.getStatus());
    assertEquals("Wall or no Passage in this direction!", RoomStatus.NO_PASSAGE.getStatus());
    assertEquals("The passage between rooms is open!", RoomStatus.OPEN.getStatus());
  }
}