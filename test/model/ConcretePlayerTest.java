package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The ConcretePlayerTest class tests the methods of the ConcretePlayer class.
 */
class ConcretePlayerTest {
  private Puzzle p1;
  private Monster m1;
  private Item i1;
  private Item i2;
  private Item i3;
  private Item i4;
  private Item i5;
  private Fixture f1;

  private Room r1;
  private Room r2;
  private Room r3;

  private Player player1;
  private Player player2;

  /**
   * The setUp() method creates objects to be used for later testing.
   */
  @BeforeEach
  void setUp() {
    p1 = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "2:Chamber of Secrets", true, "hello",
            null, 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");

    m1 = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "1:room1",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    i1 = new ConcreteItem("Carrot", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");

    i2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");

    i3 = new ConcreteItem("Keyboard", "a typing thing", 150,
            800, null, 1000, 1000, "You type away");

    i4 = new ConcreteItem("Mouse", "a clicking thing", 150,
            1, null, 1000, 1000, "You type away");

    i5 = new ConcreteItem("Laptop", "a typing thing", 150,
            1, null, 1000, 1000, "You type away");

    f1 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, null, null, "pictures/bookshelf.jpg");

    Map<Directions, Integer> passages1 = new HashMap<>();
    passages1.put(Directions.NORTH, 0);
    passages1.put(Directions.SOUTH, 2);
    passages1.put(Directions.EAST, -5);
    passages1.put(Directions.WEST, 0);

    Map<Directions, Integer> passages2 = new HashMap<>();
    passages2.put(Directions.NORTH, 1);
    passages2.put(Directions.SOUTH, 0);
    passages2.put(Directions.EAST, -3);
    passages2.put(Directions.WEST, 0);

    Map<Directions, Integer> passages3 = new HashMap<>();
    passages3.put(Directions.NORTH, 0);
    passages3.put(Directions.SOUTH, 0);
    passages3.put(Directions.EAST, -4);
    passages3.put(Directions.WEST, 2);

    Map<String, Item> rItems = new HashMap<>();
    rItems.put("carrot", i1);
    rItems.put("thumb drive", i2);
    rItems.put("keyboard", i3);
    rItems.put("mouse", i4);
    rItems.put("laptop", i5);

    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    Map<String, Fixture> rFixtures = new HashMap<>();
    rFixtures.put("bookshelf", f1);

    r1 = new ConcreteRoom("room1", "empty room", 1, passages1,
            rItems, rFixtures, m1, null, "test.png");

    r2 = new ConcreteRoom("Chamber of Secrets", "Tom Riddle is here", 2, passages2,
            null, null, null, p1, "chamber.png");

    r3 = new ConcreteRoom("The Dunk", "Now named Amica Center", 3, passages3,
            null, null, null, null, "dunkin.png");

    player1 = new ConcretePlayer("Joe", "A new player", 0.0,
            100.0, 13.0, pItems, r1, new HashSet<>());

    player2 = new ConcretePlayer("Vasilios", "A new player", 0.0,
            0.0, 6.2, new HashMap<>(), r1, new HashSet<>());
  }

  /**
   * Tests the first constructor with valid input.
   */
  @Test
  void testFirstConstructor() {
    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    Player player = new ConcretePlayer("Joe", "A new player", 0.0,
            100.0, 13.0, pItems, r1, new HashSet<>());

    assertEquals("Joe", player.getName());
    assertEquals(100.0, player.getHealth());
    assertEquals(0.0, player.getScore());
    assertEquals(r1, player.getActiveRoom());
  }

  /**
   * Tests the second overloaded constructor with valid input.
   */
  @Test
  void testSecondConstructor() {
    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    Player player = new ConcretePlayer("Joe", pItems, r1);

    assertEquals("Joe", player.getName());
    assertEquals(r1, player.getActiveRoom());
    assertEquals(0.0, player.getScore());
    assertEquals(100.0, player.getHealth());
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException when given a score
   * below zero.
   */
  @Test
  void testFirstConstructorLowScore() {
    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcretePlayer("Joe", "A new player", -3.0,
            100.0, 13.0, pItems, r1, new HashSet<>()));
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException when given a
   * health number below zero or above 100.
   */
  @Test
  void testFirstConstructorHealthOutOfBounds() {
    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcretePlayer("Joe", "A new player", 0.0,
                    -2.0, 13.0, pItems, r1, new HashSet<>()));

    assertThrows(IllegalArgumentException.class,
            () -> new ConcretePlayer("Joe", "A new player", 0.0,
                    101.0, 13.0, pItems, r1, new HashSet<>()));
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException when the weight
   * of the inventory is more than the max weight.
   */
  @Test
  void testFirstConstructorWeightOverInventory() {
    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcretePlayer("Joe", "A new player", 0.0,
            100.0, 0.0, pItems, r1, new HashSet<>()));
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException when given a weight
   * below zero.
   */
  @Test
  void testFirstConstructorWeightBelowZero() {
    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcretePlayer("Joe", "A new player", 0.0,
                    100.0, -2.0, pItems, r1, new HashSet<>()));
  }

