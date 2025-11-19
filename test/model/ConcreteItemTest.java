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
 * The ConcreteItemTest class tests the methods of the ConcreteItem class.
 */
class ConcreteItemTest {

  private ConcreteItem i1;
  private Item i2;
  private ConcreteItem i3;
  private Puzzle p1;

  /**
   * The setUp() method instantiates ConcreteItem objects to be used in later testing.
   */
  @BeforeEach
  void setUp() {
    i1 = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");
    i2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");
    i3 = new ConcreteItem("Phone", "It makes calls", 55.5, 6.79, "phone.jpg",
            10, 1, "You answer the phone");

    p1 = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "6:Kitchen", true, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");
  }

  /**
   * The testConstructor() method tests the ConcreteItem constructor with valid argument
   * inputs.
   */
  @Test
  void testConstructor() {
    ConcreteItem item = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");
    assertInstanceOf(ConcreteItem.class, item);
    assertEquals("Lamp", item.getName());
    assertEquals("An old oil lamp with flint to spark.", item.getDescription());
    assertEquals(100, item.getScore());
    assertEquals("lamp.png", item.getPicturePath());
    assertEquals(100, item.getMaxUses());
    assertEquals(20, item.getUsesRemaining());
    assertEquals("You light the lamp with the flint.", item.use());
    assertTrue(item.isActive());
  }

  /**
   * The testConstructorZeroUsesRemaining() method tests that the constructor sets the
   * active status of the ConcreteItem to false if uses remaining is zero.
   */
  @Test
  void testConstructorZeroUsesRemaining() {
    ConcreteItem item = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 0,
            "You light the lamp with the flint.");
    assertFalse(item.isActive());
  }

  /**
   * The testConstructorNegativeUsesRemaining() method tests that the constructor sets the
   * active status of the ConcreteItem to false if uses remaining is negative.
   */
  @Test
  void testConstructorNegativeUsesRemaining() {
    ConcreteItem item = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, -9,
            "You light the lamp with the flint.");
    assertFalse(item.isActive());
  }

  /**
   * The testConstructorNegativeWeight() method tests that the constructor throws an
   * IllegalArgumentException when given a negative weight.
   */
  @Test
  void testConstructorNegativeWeight() {
    assertThrows(IllegalArgumentException.class, () -> new ConcreteItem("Lamp",
            "An old oil lamp with flint to spark.",
            100, -99, "lamp.png", 100, 5,
            "You light the lamp with the flint."));
  }

  /**
   * The testGetMaxUses() method tests the getMaxUses() getter.
   */
  @Test
  void testGetMaxUses() {
    assertEquals(100, i1.getMaxUses());
    assertEquals(1000, i2.getMaxUses());
  }

  /**
   * The testGetUsesRemaining() method tests the getUsesRemaining() getter.
   */
  @Test
  void testGetUsesRemaining() {
    assertEquals(20, i1.getUsesRemaining());
    assertEquals(1000, i2.getUsesRemaining());
  }

  /**
   * The testUse() method tests the use() method of successful and unsuccessful item
   * uses. The use() method returns a UseSuccessful object that contains the use
   * description of the item as well as whether the use was successful. It decrements
   * the item's uses remaining whether successful or not.
   */
  @Test
  void testUse() {
    UseSuccessful result = i1.use(p1);
    assertEquals("You light the lamp with the flint.\nDARKNESS was deactivated by Lamp!",
            result.getUse());
    assertTrue(result.getUseSuccessful());
    assertEquals(19, i1.getUsesRemaining());

    UseSuccessful result2 = i2.use(p1);
    assertEquals("You insert the thumb drive.", result2.getUse());
    assertFalse(result2.getUseSuccessful());
    assertEquals(999, i2.getUsesRemaining());

    UseSuccessful result3 = i3.use(p1);
    assertEquals("You answer the phone", result3.getUse());
    assertFalse(result3.getUseSuccessful());
    assertEquals(0, i3.getUsesRemaining());
    assertFalse(i3.isActive());

    UseSuccessful result4 = i3.use(p1);
    assertFalse(result4.getUseSuccessful());
  }

  /**
   * The testUseNullEnemy() tests that the use() method returns false when given a null
   * value for enemy.
   */
  @Test
  void testUseNullEnemy() {
    UseSuccessful result = i1.use(null);
    assertFalse(result.getUseSuccessful());
  }

  /**
   * The testUseNoUsesLeft() tests that the use() method returns false when there are not more
   * uses allowed.
   */
  @Test
  void testUseNoUsesLeft() {
    i3.use(null);
    assertEquals(0, i3.getUsesRemaining());
    UseSuccessful result = i3.use(null);
    assertFalse(result.getUseSuccessful());


  }

  /**
   * The testGetPicturePath() method tests the getPicturePath() getter.
   */
  @Test
  void testGetPicturePath() {
    assertEquals("lamp.png", i1.getPicturePath());
    assertNull(i2.getPicturePath());
  }

  /**
   * The testGetScore() method tests the getScore() getter.
   */
  @Test
  void testGetScore() {
    assertEquals(100, i1.getScore());
    assertEquals(150, i2.getScore());
  }

  /**
   * The testGetWeight() method tests the getWeight() getter.
   */
  @Test
  void testGetWeight() {
    assertEquals(3, i1.getWeight());
    assertEquals(1, i2.getWeight());
  }

  /**
   * The testIsActive() method tests the isActive() getter.
   */
  @Test
  void testIsActive() {
    assertTrue(i1.isActive());
    assertTrue(i2.isActive());

    assertTrue(i3.isActive());
    i3.use(p1);
    assertFalse(i3.isActive());
  }

  /**
   * Tests the getter for the item's use description.
   */
  @Test
  void testGetUseDescription() {
    assertEquals("You light the lamp with the flint.", i1.getUseDescription());
  }
}