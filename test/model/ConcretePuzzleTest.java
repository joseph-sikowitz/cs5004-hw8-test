package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * The ConcretePuzzleTest class tests the ConcretePuzzle class methods. Since ConcretePuzzle
 * only has a constructor that is all that is tested. All of its other methods are inherited
 * from AbstractPuzzle and tested in its test class.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
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
            -5.5, null);

    assertInstanceOf(ConcretePuzzle.class, p1);
    assertEquals("MOD-SPOOKY-VOICE", cp1.getName());
    assertEquals(400, cp1.getScore());

  }

  /**
   * The testConstructorPositiveDamage() method tests that ConcretePuzzle throws an
   * IllegalArgumentException when passed a positive value for damage.
   */
  @Test
  void testConstructorPositiveDamage() {
    assertThrows(IllegalArgumentException.class, () -> new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, "4:Spooky Library", false, null,
            "Modulo 2", 400, """
            Books are rustling by themselves on the \
            bookshelf. That's a weird bookshelf. Really weird.
            You hear a voice whisper: \
            "~Find Even Numbers Only~"\s
            Yikes. That's creepy. Maybe we should leave?""",
            5.5, null));

  }

  /**
   * The testConstructorZeroDamage() method tests that a ConcretePuzzle can be instantiated
   * with damage set to zero.
   */
  @Test
  void testConstructorZeroDamage() {
    ConcretePuzzle cp = new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, "4:Spooky Library", false, null,
            "Modulo 2", 400, """
            Books are rustling by themselves on the \
            bookshelf. That's a weird bookshelf. Really weird.
            You hear a voice whisper: \
            "~Find Even Numbers Only~"\s
            Yikes. That's creepy. Maybe we should leave?""",
            0.0, null);

    assertInstanceOf(ConcretePuzzle.class, cp);
    assertEquals("MOD-SPOOKY-VOICE", cp.getName());
    assertEquals(400, cp.getScore());
  }

  /**
   * The testConstructorTargetNullOrEmpty() method tests that the constructor throws an
   * IllegalArgumentException when passed a null value or empty String for the target.
   */
  @Test
  void testConstructorTargetNullOrEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, "", false, null,
            "Modulo 2", 400, """
            Books are rustling by themselves on the \
            bookshelf. That's a weird bookshelf. Really weird.
            You hear a voice whisper: \
            "~Find Even Numbers Only~"\s
            Yikes. That's creepy. Maybe we should leave?""",
            -5.5, null));

    assertThrows(IllegalArgumentException.class, () -> new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, null, false, null,
            "Modulo 2", 400, """
            Books are rustling by themselves on the \
            bookshelf. That's a weird bookshelf. Really weird.
            You hear a voice whisper: \
            "~Find Even Numbers Only~"\s
            Yikes. That's creepy. Maybe we should leave?""",
            5.5, null));
  }

  /**
   * The testConstructorEffectsNullOrEmpty() method tests that the constructor throws an
   * IllegalArgumentException when passed a null value or empty String for effects.
   */
  @Test
  void testConstructorEffectsNullOrEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "A spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, "", false, null,
            "Modulo 2", 400, "", -5.5, null));

    assertThrows(IllegalArgumentException.class, () -> new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, null, false, null,
            "Modulo 2", 400,null,5.5, null));
  }

  /**
   * The testConstructorSolutionsNullOrEmpty() method tests that the constructor throws an
   * IllegalArgumentException when passed a null value or empty String or a combination of
   * the two for the solutionText and solutionItem fields.
   */
  @Test
  void testConstructorSolutionsNullOrEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, "4:Spooky Library", false, null,
            null, 400, """
            Books are rustling by themselves on the \
            bookshelf. That's a weird bookshelf. Really weird.
            You hear a voice whisper: \
            "~Find Even Numbers Only~"\s
            Yikes. That's creepy. Maybe we should leave?""",
            -5.5, null));


    assertThrows(IllegalArgumentException.class, () -> new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, "4:Spooky Library", false, null,
            "",400, """
            Books are rustling by themselves on the \
            bookshelf. That's a weird bookshelf. Really weird.
            You hear a voice whisper: \
            "~Find Even Numbers Only~"\s
            Yikes. That's creepy. Maybe we should leave?""",
            -5.5, null));

    assertThrows(IllegalArgumentException.class, () -> new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, "4:Spooky Library", false, "",
            null, 400, """
            Books are rustling by themselves on the \
            bookshelf. That's a weird bookshelf. Really weird.
            You hear a voice whisper: \
            "~Find Even Numbers Only~"\s
            Yikes. That's creepy. Maybe we should leave?""",
            -5.5, null));

    assertThrows(IllegalArgumentException.class, () -> new ConcretePuzzle("MOD-SPOOKY-VOICE",
            "An spooky, eerie library. You walked into this eerie library FROM the west. "
                    + "\nAnother room is north. Books are rustling by themselves on a bookshelf.",
            true, true, "4:Spooky Library", false, "",
            "", 400, """
            Books are rustling by themselves on the \
            bookshelf. That's a weird bookshelf. Really weird.
            You hear a voice whisper: \
            "~Find Even Numbers Only~"\s
            Yikes. That's creepy. Maybe we should leave?""",
            -5.5, null));
  }
}