  /**
   * Tests that the constructor clamps the max weight to 13.0 when given a higher
   * max weight.
   */
  @Test
  void testFirstConstructorWeightClamping() {
    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    Player player = new ConcretePlayer("Joe", "A new player", 0.0,
                    100.0, 14.0, pItems, r1, new HashSet<>());
    assertEquals(13.0, player.getMaxWeight());
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException when inventory is
   * null.
   */
  @Test
  void testFirstConstructorNullInventory() {
    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcretePlayer("Joe", "A new player", 0.0,
                    100.0, 12.0, null, r1, new HashSet<>()));
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException when the active room
   * is null.
   */
  @Test
  void testFirstConstructorNullActiveRoom() {
    Map<String, Item> pItems = new HashMap<>();
    pItems.put("carrot", i1);
    pItems.put("thumb drive", i2);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcretePlayer("Joe", "A new player", 0.0,
                    100.0, 12.0, pItems, null, new HashSet<>()));
  }

  /**
   * Tests that a player is active when they have health above zero and are not active
   * when their health is at zero or below.
   */
  @Test
  void testIsActive() {
    assertTrue(player1.isActive());
    assertFalse(player2.isActive());
  }

  /**
   * Tests that a player gets the various possible enums when attempting to walk to
   * rooms in each possible state.
   */
  @Test
  void testWalk() {
    assertEquals(RoomStatus.OPEN, player1.walk(Directions.SOUTH));
    assertEquals(RoomStatus.NO_PASSAGE, player1.walk(Directions.WEST));
    assertEquals(RoomStatus.BLOCKED, player1.walk(Directions.EAST));
  }

  /**
   * Tests the setter for a player's score.
   */
  @Test
  void testSetScore() {
    player1.setScore(110);
    assertEquals(110, player1.getScore());
    player1.setScore(24000.1254);
    assertEquals(24000.1254, player1.getScore());
    player1.setScore(6.5);
    assertEquals(6.5, player1.getScore());
    player1.setScore(0);
    assertEquals(0, player1.getScore());
    player1.setScore(-9.8);
    assertEquals(-9.8, player1.getScore());
  }

  /**
   * Tests the adder for the player's score with various amounts.
   */
  @Test
  void testAddToScore() {
    player1.addToScore(10);
    assertEquals(10, player1.getScore());
    player1.addToScore(16667.9084);
    assertEquals(16677.9084, player1.getScore());
    player1.addToScore(0);
    assertEquals(16677.9084, player1.getScore());
    player1.addToScore(1);
    assertEquals(16678.9084, player1.getScore());
  }

  /**
   * Tests that addToScore() throws an IllegalArgumentException when given a negative
   * number.
   */
  @Test
  void testAddNegativeToScore() {
    assertThrows(IllegalArgumentException.class, () -> player1.addToScore(-5));
  }

  /**
   * Tests the getter for a player's health before and after changing it.
   */
  @Test
  void testGetHealth() {
    assertEquals(100.0, player1.getHealth());
    player1.changeHealth(-5);
    assertEquals(95.0, player1.getHealth());

    assertEquals(0.0, player2.getHealth());
    player2.changeHealth(55.0);
    assertEquals(55.0, player2.getHealth());
  }

  /**
   * Tests that changing a player's health will change it and that it can't go over
   * the maximum health of one-hundred.
   */
  @Test
  void testChangeHealth() {
    player1.changeHealth(5);
    assertEquals(100.0, player1.getHealth());

    player1.changeHealth(-5);
    assertEquals(95.0, player1.getHealth());

    player1.changeHealth(-55.0);
    assertEquals(40.0, player1.getHealth());

    player1.changeHealth(-45.0);
    assertEquals(-5.0, player1.getHealth());
  }

