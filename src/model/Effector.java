package model;

/**
 * The Effector interface defines types that have an effect on their targets.
 * It extends the Activatable interface.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Effector extends Activatable {

  /**
   * The getEffect() method gets the effect description on an Effector.
   *
   * @return String of Effector's effect.
   */
  String getEffect();
}
