package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

/**
 * The ConcretePuzzleTest class tests the ConcretePuzzle class methods. Since ConcretePuzzle
 * only has a constructor that is all that is tested. All of its other methods are inherited
 * from AbstractEnemy and tested in its test class.
 */
class ConcretePuzzleTest {

  /**
   * The testConstructor() method tests the constructor when given arguments that are
   * expected to be correct.
   */
  @Test
  void testConstructor() {
    Puzzle p1 = new ConcretePuzzle("DARKNESS", "Darkness! You cannot see!",
            true, true, "6:Kitchen", true, null,
            "Lamp", 150,
            "It's dark! You cannot see anything! Maybe we should go back?",
            0.0, "darkness.png");

    assertInstanceOf(ConcretePuzzle.class, p1);

    ConcretePuzzle cp1 = new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, "4:Spooky Library", false, null,
            "Modulo 2", 400, """
            Books are rustling by themselves on the \
            bookshelf. That's a weird bookshelf. Really weird.
            You hear a voice whisper: \
            "~Find Even Numbers Only~"\s
            Yikes. That's creepy. Maybe we should leave?""",
            5.5, null);

    assertInstanceOf(ConcretePuzzle.class, p1);
    assertEquals("MOD-SPOOKY-VOICE", cp1.getName());
    assertEquals(400, cp1.getScore());

  }
}