  /**
   * Tests that the correct HealthStatus enums are returned for each of the
   * four tiers of a player's possible health.
   */
  @Test
  void testGetHealthStatus() {
    assertEquals(HealthStatus.FULL_HEALTH, player1.getHealthStatus());
    assertEquals(HealthStatus.ZERO_HEALTH, player2.getHealthStatus());

    player1.changeHealth(-30);
    assertEquals(HealthStatus.HIGH_HEALTH, player1.getHealthStatus());

    player1.changeHealth(-30);
    assertEquals(HealthStatus.LOW_HEALTH, player1.getHealthStatus());
  }

  /**
   * Tests the useItem() method that uses an item on a Puzzle (monster or puzzle). It checks
   * for the response when the player does not possess the item and when they do.
   */
  @Test
  void testUseItem() {
    UseSuccessful us = player1.useItem("fork");
    assertFalse(us.getUseSuccessful());

    UseSuccessful us2 = player1.useItem("carrot");
    assertTrue(us2.getUseSuccessful());
    assertEquals(300.0, player1.getScore());
  }

  /**
   * The testTakeItem() method tests a player picking up items in a room and adding
   * them to inventory.
   */
  @Test
  void testTakeItem() {
    m1.solve(i1);
    // item not in room
    assertEquals(TakeItemStatus.ITEM_NOT_FOUND, player2.takeItem("fork"));

    // item added
    assertEquals(TakeItemStatus.ITEM_ADDED, player2.takeItem("thumb drive"));
    assertEquals(150, player2.getScore());

    // item too heavy
    assertEquals(TakeItemStatus.ITEM_NOT_ADDED_OVER_CAPACITY, player2.takeItem("keyboard"));
  }

  /**
   * Tests that dropItem() returns the correct boolean when given an item in the player's
   * inventory and when given one that's not or that is null.
   */
  @Test
  void testDropItem() {
    assertFalse(player1.dropItem("fork"));
    assertFalse(player1.dropItem(null));
    assertTrue(player1.dropItem("thumb drive"));
  }

  /**
   * Tests that the examine() method can examine an item or a fixture and return
   * their description.
   */
  @Test
  void testExamine() {
    m1.solve(i1);
    assertEquals(i1.getDescription(), player1.examine("carrot"));
    assertEquals(f1.getDescription(), player1.examine("Bookshelf"));
  }

  /**
   * Tests that a player can provide a successful and unsuccessful answer to a puzzle
   * and get the correct result.
   */
  @Test
  void testAnswer() {
    player1.walk(Directions.SOUTH);
    UseSuccessful us1 = player1.answer("fork");
    assertFalse(us1.getUseSuccessful());

    UseSuccessful us2 = player1.answer("hello");
    assertTrue(us2.getUseSuccessful());
  }

  /**
   * Tests the getter for the player's active room.
   */
  @Test
  void testGetActiveRoom() {
    assertEquals(r1, player1.getActiveRoom());
    player1.walk(Directions.SOUTH);
    assertEquals(r2, player1.getActiveRoom());
  }

  /**
   * Tests the getter for the player's score as it changes.
   */
  @Test
  void testGetScore() {
    assertEquals(0.0, player1.getScore());
    player1.setScore(25.0);
    assertEquals(25.0, player1.getScore());
    player1.setScore(3.0);
    assertEquals(3.0, player1.getScore());
    player1.setScore(0.0);
    assertEquals(0.0, player1.getScore());
    player1.setScore(-88.4);
    assertEquals(-88.4, player1.getScore());
  }

  /**
   * Tests the getter for the player's inventory by checking for the correct size
   * and item objects.
   */
  @Test
  void testGetInventory() {
    Map<String, Item> inventory = player1.getInventory();
    assertFalse(inventory.isEmpty());
    assertEquals(2, player1.getInventory().size());
    assertSame(i1, player1.getInventory().get("carrot"));
    assertSame(i2, player1.getInventory().get("thumb drive"));

    Map<String, Item> inventory2 = player2.getInventory();
    assertTrue(inventory2.isEmpty());
  }

  /**
   * Tests the getter for a player's max weight.
   */
  @Test
  void testGetMaxWeight() {
    assertEquals(13.0, player1.getMaxWeight());
    assertEquals(6.2, player2.getMaxWeight());
  }

  /**
   * Tests that items are recorded and that the getter for items added returns the
   * items as they are added to the Set.
   */
  @Test
  void testGetItemsAdded() {
    m1.solve(i1);
    assertTrue(player1.getItemsAdded().isEmpty());
    player1.takeItem("mouse");
    assertTrue(player1.getItemsAdded().contains("mouse"));
    player1.takeItem("laptop");
    assertTrue(player1.getItemsAdded().contains("laptop"));
    assertTrue(player1.getItemsAdded().contains("mouse"));
  }
}