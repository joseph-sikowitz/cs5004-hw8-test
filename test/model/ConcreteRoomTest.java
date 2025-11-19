package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The ConcreteRoomTest class tests the methods of the ConcreteRoom class.
 */
class ConcreteRoomTest {
  private Puzzle p1;
  private Monster m1;
  private Item i1;
  private Item i2;
  private Fixture f1;

  private Room r1;
  private Room r2;
  private Room r3;

  /**
   * The setUp() method creates game objects for later testing within a Room.
   */
  @BeforeEach
  void setUp() {
    p1 = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "3:The Dunk", true, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");

    m1 = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "1:room1",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    i1 = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");

    i2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");

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
    passages2.put(Directions.EAST, 3);
    passages2.put(Directions.WEST, 0);

    Map<Directions, Integer> passages3 = new HashMap<>();
    passages3.put(Directions.NORTH, 0);
    passages3.put(Directions.SOUTH, 0);
    passages3.put(Directions.EAST, -4);
    passages3.put(Directions.WEST, 2);

    Map<String, Item> rItems = new HashMap<>();
    rItems.put("lamp", i1);
    rItems.put("thumb drive", i2);

    Map<String, Fixture> rFixtures = new HashMap<>();
    rFixtures.put("bookshelf", f1);

    r1 = new ConcreteRoom("room1", "empty room", 1, passages1,
            rItems, rFixtures, m1, null, "test.png");

    r2 = new ConcreteRoom("Chamber of Secrets", "Tom Riddle is here", 2, passages2,
            rItems, rFixtures, null, null, "chamber.png");

    r3 = new ConcreteRoom("The Dunk", "Now named Amica Center", 3, passages3,
            rItems, rFixtures, null, p1, "dunkin.png");
  }

  /**
   * Tests the constructor for a ConcreteRoom with valid input.
   */
  @Test
  void testConstructor() {
    final Monster mon = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "1:room1",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    final Item it1 = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");

    final Item it2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");

    final  Fixture fi1 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, null, null, "pictures/bookshelf.jpg");

    Map<Directions, Integer> passages1 = new HashMap<>();
    passages1.put(Directions.NORTH, 0);
    passages1.put(Directions.SOUTH, 2);
    passages1.put(Directions.EAST, -5);
    passages1.put(Directions.WEST, 0);

    Map<String, Item> rItems = new HashMap<>();
    rItems.put("lamp", it1);
    rItems.put("thumb drive", it2);

    Map<String, Fixture> rFixtures = new HashMap<>();
    rFixtures.put("bookshelf", fi1);

