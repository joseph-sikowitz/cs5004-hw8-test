package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The UseSuccessfulTest class tests the UseSuccessful class which is used to return
 * use messages for items and whether they were successful or not.
 */
class UseSuccessfulTest {
  private UseSuccessful us;
  private UseSuccessful us2;

  /**
   * The setUp() method creates two UseSuccessful objects for testing.
   */
  @BeforeEach
  void setUp() {
    us = new UseSuccessful("this item was used", true);
    us2 = new UseSuccessful("this item was not used", false);
  }

  /**
   * Tests the getter for an UseSuccessful object's use.
   */
  @Test
  void getUse() {
    assertEquals("this item was used", us.getUse());
    assertEquals("this item was not used", us2.getUse());
  }

  /**
   * Tests the getter for an UseSuccessful object's use successful boolean.
   */
  @Test
  void getUseSuccessful() {
    assertTrue(us.getUseSuccessful());
    assertFalse(us2.getUseSuccessful());
  }
}