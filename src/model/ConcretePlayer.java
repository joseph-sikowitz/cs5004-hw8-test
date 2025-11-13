package model;

import java.util.HashMap;
import java.util.Map;

/**
 * The ConcretePlayer class represents the game's player. It extends AbstractElement
 * and implements Activatable. ConcretePlayers have a name, description, score, active
 * status, maximum carrying weight, current carrying weight, a Map of ConcreteItems, and a
 * currently active ConcreteRoom.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class ConcretePlayer extends AbstractElement implements Player {
  private static final double MAX_HEALTH = 100.0;
  private static final double MAX_WEIGHT = 13.0;

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
   * @param name          String of player's name.
   * @param description   String of player's description.
   * @param score         double of player's current score.
   * @param maxWeight     double of the maximum weight a player can carry.
   * @param inventory     Map of the player's item inventory.
   * @param activeRoom    ConcreteRoom where the player is currently positioned.
   */
  public ConcretePlayer(String name, String description, double score,
                        double health, double maxWeight, Map<String, Item> inventory, Room activeRoom) {
    super(name, description);
    if (score < 0.0)
      throw new IllegalArgumentException("Score cannot be negative");
    this.score = score;
    if (health < 0.0 || health > 100.0)
      throw new IllegalArgumentException("Health isn't greater than 0"
              + " and less than or equal 100.!");

    this.health = health;
    //clamp maxWeight value to 13.0
    this.maxWeight = Math.min(maxWeight, MAX_WEIGHT);
    if (currentWeight < 0.0)
      throw new IllegalArgumentException("Current weight isn't greater than 0!");

    if (currentWeight < maxWeight) {
      throw new IllegalArgumentException("Weight of inventory is over maxWeight!");
    }
    if (inventory == null || activeRoom == null) {
      throw new IllegalArgumentException("inventory and activeRoom cannot be null!");
    }
    //accumulate weight of all items in the inventory.
    this.currentWeight = inventory.values().stream().map(Item::getWeight).reduce(0.0, Double::sum);
    //ensure currentWeight is equal to weight of all items in inventory.
    if (currentWeight > maxWeight) {
      throw new IllegalArgumentException("Weight of inventory is over maxWeight!");
    }

    this.inventory = inventory;
    this.activeRoom = activeRoom;
  }

  @Override
  public boolean isActive() {
    return this.health > 0.0;
  }

  @Override
  public RoomStatus walk(Directions direction) {
    // should this return something?
    int passageValue = this.activeRoom.getPassageValue(direction);
    if (passageValue > 0) {
      // TODO: change activeRoom
    } else if (passageValue < 0) {
      // TODO: indicate passage is blocked
    } else {
      // TODO: indicate passage is not passable
    }
    return RoomStatus.BLOCKED;
  }

  @Override
  public void setScore(double score) {
    this.score = score;
  }

  @Override
  public void addToScore(double score) throws IllegalArgumentException {
    if (score < 0.0)
      throw new IllegalArgumentException("Player score cannot be decreased!");

    this.score += score;
  }

  @Override
  public double getHealth() {
    return this.health;
  }

  @Override
  public void addHealth(double health) {
    if (this.health + health <= MAX_HEALTH)
      this.health += health;
  }

  @Override
  public void subtractHealth(double health) {
    this.health -= health;
  }

  @Override
  public HealthStatus getHealthStatus() {
    if (health <= HealthStatus.SLEEP.getMaxHealth()) {
      return HealthStatus.SLEEP;
    } else if (health <= HealthStatus.WOOZY.getMaxHealth()) {
      return HealthStatus.WOOZY;
    } else if (health <= HealthStatus.FATIGUED.getMaxHealth()) {
      return HealthStatus.FATIGUED;
    }
    return HealthStatus.AWAKE;
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

  @Override
  public Room getActiveRoom() {
    return this.activeRoom;
  }

  @Override
  public void setActiveRoom(Room room) {
    this.activeRoom = room;
  }

  @Override
  public double getScore() {
    return this.score;
  }

  Map<String, Item> getInventory() {
    return new HashMap<>(inventory);
  }
}
