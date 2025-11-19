package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AbstractPuzzleTest {
  private Puzzle testPuzzleWithAnswerSolution;
  private Puzzle testPuzzleWithItemSolution;
  private Item testItemSolution;
  private Item testItemNotSolution;

  @BeforeEach
  void setUp() {
    this.testPuzzleWithAnswerSolution = new ConcretePuzzle("testName2", "testDescription2", true,
            true, "1:testRoom", false, "test", null, 12, "no effect", 0, null);
    this.testPuzzleWithItemSolution = new ConcreteMonster("testName2", "testDescription2", true,
            true, "1:testRoom", true, null, "testName1", 70,
            "big effect", -100, "picture", true, "attack");
    this.testItemSolution = new ConcreteItem("testName1", "testDescription1", 0.0, 0.0, null,
            10, 10, "test");
    this.testItemNotSolution = new ConcreteItem("test", "testDescription1", 0.0, 0.0, null,
            10, 10, "test");
  }

  @Test
  void testConstructor() {
    //damage > 0.0
    assertThrows(IllegalArgumentException.class, () -> {
      new ConcretePuzzle("testName2", "testDescription2", true,
              true, "1:testRoom", true, "test", "test", 12, "no effect", 10, null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new ConcreteMonster("testName2", "testDescription2", true,
              true, "1:testRoom", true, "test", "test", 12,
              "no effect", 10, "picture", true, "attack");
    });

    //negative score
    assertThrows(IllegalArgumentException.class, () -> {
      new ConcretePuzzle("testName2", "testDescription2", true,
              true, "1:testRoom", true, "test", "test", -12, "no effect", -10, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ConcreteMonster("testName2", "testDescription2", true,
              true, "1:testRoom", true, "test", "test", -100,
              "no effect", -10, "picture", true, "attack");
    });

    //affectsTarget but no target
    assertThrows(IllegalArgumentException.class, () -> {
      new ConcretePuzzle("testName2", "testDescription2", true,
              true, null, true, null, null, 12, "no effect", -10, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ConcreteMonster("testName2", "testDescription2", true,
              true, null, true, "null", null, 12,
              "no effect", -10, "picture", true, "attack");
    });

    //no solution
    assertThrows(IllegalArgumentException.class, () -> {
      new ConcretePuzzle("testName2", "testDescription2", true,
              true, "1:testRoom", true, null, null, 12, "no effect", -10, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ConcreteMonster("testName2", "testDescription2", true,
              true, "1:testRoom", true, null, null, 12,
              "no effect", -10, "picture", true, "attack");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ConcretePuzzle("testName2", "testDescription2", true,
              true, "1:testRoom", false, "test", null, 12, null, 0, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new ConcreteMonster("testName2", "testDescription2", true,
              true, "1:testRoom", true, null, "testName1", 70,
              "big effect", -100, "picture", true, "attack");
    });
  }



  @Test
  void testGetPuzzleDamage() {
    assertEquals(0, ((ConcretePuzzle) testPuzzleWithAnswerSolution).getPuzzleDamage());

    assertEquals(-100, ((ConcretePuzzle) testPuzzleWithItemSolution).getPuzzleDamage());
  }

  @Test
  void testGetPicturePath() {
    assertEquals(null, testPuzzleWithAnswerSolution.getPicturePath());
    assertEquals("picture", testPuzzleWithItemSolution.getPicturePath());
  }

  @Test
  void testAffectsPlayer() {
    assertFalse(testPuzzleWithItemSolution.affectsPlayer());
    assertTrue(testPuzzleWithAnswerSolution.affectsPlayer());
  }

  @Test
  void testGetScore() {
    assertEquals(12, testPuzzleWithAnswerSolution.getScore());
    assertEquals(70, testPuzzleWithItemSolution.getScore());
  }

  @Test
  void testGetTarget() {
    assertEquals("1:testRoom", testPuzzleWithItemSolution.getTarget());
    assertEquals("1:testRoom", testPuzzleWithAnswerSolution.getTarget());
  }

  @Test
  void testAffectsTarget() {
    assertTrue(testPuzzleWithItemSolution.affectsTarget());
    assertTrue(testPuzzleWithAnswerSolution.affectsTarget());
  }

  @Test
  void testGetEffect() {
    assertEquals("no effect", testPuzzleWithItemSolution.getEffect());
    assertEquals("big effect", testPuzzleWithAnswerSolution.getEffect());
  }

  @Test
  void testGetSolutionText() {
    assertEquals("test", testPuzzleWithAnswerSolution.getSolutionText());
    assertEquals(null, testPuzzleWithItemSolution.getSolutionText());
  }

  @Test
  void testGetSolutionItem() {
    assertEquals(null, testPuzzleWithAnswerSolution.getSolutionItem());
    assertEquals("test", testPuzzleWithItemSolution.getSolutionItem());
  }

  @Test
  void testIsActive() {
    assertTrue(testPuzzleWithItemSolution.isActive());
    assertTrue(testPuzzleWithAnswerSolution.isActive());
  }

  @Test
  void testSolve() {
    assertFalse(testPuzzleWithAnswerSolution.solve((String) null));
    assertFalse(testPuzzleWithAnswerSolution.isActive());
    assertFalse(testPuzzleWithAnswerSolution.solve(testItemSolution));
    assertFalse(testPuzzleWithAnswerSolution.isActive());
    assertFalse(testPuzzleWithAnswerSolution.solve((Item) null));
    assertFalse(testPuzzleWithAnswerSolution.isActive());
    assertFalse(testPuzzleWithAnswerSolution.solve(testItemNotSolution));
    assertFalse(testPuzzleWithAnswerSolution.isActive());
    assertTrue(testPuzzleWithAnswerSolution.solve("test"));
    assertFalse(testPuzzleWithAnswerSolution.isActive());
    assertFalse(testPuzzleWithAnswerSolution.solve("test"));
    assertFalse(testPuzzleWithAnswerSolution.isActive());

    assertFalse(testPuzzleWithItemSolution.solve(testItemNotSolution));
    assertTrue(testPuzzleWithItemSolution.isActive());
    assertFalse(testPuzzleWithItemSolution.solve((Item) null));
    assertTrue(testPuzzleWithItemSolution.isActive());
    assertFalse(testPuzzleWithItemSolution.solve(testItemSolution.getName()));
    assertTrue(testPuzzleWithItemSolution.isActive());
    assertFalse(testPuzzleWithItemSolution.solve((String) null));
    assertTrue(testPuzzleWithItemSolution.isActive());
    assertTrue(testPuzzleWithItemSolution.solve(testItemSolution));
    assertFalse(testPuzzleWithItemSolution.isActive());
    assertFalse(testPuzzleWithItemSolution.solve(testItemSolution));
    assertFalse(testPuzzleWithItemSolution.isActive());
  }
}