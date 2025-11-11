package model;

/**
 * The ConcretePlayer class represents the game's player. It extends AbstractElement
 * and implements Activatable. ConcretePlayers have a name, description, score, active
 * status, maximum carrying weight, current carrying weight, a Map of ConcreteItems, and a
 * currently active ConcreteRoom.
 */
public class ConcretePlayer extends AbstractElement implements Activatable {

  // attributes
  private double score;
  private boolean active;
  private double health;
  private final double maxWeight;
  private double currentWeight;
  private Map<String, ConcreteItem> inventory;
  private ConcreteRoom activeRoom;

  /**
   * The constructor for ConcretePlayer initializes its attributes using its parent
   * class constructor to initialize name and description.
   *
   * @param name String of player's name.
   * @param description String of player's description.
   * @param score double of player's current score.
   * @param active boolean indicating if player is active.
   * @param maxWeight double of the maximum weight a player can carry.
   * @param currentWeight double of the current weight of items a player is carrying.
   * @param inventory Map of the player's item inventory.
   * @param activeRoom ConcreteRoom where the player is currently positioned.
   */
  public ConcretePlayer(String name, String description, double score, boolean active,
                        double health, double maxWeight, double currentWeight,
                        Map<String, ConcreteItem> inventory, ConcreteRoom activeRoom) {
    super(name, description);

    this.score = score;
    this.active = active;
    this.health = health;
    this.maxWeight = maxWeight;
    this.currentWeight = currentWeight;
    this.inventory = inventory;
    this.activeRoom = activeRoom;
  }

  @Override
  public boolean isActive() {
    return this.active;
  }

  @Override
  public void flipActive() {
    this.active = !this.active;
  }

  /**
   * The walk() method moves a player to a new room if passage to that room is
   * allowed or indicates why the player cannot move to a room.
   *
   * @param direction Directions enum of the direction to walk.
   */
  public void walk(Directions direction) {
    // should this return something?
    int passageValue = this.activeRoom.getPassageValue(direction);
    if (passageValue > 0) {
      // TODO: change activeRoom
    } else if (passageValue < 0) {
      // TODO: indicate passage is blocked
    } else {
      // TODO: indicate passage is not passable
    }
  }

  /**
   * The setScore() method sets the score of the player.
   *
   * @param score double of score to set.
   */
  public void setScore(double score) {
    this.score = score;
  }

  /**
   * The addToScore() method adds the given amount to the player's
   * score.
   *
   * @param scoreToAdd double of score to add.
   */
  public void addToScore(double scoreToAdd) {
    this.score += scoreToAdd;
  }

  /**
   * The getHealth method is the getter for the player's health.
   *
   * @return double of player's current health.
   */
  public double getHealth() {
    return this.health;
  }

  /**
   * The addHealth method increases the player's health by the given amount.
   *
   * @param health double of amount to add to player's health.
   */
  public void addHealth(double health) {
    this.health += health;
  }

  /**
   * The subtractHealth method decreases the player's health by the given amount.
   *
   * @param health double of amount to subtract from player's health.
   */
  public void subtractHealth(double health) {
    this.health -= health;
  }

  /**
   * The useItem() method uses an item in order to affect the room the player is
   * in.
   *
   * @param item String key of item to use in inventory.
   */
  public void useItem(String item) {

  }

  /**
   * The takeItem() method takes an item from the room the player is currently in.
   *
   * @param item String of item to add to the player's inventory.
   */
  public void takeItem(String item) {

  }

  /**
   * The dropItem() method takes an item out of a player's inventory and drops it
   * in the active room.
   *
   * @param item String of the item to drop from the player's inventory.
   */
  public void dropItem(String item) {

  }

  /**
   * The examine() method gets the description of an element in the player's
   * active room.
   *
   * @param element Element in active room to return description of.
   * @return String of Element's description.
   */
  public String examine(Element element) {
    return element.getDescription();
  }

  /**
   * The answer() method provides an answer to a puzzle in the player's active
   * room in order to solve it.
   *
   * @param answer String of answer to provide to solve puzzle.
   */
  public void answer(String answer) {

  }

  /**
   * The addWeight() method adds the given weight to the player's current carrying
   * weight. If the added weight would increase the player's weight beyond the
   * maxmimum allowed, the weight is not added and an exception is thrown.
   *
   * @param weight double of weight to add to player's currentWeight.
   * @throws IllegalArgumentException if added weight will exceed maximum allowed.
   */
  public void addWeight(double weight) throws IllegalArgumentException {
    double extraCapacity = this.maxWeight - this.currentWeight;
    if (weight <= extraCapacity) {
      this.currentWeight += weight;
    } else {
      throw new IllegalArgumentException("Cannot add that much weight.");
    }
  }

  /**
   * The reduceWeight() method reduces the player's current carrying weight by
   * the given amount.
   *
   * @param weight double of the weight to subtract from player's currentWeight.
   */
  public void reduceWeight(double weight) {
    this.currentWeight -= weight;
  }
}
