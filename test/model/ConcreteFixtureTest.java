package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The ConcreteFixtureTest class tests the methods of the ConcreteFixture class.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
class ConcreteFixtureTest {

  private ConcreteFixture f1;
  private Fixture f2;
  private ConcreteFixture f3;
  private Puzzle p1;

  /**
   * The setUp() method creates a Puzzle object to be passed to ConcreteFixture constructors
   * and ConcreteFixture objects to be used in later tests.
   */
  @BeforeEach
  void setUp() {
    p1 = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
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
            false,true, "6:Kitchen", true, null,
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
    ConcreteFixture cf = new ConcreteFixture("Stove", "A place to cook a meal",
            200.0, null, "Maine, New Hampshire, Vermont",
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
   * The testGetDescriptionActivePuzzle() method tests the getDescription() method override
   * in ConcreteFixture when passed an active puzzle.
   */
  @Test
  void testGetDescriptionActivePuzzle() {
    Puzzle puzzle = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "6:Kitchen", true, null,
            "Lamp", 200.0,
            "It's dark! You cannot see anything! Maybe we should go back?", 0.0,
            "darkness.png");

    ConcreteFixture cf = new ConcreteFixture("Stove", "A place to cook a meal",
            3000.5, puzzle, "Maine, New Hampshire, Vermont",
            "pictures/stove.png");

    assertEquals("It's dark! You cannot see anything! Maybe we should go back?",
            cf.getDescription());
  }

  /**
   * The testGetDescriptionInactivePuzzle() method tests the getDescription() method override
   * in ConcreteFixture when passed an inactive puzzle.
   */
  @Test
  void testGetDescriptionInactivePuzzle() {
    Puzzle puzzle = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            false, true, "6:Kitchen", true, null,
            "Lamp", 200.0,
            "It's dark! You cannot see anything! Maybe we should go back?", 0.0,
            "darkness.png");

    ConcreteFixture cf = new ConcreteFixture("Stove", "A place to cook a meal",
            3000.5, puzzle, "Maine, New Hampshire, Vermont",
            "pictures/stove.png");

    assertEquals("A place to cook a meal", cf.getDescription());
  }

  /**
   * The testGetDescriptionNullPuzzle() method tests the getDescription() method override
   * in ConcreteFixture when passed a null puzzle.
   */
  @Test
  void testGetDescriptionNullPuzzle() {
    ConcreteFixture cf = new ConcreteFixture("Stove", "A place to cook a meal",
            3000.5, null, "Maine, New Hampshire, Vermont",
            "pictures/stove.png");

    assertEquals("A place to cook a meal", cf.getDescription());
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

  /**
   * The testGetStates() method tests the getter getStates().
   */
  @Test
  void testGetStates() {
    assertEquals("Solid|Liquid|Gas", f2.getStates());
    assertEquals("Connecticut; Rhode Island; Mass", f3.getStates());
  }

  /**
   * Tests the getter for the fixture's puzzle.
   */
  @Test
  void testGetPuzzle() {
    assertNull(f2.getPuzzle());
    assertEquals(p1, f3.getPuzzle());
  }

  /**
   * Tests the getter for the fixture's true description, unobscured by a puzzle or monster.
   */
  @Test
  void testGetTrueDescription() {
    assertEquals("It's dark! You cannot see anything! Maybe we should go back?",
            f1.getDescription());
    assertEquals("A bookshelf filled with books of magic", f1.getTrueDescription());
  }

  /**
   * Tests that affectorAffectsPlayer returns correct boolean based on its status.
   */
  @Test
  void testAffectorAffectsPlayer() {
    Puzzle puzz = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "6:Kitchen", true, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");
    Fixture fix = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, puzz, null, "pictures/bookshelf.jpg");
    assertTrue(fix.affectorAffectsPlayer());

    Puzzle puzz2 = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            false, true, "6:Kitchen", true, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");
    Fixture fix2 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, puzz2, null, "pictures/bookshelf.jpg");
    assertFalse(fix2.affectorAffectsPlayer());

    Puzzle puzz3 = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "6:Kitchen", false, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");
    Fixture fix3 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, puzz3, null, "pictures/bookshelf.jpg");
    assertFalse(fix3.affectorAffectsPlayer());

    Fixture fix4 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, null, null, "pictures/bookshelf.jpg");
    assertFalse(fix4.affectorAffectsPlayer());

  }
}