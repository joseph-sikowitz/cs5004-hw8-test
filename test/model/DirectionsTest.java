package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the Directions enum extra method.
 */
class DirectionsTest {

  @Test
  void getOppositeDirection() {
    assertEquals(Directions.SOUTH, Directions.NORTH.getOppositeDirection());
    assertEquals(Directions.NORTH, Directions.SOUTH.getOppositeDirection());
    assertEquals(Directions.EAST, Directions.WEST.getOppositeDirection());
    assertEquals(Directions.WEST, Directions.EAST.getOppositeDirection());
  }
}