package model;

/**
 * The Activatable interface defines types that are activated or not as
 * defined by a boolean.
 */
public interface Activatable {

  /**
   * The isActive() method indicates if an Activatable is active or not.
   *
   * @return boolean indicating if Activatable is active.
   */
  boolean isActive();

  /**
   * The flipActive() method changes the current value of an Activatable
   * boolean to its opposite.
   */
  void flipActive();
}
