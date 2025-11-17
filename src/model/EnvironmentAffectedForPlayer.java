package model;

/**
 * A type where the Environment of the Type may be affected for the Player.
 */
public interface EnvironmentAffectedForPlayer {
  /**
   * Returns whether the Environment of the Type may be affected for the Player.
   * @return true
   */
  boolean affectorAffectsPlayer();
}
