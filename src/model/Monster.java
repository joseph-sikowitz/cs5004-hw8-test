package model;

/**
 * The Monster interface defines the Monster type. Monsters can attack and
 * exist in Rooms. In a given room, there can only be one Monster or one Puzzle.
 * Monsters extend the Element and Puzzle interfaces.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Monster extends Element, Puzzle {

  /**
   * The getDamage() method is the getter for the amount of damage a Monster
   * can do.
   *
   * @return double of Monster's damage.
   */
  double getDamage();

  /**
   * The getCanAttack() method is the getter that determines if a Monster can
   * attack its target.
   *
   * @return boolean indicating if a Monster can attack.
   */
  boolean getCanAttack();

  /**
   * The flipCanAttack() method changes the boolean value indicating if a Monster
   * can or cannot attack.
   */
  void flipCanAttack();

  /**
   * The getAttackDescription() method gets the description of a Monster's attack.
   *
   * @return String of the Monster's attack.
   */
  String getAttackDescription();
}
