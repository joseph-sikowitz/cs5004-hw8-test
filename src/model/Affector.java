package model;

/**
 * The Affector interface defines types that have an effect on their targets.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Affector {

  /**
   * The getEffect() method gets the effect description on an Affector.
   *
   * @return String of Affector's effect.
   */
  String getEffect();
}
