package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The ConcretePlayer class represents the game's player. It extends AbstractElement
 * and implements Activatable. ConcretePlayers have a name, description, score, active
 * status, maximum carrying weight, current carrying weight, a Map of ConcreteItems, a
 * currently active ConcreteRoom, and a set of items added to inventory.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class ConcretePlayer extends AbstractElement implements Player {

  // attributes
  private double score;
  private boolean active;
  private double health;
  private final double maxWeight;
  private double currentWeight;
  private final Map<String, Item> inventory;
  private Room activeRoom;
  private final Set<String> itemsAdded;

  // constants
  private static final String DEFAULT_PLAYER_DESCRIPTION = "Default player";
  private static final double MAX_HEALTH = 100.0;
  private static final double MIN_HEALTH = 0.0;
  private static final double MAX_WEIGHT = 13.0;
  private static final double MIN_WEIGHT = 0.0;
  private static final double MIN_SCORE = 0.0;

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
    if (score < MIN_SCORE)
      throw new IllegalArgumentException("Score cannot be negative");
    this.score = score;
    if (health < MIN_HEALTH || health > MAX_HEALTH)
      throw new IllegalArgumentException("Health isn't greater than 0"
              + " and less than or equal 100.!");

    this.health = health;
    //clamp maxWeight value to 13.0
    this.maxWeight = Math.min(maxWeight, MAX_WEIGHT);
    if (currentWeight < MIN_WEIGHT)
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
    this.itemsAdded = new HashSet<>();
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
    this(name, DEFAULT_PLAYER_DESCRIPTION, MIN_SCORE, MAX_HEALTH, MAX_WEIGHT,
            inventory, activeRoom);
  }

  @Override
  public boolean isActive() {
    return this.health > MIN_HEALTH;
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
    if (score < MIN_SCORE)
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
    if (health <= HealthStatus.ZERO_HEALTH.getMaxHealth()) {
      return HealthStatus.ZERO_HEALTH;
    } else if (health <= HealthStatus.LOW_HEALTH.getMaxHealth()) {
      return HealthStatus.LOW_HEALTH;
    } else if (health <= HealthStatus.HIGH_HEALTH.getMaxHealth()) {
      return HealthStatus.HIGH_HEALTH;
    }
    return HealthStatus.FULL_HEALTH;
  }

  @Override
  public UseSuccessful useItem(String itemName) {
    Puzzle enemy = this.activeRoom.getRoomEnvironmentAffector();
    /*
     * short circuit if player doesn't have the item,
     * active room doesn't have the enemy, or enemy is already deactivated.
     */
    if (!inventory.containsKey(itemName.toLowerCase())) {
      return new UseSuccessful(itemName + " not found within inventory!\n", false);
    }
    /*
      return new UseSuccessful("You can't use " + itemName + " on this puzzle.", false);

     */
    Item item = inventory.get(itemName.toLowerCase());
    UseSuccessful wasUseSuccessful = item.use(enemy);
    if (wasUseSuccessful.getUseSuccessful())
      this.addToScore(enemy.getScore());
    return wasUseSuccessful;
  }

  @Override
  public TakeItemStatus takeItem(String item) {
    Item itemToPickUp = this.activeRoom.getItem(item.toLowerCase());
    if (itemToPickUp == null )
      return TakeItemStatus.ITEM_NOT_FOUND;

    if (itemToPickUp.getWeight() + this.currentWeight > this.maxWeight)
      return TakeItemStatus.ITEM_NOT_ADDED_OVER_CAPACITY;

    //add item to Player's inventory.
    this.inventory.put(itemToPickUp.getName().toLowerCase(), this.activeRoom.removeItem(item));
    //increment currentWeight by itemToPickUp's weight.
    this.currentWeight += itemToPickUp.getWeight();

    //Increase Player's score the first time they pick up an item.
    if (itemsAdded.add(itemToPickUp.getName().toLowerCase()))
      this.addToScore(itemToPickUp.getScore());

    return TakeItemStatus.ITEM_ADDED;
  }

  @Override
  public boolean dropItem(String item) {
    //Item not in Player's inventory
    if (!this.inventory.containsKey(item.toLowerCase()))
      return false;

    //remove Item from inventory.
    Item droppedItem = this.inventory.remove(item.toLowerCase());
    //decrement currentWeight by droppedItem's weight.
    this.currentWeight -= droppedItem.getWeight();
    //add droppedItem to Room player is in.
    this.activeRoom.addItem(droppedItem);
    return true;
  }

  @Override
  public String examine(String element) {
    //Player either examines Item in their inventory or Item in activeRoom
    Item itemToExamine = this.inventory.get(element.toLowerCase()) != null
            ? this.inventory.get(element.toLowerCase())
            : this.activeRoom.getItem(element);
    Fixture fixtureToExamine = this.activeRoom.getFixture(element);
    String description = "You cannot see or examine " +  element;
    if (itemToExamine != null)
      description = itemToExamine.getDescription();
    else if (fixtureToExamine != null)
      description = fixtureToExamine.getDescription();
    return description;
  }

  @Override
  public UseSuccessful answer(String answer) {
    Puzzle enemy = this.activeRoom.getRoomEnvironmentAffector();
    if (enemy == null)
      return new UseSuccessful("No Puzzle or Monster exists in the current room"
              + " to use the answer on!", false);

    //short circuit if enemy is already deactivated.
    if (!enemy.isActive())
      return new UseSuccessful(enemy.getName() + " already deactivated!", false);

    boolean enemySolved = enemy.solve(answer.toLowerCase());
    if (enemySolved)
      this.addToScore(enemy.getScore());
    return new UseSuccessful(enemy.getName() + " was deactivated by " + answer + "!", true);
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
