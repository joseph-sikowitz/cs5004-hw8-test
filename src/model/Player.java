package model;

/**
 * The Player interface defines the Player type in an adventure game. Players
 * are the way that users interact with the game and act as their avatar.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Player {

  /**
   * The walk() method moves a player to a new room if passage to that room is
   * allowed or indicates why the player cannot move to a room.
   *
   * @param direction Directions enum of the direction to walk.
   */
  void walk(Directions direction);

  /**
   * The setScore() method sets the score of the player.
   *
   * @param score double of score to set.
   */
  void setScore(double score);

  /**
   * The addToScore() method adds the given amount to the player's
   * score.
   *
   * @param score double of score to add.
   */
  void addToScore(double score);

  /**
   * The getHealth method is the getter for the player's health.
   *
   * @return double of player's current health.
   */
  double getHealth();

  /**
   * The addHealth method increases the player's health by the given amount.
   *
   * @param health double of amount to add to player's health.
   */
  void addHealth(double health);

  /**
   * The subtractHealth method decreases the player's health by the given amount.
   *
   * @param health double of amount to subtract from player's health.
   */
  void subtractHealth(double health);

  /**
   * The useItem() method uses an item in order to affect the room the player is
   * in.
   *
   * @param item String key of item to use in inventory.
   */
  void useItem(String item);

  /**
   * The takeItem() method takes an item from the room the player is currently in.
   *
   * @param item String of item to add to the player's inventory.
   */
  void takeItem(String item);

  /**
   * The dropItem() method takes an item out of a player's inventory and drops it
   * in the active room.
   *
   * @param item String of the item to drop from the player's inventory.
   */
  void dropItem(String item);

  /**
   * The examine() method gets the description of an element in the player's
   * active room.
   *
   * @param element Element in active room to return description of.
   * @return String of Element's description.
   */
  String examine(Element element);

  /**
   * The answer() method provides an answer to a puzzle in the player's active
   * room in order to solve it.
   *
   * @param answer String of answer to provide to solve puzzle.
   */
  void answer(String answer);

  /**
   * The addWeight() method adds the given weight to the player's current carrying
   * weight. If the added weight would increase the player's weight beyond the
   * maximum allowed, the weight is not added and an exception is thrown.
   *
   * @param weight double of weight to add to player's currentWeight.
   * @throws IllegalArgumentException if added weight will exceed maximum allowed.
   */
  void addWeight(double weight);

  /**
   * The reduceWeight() method reduces the player's current carrying weight by
   * the given amount.
   *
   * @param weight double of the weight to subtract from player's currentWeight.
   */
  void reduceWeight(double weight);

}
