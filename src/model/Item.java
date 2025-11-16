package model;

/**
 * The Item interface represents an item game object in an adventure game. The interface
 * extends the Activatable, Element, Picturable, Scorable, and Weightable interfaces.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Item extends Element, Activatable, Picturable, Scorable, Weightable {

  /**
   * The getMaxUses() method is a getter for the maximum number of uses possible
   * for an item.
   *
   * @return int of the maximum uses.
   */
  int getMaxUses();

  /**
   * The getUsesRemaining() method is a getter for the number of uses left for an
   * item.
   *
   * @return int of the remaining uses.
   */
  int getUsesRemaining();


  /**
   * The use() method uses an item in a room. It can have effects in the room
   * and displays a message.
   *
   * @return String message when item is used.
   */
  String use();

  /**
   * Uses the Item on an enemy.
   * Decrements the amount of uses left for this Item by 1.
   * @param enemy an instance of Puzzle subtype to use this Item on.
   * @return an instance UseSuccessful describing this Item's use and whether it was successful.
   */
  UseSuccessful use(Puzzle enemy);

  /**
   * The getter for the Item's use description.
   *
   * @return String of use description.
   */
  String getUseDescription();
}
