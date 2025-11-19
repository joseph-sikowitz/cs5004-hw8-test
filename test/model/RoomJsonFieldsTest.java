package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the RoomJsonFields enum.
 */
class RoomJsonFieldsTest {

  /**
   * Tests the getter of the enum's value.
   */
  @Test
  void getValue() {
    assertEquals("room_name", RoomJsonFields.ROOM_NAME.getValue());
    assertEquals("room_number", RoomJsonFields.ROOM_NUMBER.getValue());
    assertEquals("description", RoomJsonFields.DESCRIPTION.getValue());
    assertEquals("N", RoomJsonFields.NORTH.getValue());
    assertEquals("S", RoomJsonFields.SOUTH.getValue());
    assertEquals("W", RoomJsonFields.WEST.getValue());
    assertEquals("E", RoomJsonFields.EAST.getValue());
    assertEquals("puzzle", RoomJsonFields.PUZZLE.getValue());
    assertEquals("monster", RoomJsonFields.MONSTER.getValue());
    assertEquals("items", RoomJsonFields.ITEMS.getValue());
    assertEquals("fixtures", RoomJsonFields.FIXTURES.getValue());
    assertEquals("picture", RoomJsonFields.PICTURE.getValue());
  }
}