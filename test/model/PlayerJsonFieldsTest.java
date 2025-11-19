package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the PlayerJsonFields enum.
 */
class PlayerJsonFieldsTest {

  /**
   * Tests the getter for the enum's value.
   */
  @Test
  void getValue() {
    assertEquals("name", PlayerJsonFields.NAME.getValue());
    assertEquals("health", PlayerJsonFields.HEALTH.getValue());
    assertEquals("description", PlayerJsonFields.DESCRIPTION.getValue());
    assertEquals("value", PlayerJsonFields.SCORE.getValue());
    assertEquals("max_weight", PlayerJsonFields.MAX_WEIGHT.getValue());
    assertEquals("inventory", PlayerJsonFields.INVENTORY.getValue());
    assertEquals("active_room", PlayerJsonFields.ACTIVE_ROOM.getValue());
    assertEquals("items_added", PlayerJsonFields.ITEMS_ADDED.getValue());
  }

}