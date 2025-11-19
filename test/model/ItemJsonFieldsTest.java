package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the ItemJsonFields enum.
 */
class ItemJsonFieldsTest {

  /**
   * Tests for the getter of the enum's values.
   */
  @Test
  void getValue() {
    assertEquals("name", ItemJsonFields.NAME.getValue());
    assertEquals("weight", ItemJsonFields.WEIGHT.getValue());
    assertEquals("description", ItemJsonFields.DESCRIPTION.getValue());
    assertEquals("max_uses", ItemJsonFields.MAX_USES.getValue());
    assertEquals("uses_remaining", ItemJsonFields.USES_REMAINING.getValue());
    assertEquals("value", ItemJsonFields.VALUE.getValue());
    assertEquals("when_used", ItemJsonFields.WHEN_USED.getValue());
    assertEquals("picture", ItemJsonFields.PICTURE.getValue());
  }
}