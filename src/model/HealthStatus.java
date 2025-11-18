package model;

/**
 * Represents the discrete health states of the player.
 */
public enum HealthStatus {
  SLEEP(0, "Asleep"),
  WOOZY(40, "Woozy"),
  FATIGUED(70, "Fatigued"),
  AWAKE(100, "Awake");

  final int maxHealth;
  final String healthStatus;

  /**
   * Initializes the values for each enum.
   * @param maxHealth an int representing the max health value for the state.
   * @param healthStatus a String describing the health state.
   */
  HealthStatus(int maxHealth, String healthStatus) {
    this.maxHealth = maxHealth;
    this.healthStatus = healthStatus;
  }

  /**
   * Returns the max health value for the specific HealthStatus state.
   * @return an int representing the max health value for the state.
   */
  public int getMaxHealth() {
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
