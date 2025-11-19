package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * Tests the HealthStatus enum class.
 */
class HealthStatusTest {

  @Test
  void getMaxHealth() {
    assertEquals(0, HealthStatus.ZERO_HEALTH.getMaxHealth());
    assertEquals(40, HealthStatus.LOW_HEALTH.getMaxHealth());
    assertEquals(70, HealthStatus.HIGH_HEALTH.getMaxHealth());
    assertEquals(100, HealthStatus.FULL_HEALTH.getMaxHealth());
  }

  @Test
  void getHealthStatus() {
    assertEquals("Asleep", HealthStatus.ZERO_HEALTH.getHealthStatus());
    assertEquals("Woozy", HealthStatus.LOW_HEALTH.getHealthStatus());
    assertEquals("Fatigued", HealthStatus.HIGH_HEALTH.getHealthStatus());
    assertEquals("Awake", HealthStatus.FULL_HEALTH.getHealthStatus());
  }
}