package model;

/**
 * Represents the discrete health states of the player.
 */
public enum HealthStatus {
  ZERO_HEALTH(0, "Asleep"),
  LOW_HEALTH(40, "Woozy"),
  HIGH_HEALTH(70, "Fatigued"),
  FULL_HEALTH(100, "Awake");

  private final double maxHealth;
  private final String healthStatus;

  /**
   * Initializes the values for each enum.
   * @param maxHealth an int representing the max health value for the state.
   * @param healthStatus a String describing the health state.
   */
  HealthStatus(double maxHealth, String healthStatus) {
    this.maxHealth = maxHealth;
    this.healthStatus = healthStatus;
  }

  /**
   * Returns the max health value for the specific HealthStatus state.
   * @return an int representing the max health value for the state.
   */
  public double getMaxHealth() {
    return maxHealth;
  }

  /**
   * Returns a String describing the health state.
   * @return a String describing the health state.
   */
  public String getHealthStatus() {
    return healthStatus;
  }
}
