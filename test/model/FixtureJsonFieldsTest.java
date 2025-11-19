package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the FixtureJsonFields enum.
 */
class FixtureJsonFieldsTest {

  /**
   * Tests the getter for the enum's values.
   */
  @Test
  void getValue() {
    assertEquals("name", FixtureJsonFields.NAME.getValue());
    assertEquals("weight", FixtureJsonFields.WEIGHT.getValue());
    assertEquals("description", FixtureJsonFields.DESCRIPTION.getValue());
    assertEquals("puzzle",  FixtureJsonFields.PUZZLE.getValue());
    assertEquals("states", FixtureJsonFields.STATES.getValue());
    assertEquals("picture", FixtureJsonFields.PICTURE.getValue());
  }
}