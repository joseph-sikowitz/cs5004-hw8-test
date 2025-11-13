package model;

/**
 * Represents the discrete Health states of the player.
 */
public enum HealthStatus {
  SLEEP(0),
  WOOZY(40),
  FATIGUED(70),
  AWAKE(100);

  final int maxHealth;

  /**
   * Returns the max health value for the HealthState.
   * @param maxHealth an int representing the max health value for the state.
   */
  HealthStatus(int maxHealth) {
    this.maxHealth = maxHealth;
  }

  public int getMaxHealth() {
    return maxHealth;
  }
}
