package model;

/**
 * The ConcretePlayer class represents the game's player. It extends AbstractElement
 * and implements Activatable. ConcretePlayers have a name, description, score, active
 * status, maximum carrying weight, current carrying weight, a Map of ConcreteItems, and a
 * currently active ConcreteRoom.
 */
public class ConcretePlayer extends AbstractElement implements Player, Activatable {

  // attributes
  private double score;
  private boolean active;
  private double health;
  private final double maxWeight;
  private double currentWeight;
  private Map<String, Item> inventory;
  private Room activeRoom;

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

  @Override
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

  @Override
  public void setScore(double score) {
    this.score = score;
  }

  @Override
  public void addToScore(double score) {
    this.score += score;
  }

  @Override
  public double getHealth() {
    return this.health;
  }

  @Override
  public void addHealth(double health) {
    this.health += health;
  }

  @Override
  public void subtractHealth(double health) {
    this.health -= health;
  }

  @Override
  public void useItem(String item) {

  }

  @Override
  public void takeItem(String item) {

  }

  @Override
  public void dropItem(String item) {

  }

  @Override
  public String examine(Element element) {
    return element.getDescription();
  }

  @Override
  public void answer(String answer) {

  }

  @Override
  public void addWeight(double weight) throws IllegalArgumentException {
    double extraCapacity = this.maxWeight - this.currentWeight;
    if (weight <= extraCapacity) {
      this.currentWeight += weight;
    } else {
      throw new IllegalArgumentException("Cannot add that much weight.");
    }
  }

  @Override
  public void reduceWeight(double weight) {
    this.currentWeight -= weight;
  }
}
