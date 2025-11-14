package model;

/**
 * The Puzzle type represents enemies in an adventure
 * game that need to be defeated to stop their effect on a Room.
 * extends AbstractElement and implements Activatable, Effector, and PlayerAffector.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Puzzle extends Element, Activatable, Effector, PlayerAffector {

  /**
   * Attempt to solve an Puzzle using an answer.
   *
   * @param answer A String representing an answer that might deactivate the enemy.
   * @return true is the Puzzle is no longer active, false if the enemy is still active.
   */
  boolean solve(String answer);

  /**
   * Attempt to solve an Puzzle using an Item.
   *
   * @param item An Item representing an item that might deactivate the enemy.
   * @return true is the Puzzle is no longer active, false if the enemy is still active.
   */
  boolean solve(Item item);

}
