package model;

/**
 * The Element interface defines the methods for all elements within the adventure
 * game.
 */
public interface Element {

  /**
   * The getName() method is the getter for the Element's name.
   *
   * @return String of the name.
   */
  String getName();

  /**
   * The getDescription() method is the getter for the Element's description.
   *
   * @return String of the description.
   */
  String getDescription();
}
