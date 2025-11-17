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

  // constants
  private static final String DEFAULT_PLAYER_DESCRIPTION = "Default player";

  /**
   * The constructor for ConcretePlayer initializes its attributes using its parent
   * class constructor to initialize name and description.
   *
   * @param name          String of player's name.
   * @param description   String of player's description.
   * @param score         double of player's current score.
   * @param health        double of the player's current health.
   * @param maxWeight     double of the maximum weight a player can carry.
   * @param inventory     Map of the player's item inventory.
   * @param activeRoom    ConcreteRoom where the player is currently positioned.
   */
  public ConcretePlayer(String name, String description, double score,
                        double health, double maxWeight, Map<String, Item> inventory,
                        Room activeRoom) {
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

    if (currentWeight > maxWeight) {
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

  /**
   * This ConcretePlayer constructor is used to instantiate a new player. It takes a
   * name, inventory map, and the player's active room.
   *
   * @param name String of player's name.
   * @param inventory Map of player's inventory, probably empty to start.
   * @param activeRoom Room where the player will start the game.
   */
  public ConcretePlayer(String name, Map<String, Item> inventory, Room activeRoom) {
    this(name, DEFAULT_PLAYER_DESCRIPTION, 0.0, 100.0, 13.0, inventory, activeRoom);
  }

  @Override
  public boolean isActive() {
    return this.health > 0.0;
  }

  @Override
  public RoomStatus walk(Directions direction) {
    try {
      this.activeRoom = this.activeRoom.getPassageRoom(direction);
    } catch (CannotGetRoomException e) {
      return e.getRoomExceptionStatus();
    }
    return RoomStatus.OPEN;
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
  public void changeHealth(double health) {
    if (this.health + health <= MAX_HEALTH)
      this.health += health;
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
  public UseSuccessful useItem(String itemName) {
    Puzzle enemy = this.activeRoom.getRoomEnvironmentEffector();
    /*
     * short circuit if player doesn't have the item,
     * active room doesn't have the enemy, or enemy is already deactivated.
     */
    if (!inventory.containsKey(itemName)) {
      return new UseSuccessful(itemName + " not found within inventory", false);
    }
    if (enemy == null )
      return new UseSuccessful("No Monster or Puzzle to use " + itemName + " on.", false);
    if (!enemy.isActive())
      return new UseSuccessful("You can't use " + itemName + " on this puzzle.", false);
    Item item = inventory.get(itemName);
    UseSuccessful wasUseSuccessful = item.use(enemy);
    if (wasUseSuccessful.getUseSuccessful())
      this.addToScore(enemy.getScore());
    return wasUseSuccessful;
  }

  @Override
  public TakeItemStatus takeItem(String item) {
    Item itemToPickUp = this.activeRoom.getItem(item);
    if (itemToPickUp == null )
      return TakeItemStatus.ITEM_NOT_FOUND;


    if (itemToPickUp.getWeight() + this.currentWeight > this.maxWeight)
      return TakeItemStatus.ITEM_NOT_ADDED_OVER_CAPACITY;

    //add item to Player's inventory.
    this.inventory.put(itemToPickUp.getName(), this.activeRoom.removeItem(item));
    //increment currentWeight by itemToPickUp's weight.
    this.currentWeight += itemToPickUp.getWeight();

    return TakeItemStatus.ITEM_ADDED;
  }

  @Override
  public boolean dropItem(String item) {
    //Item not in Player's inventory
    if (!this.inventory.containsKey(item))
      return false;

    //remove Item from inventory.
    Item droppedItem = this.inventory.remove(item);
    //decrement currentWeight by droppedItem's weight.
    this.currentWeight -= droppedItem.getWeight();
    //add droppedItem to Room player is in.
    this.activeRoom.addItem(droppedItem);
    return true;
  }

  @Override
  public String examine(String element) {
    //Player either examines Item in their inventory or Item in activeRoom
    Item itemToExamine = this.inventory.get(element) != null ? this.inventory.get(element)
            : this.activeRoom.getItem(element);
    Fixture fixtureToExamine = this.activeRoom.getFixture(element);
    String description = null;
    if (itemToExamine != null)
      description = itemToExamine.getDescription();
    else if (fixtureToExamine != null)
      description = fixtureToExamine.getDescription();
    return description;
  }

  @Override
  public boolean answer(String answer) {
    Puzzle enemy = this.activeRoom.getRoomEnvironmentEffector();
    //short circuit if enemy is already deactivated.
    if (!enemy.isActive())
      return false;

    boolean enemySolved = enemy.solve(answer);
    if (enemySolved)
      this.addToScore(enemy.getScore());
    return enemySolved;
  }

  @Override
  public Room getActiveRoom() {
    return this.activeRoom;
  }


  @Override
  public double getScore() {
    return this.score;
  }

  @Override
  public Map<String, Item> getInventory() {
    return new HashMap<>(this.inventory);
  }

  @Override
  public double getMaxWeight() {
    return this.maxWeight;
  }
}
