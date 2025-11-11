package model;

/**
 * The Targeter interface defines a type that can target another game element
 * with an affect.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Targeter {

  /**
   * The getTarget() method gets the target of a Targeter.
   *
   * @return String of the Targeter's target.
   */
  String getTarget();

  /**
   * The affectsTarget() method determines if the Targeter can affect its target.
   *
   * @return boolean indicating if a Targeter affects its target.
   */
  boolean affectsTarget();
}