    r1 = new ConcreteRoom("room1", "empty room", 1, passages1,
            rItems, rFixtures, mon, null, "test.png");
  }

  /**
   * Tests that the constructor for a ConcreteRoom with an invalid room number throws an
   * IllegalArgumentException.
   */
  @Test
  void testConstructorInvalidRoomNumber() {
    final Monster mon = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "1:room1",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    final Item it1 = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");

    final Item it2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");

    final Fixture fi1 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, null, null, "pictures/bookshelf.jpg");

    Map<Directions, Integer> passages1 = new HashMap<>();
    passages1.put(Directions.NORTH, 0);
    passages1.put(Directions.SOUTH, 2);
    passages1.put(Directions.EAST, -5);
    passages1.put(Directions.WEST, 0);

    Map<String, Item> rItems = new HashMap<>();
    rItems.put("lamp", it1);
    rItems.put("thumb drive", it2);

    Map<String, Fixture> rFixtures = new HashMap<>();
    rFixtures.put("bookshelf", fi1);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcreteRoom("room1", "empty room", 0, passages1,
            rItems, rFixtures, mon, null, "test.png"));

    assertThrows(IllegalArgumentException.class,
            () -> new ConcreteRoom("room1", "empty room", -2, passages1,
                    rItems, rFixtures, mon, null, "test.png"));
  }

  /**
   * Tests that the constructor for a ConcreteRoom with either null or incorrect passages
   * throws an IllegalArgumentException.
   */
  @Test
  void testConstructorInvalidPassages() {
    final Monster mon = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "1:room1",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    final Item it1 = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");

    final Item it2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");

    final Fixture fi1 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, null, null, "pictures/bookshelf.jpg");

    Map<Directions, Integer> passages1 = new HashMap<>();
    passages1.put(Directions.NORTH, 0);
    passages1.put(Directions.SOUTH, 2);
    passages1.put(Directions.EAST, -5);

    Map<String, Item> rItems = new HashMap<>();
    rItems.put("lamp", it1);
    rItems.put("thumb drive", it2);

    Map<String, Fixture> rFixtures = new HashMap<>();
    rFixtures.put("bookshelf", fi1);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcreteRoom("room1", "empty room", 1, null,
                    rItems, rFixtures, mon, null, "test.png"));

    assertThrows(IllegalArgumentException.class,
            () -> new ConcreteRoom("room1", "empty room", 1, passages1,
                    rItems, rFixtures, mon, null, "test.png"));
  }

  /**
   * Tests that the constructor for a ConcreteRoom with two passages to the same room from
   * the current room throws an IllegalArgumentException.
   */
  @Test
  void testConstructorInvalidDoublePassages() {
    final Monster mon = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "1:room1",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    final Item it1 = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");

    final Item it2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");

    final Fixture fi1 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, null, null, "pictures/bookshelf.jpg");

    Map<Directions, Integer> passages1 = new HashMap<>();
    passages1.put(Directions.NORTH, 0);
    passages1.put(Directions.SOUTH, 2);
    passages1.put(Directions.EAST, -5);
    passages1.put(Directions.WEST, 2);

    Map<String, Item> rItems = new HashMap<>();
    rItems.put("lamp", it1);
    rItems.put("thumb drive", it2);

    Map<String, Fixture> rFixtures = new HashMap<>();
    rFixtures.put("bookshelf", fi1);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcreteRoom("room1", "empty room", 1, passages1,
                    rItems, rFixtures, mon, null, "test.png"));
  }

  /**
   * Tests that the constructor for a ConcreteRoom with two puzzles (puzzle and monster) in the
   * same room throws an IllegalArgumentException.
   */
  @Test
  void testConstructorTwoPuzzles() {
    final Puzzle puz = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "3:The Dunk", true, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");

    final Monster mon = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "1:room1",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    final Item it1 = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");

    final Item it2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");

    final Fixture fi1 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, null, null, "pictures/bookshelf.jpg");

    Map<Directions, Integer> passages1 = new HashMap<>();
    passages1.put(Directions.NORTH, 0);
    passages1.put(Directions.SOUTH, 2);
    passages1.put(Directions.EAST, -5);
    passages1.put(Directions.WEST, 0);

    Map<String, Item> rItems = new HashMap<>();
    rItems.put("lamp", it1);
    rItems.put("thumb drive", it2);

    Map<String, Fixture> rFixtures = new HashMap<>();
    rFixtures.put("bookshelf", fi1);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcreteRoom("room1", "empty room", 1, passages1,
                    rItems, rFixtures, mon, puz, "test.png"));
  }

  /**
   * Tests that the constructor for a ConcreteRoom a puzzle (monster) affecting another room
   * throws an IllegalArgumentException.
   */
  @Test
  void testConstructorPuzzleAffectingOtherRoom() {
    final Monster mon = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "2:chamber",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    final Item it1 = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");

    final Item it2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");

    final Fixture fi1 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, null, null, "pictures/bookshelf.jpg");

    Map<Directions, Integer> passages1 = new HashMap<>();
    passages1.put(Directions.NORTH, 0);
    passages1.put(Directions.SOUTH, 2);
    passages1.put(Directions.EAST, -5);
    passages1.put(Directions.WEST, 0);

    Map<String, Item> rItems = new HashMap<>();
    rItems.put("lamp", it1);
    rItems.put("thumb drive", it2);

    Map<String, Fixture> rFixtures = new HashMap<>();
    rFixtures.put("bookshelf", fi1);

    assertThrows(IllegalArgumentException.class,
            () -> new ConcreteRoom("room1", "empty room", 1, passages1,
                    rItems, rFixtures, mon, null, "test.png"));
  }

  /**
   * Tests the description getter which should be obscured when a monster is in the room.
   */
  @Test
  void testGetDescription() {
    assertEquals("A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", r1.getDescription());
    assertEquals("Tom Riddle is here", r2.getDescription());
  }

  /**
   * Tests the room number getter for a Room.
   */
  @Test
  void testGetRoomNumber() {
    assertEquals(1, r1.getRoomNumber());
    assertEquals(2, r2.getRoomNumber());
  }

  /**
   * Tests the getter for passage values in each direction.
   */
  @Test
  void testGetPassageValue() {
    assertEquals(0, r1.getPassageValue(Directions.NORTH));
    assertEquals(2, r1.getPassageValue(Directions.SOUTH));
    assertEquals(-5, r1.getPassageValue(Directions.EAST));
    assertEquals(0, r1.getPassageValue(Directions.WEST));
  }

  /**
   * Tests the Fixture getter for a room. When a room is being affected by a Puzzle type,
   * the getter returns null.
   */
  @Test
  void testGetFixture() {
    assertEquals("Bookshelf", r2.getFixture("Bookshelf").getName());
    assertNull(r1.getFixture("Bookshelf"));
  }

  /**
   * Tests the Items getter for a room. When the room is being affect by a Puzzle type,
   * the getter returns null.
   */
  @Test
  void testGetItem() {
    assertEquals("Lamp", r2.getItem("lamp").getName());
    assertEquals("Thumb Drive", r2.getItem("thumb drive").getName());
    assertNull(r1.getItem("lamp"));
  }

  /**
   * Tests the Monster getter for a room.
   */
  @Test
  void testGetMonster() {
    assertEquals("Rabbit", r1.getMonster().getName());
  }

  /**
   * Tests the Puzzle getter for a room.
   */
  @Test
  void testGetPuzzle() {
    assertEquals("DARKNESS", r3.getPuzzle().getName());
  }

  /**
   * Tests the getter for a room's environment affector. It can be a monster or a puzzle.
   */
  @Test
  void testGetRoomEnvironmentAffector() {
    assertEquals("Rabbit", r1.getRoomEnvironmentAffector().getName());
    assertEquals("DARKNESS", r3.getRoomEnvironmentAffector().getName());
    assertNull(r2.getRoomEnvironmentAffector());
  }

  /**
   * Tests that an affector in a room will return the correct boolean when it is present
   * and affects a player or not.
   */
  @Test
  void affectorAffectsPlayer() {
    assertTrue(r1.affectorAffectsPlayer());
    assertFalse(r2.affectorAffectsPlayer());
    assertTrue(r3.affectorAffectsPlayer());
  }

  /**
   * Tests if an item can be added to a room.
   */
  @Test
  void testAddItem() {
    Item i3 = new ConcreteItem("IPhone", "State of the art", 600,
            2, null, 1000, 876, "You answer the phone");
    r2.addItem(i3);
    assertEquals("IPhone", r2.getItem("iphone").getName());
  }

  /**
   * Tests that an item can be removed from a room.
   */
  @Test
  void testRemoveItem() {
    Item lamp = r2.removeItem("Lamp");
    assertEquals("Lamp", lamp.getName());
    assertNull(r2.getItem("lamp"));
  }

  /**
   * Tests the getter for returning a room by the direction it is in from an
   * adjacent room.
   */
  @Test
  void testGetPassageRoom() {
    Room room = r1.getPassageRoom(Directions.SOUTH);
    assertEquals("Chamber of Secrets", room.getName());
  }

  /**
   * Tests that the getter for a room's passages returns a Map with the correct
   * directions and corresponding values.
   */
  @Test
  void testGetPassages() {
    Map<Directions, Integer> passages = r1.getPassages();
    assertEquals(0, passages.get(Directions.NORTH));
    assertEquals(2, passages.get(Directions.SOUTH));
    assertEquals(-5, passages.get(Directions.EAST));
    assertEquals(0, passages.get(Directions.WEST));
  }

  /**
   * Tests the getter for a room's items Map.
   */
  @Test
  void testGetItems() {
    Map<String, Item> items = r2.getItems();
    assertEquals(2, items.size());
    assertEquals("Lamp", items.get("lamp").getName());
    assertEquals("Thumb Drive", items.get("thumb drive").getName());
  }

  /**
   * Tests the getter for a room's fixtures Map.
   */
  @Test
  void testGetFixtures() {
    Map<String, Fixture> fixtures = r2.getFixtures();
    assertEquals(1, fixtures.size());
    assertEquals("Bookshelf", fixtures.get("bookshelf").getName());
  }

  /**
   * Tests the getter for a room's picture file path.
   */
  @Test
  void testGetPicturePath() {
    assertEquals("test.png", r1.getPicturePath());
    assertEquals("chamber.png", r2.getPicturePath());
    assertEquals("dunkin.png", r3.getPicturePath());
  }

  /**
   * Tests the getter for a room's true description. This should provide the underlying
   * room's description even if there is something affecting the room that is active.
   */
  @Test
  void testGetTrueDescription() {
    assertEquals("empty room", r1.getTrueDescription());
    assertEquals("Now named Amica Center", r3.getTrueDescription());
  }

}