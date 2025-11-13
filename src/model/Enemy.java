package model;

/**
 * The Enemy type represents enemies in an adventure
 * game that need to be defeated to stop their effect on a Room.
 * extends AbstractElement and implements Activatable, Effector, and PlayerAffector.
 */
public interface Enemy extends Activatable, Effector, PlayerAffector {

  /**
   * Attempt to solve an Enemy using an answer.
   * @param answer A String representing an answer that might deactivate the enemy.
   * @return true is the Enemy is no longer active, false if the enemy is still active.
   */
  boolean solve(String answer);

  /**
   * Attempt to solve an Enemy using an Item.
   * @param item An Item representing an item that might deactivate the enemy.
   * @return true is the Enemy is no longer active, false if the enemy is still active.
   */
  boolean solve(Item item);

}
