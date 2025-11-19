package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the JsonFields enum.
 */
class JsonFieldsTest {

  /**
   * Tests the getter for the enum's value.
   */
  @Test
  void getValue() {
    assertEquals("name", JsonFields.NAME.getValue());
    assertEquals("version", JsonFields.VERSION.getValue());
    assertEquals("rooms", JsonFields.ROOMS.getValue());
    assertEquals("items", JsonFields.ITEMS.getValue());
    assertEquals("fixtures", JsonFields.FIXTURES.getValue());
    assertEquals("monsters", JsonFields.MONSTERS.getValue());
    assertEquals("puzzles", JsonFields.PUZZLES.getValue());
    assertEquals("player", JsonFields.PLAYER.getValue());
  }
}