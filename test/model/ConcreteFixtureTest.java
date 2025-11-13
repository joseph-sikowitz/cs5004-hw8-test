package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The ConcreteFixtureTest class tests the methods of the ConcreteFixture class.
 */
class ConcreteFixtureTest {

  private ConcreteFixture f1;
  private Fixture f2;
  private ConcreteFixture f3;

  /**
   * The setUp() method creates a Puzzle object to be passed to ConcreteFixture constructors
   * and ConcreteFixture objects to be used in later tests.
   */
  @BeforeEach
  void setUp() {
    Puzzle p1 = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "6:Kitchen", true, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");

    f1 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, p1, null, "pictures/bookshelf.jpg");
    f2 = new ConcreteFixture("Table", "A table with a computer and pen",
            200.0, null, "Solid|Liquid|Gas", "pictures/table.jpg");
    f3 = new ConcreteFixture("Mainframe", "A massive computer with many lights",
            546912.984367, p1, "Connecticut; Rhode Island; Mass",
            "pictures/mainframe.jpg");
  }

  /**
   * The testConstructor() method instantiates a ConcreteFixture object that should be
   * created without issues.
   */
  @Test
  void testConstructor() {
    Puzzle puzzle = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "6:Kitchen", true, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");

    ConcreteFixture cf = new ConcreteFixture("Stove", "A place to cook a meal",
            3000.5, puzzle, "Maine, New Hampshire, Vermont",
            "pictures/stove.png");

    assertInstanceOf(ConcreteFixture.class, cf);
    assertEquals("Stove", cf.getName());
    assertEquals("A place to cook a meal", cf.getDescription());
  }

  /**
   * The testConstructorAtMinimumWeight() method tests that the constructor can create
   * a ConcreteFixture object at the minimum weight of 200.0.
   */
  @Test
  void testConstructorAtMinimumWeight() {
    Puzzle puzzle = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "6:Kitchen", true, null,
            "Lamp", 200.0,
            "It's dark! You cannot see anything! Maybe we should go back?", 0.0,
            "darkness.png");

    ConcreteFixture cf = new ConcreteFixture("Stove", "A place to cook a meal",
            200.0, puzzle, "Maine, New Hampshire, Vermont",
            "pictures/stove.png");

    assertEquals(200.0, cf.getWeight());
  }

  /**
   * The testConstructorBelowWeight() method tests that the ConcreteFixture constructor
   * throws an IllegalArgumentException when given a weight below 200.0.
   */
  @Test
  void testConstructorBelowWeight() {
    Puzzle puzzle = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "6:Kitchen", true, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?", 0.0,
            "darkness.png");

    assertThrows(IllegalArgumentException.class, () -> new ConcreteFixture("Stove",
            "A place to cook a meal", 15.67, puzzle,
            "Maine, New Hampshire, Vermont", "pictures/stove.png"));

    assertThrows(IllegalArgumentException.class, () -> new ConcreteFixture("Stove",
            "A place to cook a meal", 199.99, puzzle,
            "Maine, New Hampshire, Vermont", "pictures/stove.png"));
  }

  /**
   * The testGetPicturePath() method tests that getPicturePath() returns the expected
   * file path.
   */
  @Test
  void testGetPicturePath() {
    assertEquals("pictures/bookshelf.jpg", f1.getPicturePath());
    assertEquals("pictures/table.jpg", f2.getPicturePath());
    assertEquals("pictures/mainframe.jpg", f3.getPicturePath());
  }

  /**
   * The testGetWeight() method tests that the getWeight() method returns the expected
   * weight.
   */
  @Test
  void testGetWeight() {
    assertEquals(250.0, f1.getWeight());
    assertEquals(200.0, f2.getWeight());
    assertEquals(546912.984367, f3.getWeight());
  }
}