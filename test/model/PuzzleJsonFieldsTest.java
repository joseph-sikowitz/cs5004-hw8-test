package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the PuzzleJsonFields enum.
 */
class PuzzleJsonFieldsTest {

  /**
   * Tests the getter for the enum's value.
   */
  @Test
  void getValue() {
    assertEquals("name", PuzzleJsonFields.NAME.getValue());
    assertEquals("active", PuzzleJsonFields.ACTIVE.getValue());
    assertEquals("affects_target", PuzzleJsonFields.AFFECTS_TARGET.getValue());
    assertEquals("affects_player", PuzzleJsonFields.AFFECTS_PLAYER.getValue());
    assertEquals("solution", PuzzleJsonFields.SOLUTION.getValue());
    assertEquals("value", PuzzleJsonFields.VALUE.getValue());
    assertEquals("description", PuzzleJsonFields.DESCRIPTION.getValue());
    assertEquals("effects", PuzzleJsonFields.EFFECTS.getValue());
    assertEquals("target", PuzzleJsonFields.TARGET.getValue());
    assertEquals("picture", PuzzleJsonFields.PICTURE.getValue());
  }

}