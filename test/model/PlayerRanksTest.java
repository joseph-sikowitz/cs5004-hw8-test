package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the PlayerRanks enum class.
 */
class PlayerRanksTest {

  @Test
  void getName() {
    assertEquals("Novice", PlayerRanks.NOVICE.getName());
    assertEquals("Squire", PlayerRanks.SQUIRE.getName());
    assertEquals("Knight", PlayerRanks.KNIGHT.getName());
    assertEquals("Baron", PlayerRanks.BARON.getName());
    assertEquals("Prince", PlayerRanks.PRINCE.getName());
    assertEquals("King", PlayerRanks.KING.getName());
  }

  @Test
  void getLowValue() {
    assertEquals(0, PlayerRanks.NOVICE.getLowValue());
    assertEquals(100, PlayerRanks.SQUIRE.getLowValue());
    assertEquals(250, PlayerRanks.KNIGHT.getLowValue());
    assertEquals(500, PlayerRanks.BARON.getLowValue());
    assertEquals(1000, PlayerRanks.PRINCE.getLowValue());
    assertEquals(2500, PlayerRanks.KING.getLowValue());
  }

  @Test
  void getHighValue() {
    assertEquals(99, PlayerRanks.NOVICE.getHighValue());
    assertEquals(249, PlayerRanks.SQUIRE.getHighValue());
    assertEquals(499, PlayerRanks.KNIGHT.getHighValue());
    assertEquals(999, PlayerRanks.BARON.getHighValue());
    assertEquals(2499, PlayerRanks.PRINCE.getHighValue());
    assertEquals(10000, PlayerRanks.KING.getHighValue());
  }
}