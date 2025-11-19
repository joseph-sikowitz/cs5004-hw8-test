package model;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



/**
 * Tests the AbstractElement class that all concrete
 * subtypes of Element use for physical code reuse.
 */
class AbstractElementTest {

  private Element testPuzzle;
  private Element testMonster;
  private Element testPlayer;
  private Element testItem;
  private Element testRoom;
  private Map<Directions, Integer> passages;
  private Map<String, Item> inventory;

  @BeforeEach
  void setUp() {
    this.testItem = new ConcreteItem("testName1", "testDescription1", 0.0, 0.0, null, 10, 10, "test");
    this.testPuzzle = new ConcretePuzzle("testName2", "testDescription2", true, true, "1:testRoom",
            true, "test", "test", 12, "no effect", -10, null);
    this.testMonster = new ConcreteMonster("testName3", "testDescription3", true, true,
            "1:testRoom", true, "test", "test", 12, "no effect", -10, null, true, "attack");

    this.passages = new HashMap<>();
    for (Directions dir : Directions.values()) {
      passages.put(dir, dir.ordinal() + 2);
    }

    this.testRoom = new ConcreteRoom("testName4", "testDescription4", 1, this.passages,
            null, null, null, null, null);
    this.inventory = new HashMap<>();

    this.testPlayer = new ConcretePlayer("testName5", this.inventory, (Room) this.testRoom);
  }

  @Test
  void testCheckIfInvalid() {
    assertTrue(AbstractElement.checkIfInvalid(null));
    assertTrue(AbstractElement.checkIfInvalid(""));
    assertFalse(AbstractElement.checkIfInvalid(" "));
    assertFalse(AbstractElement.checkIfInvalid("Test"));
  }

  @Test
  void testGetName() {
    assertEquals("testName1", testItem.getName());
    assertEquals("testName2", testPuzzle.getName());
    assertEquals("testName3", testMonster.getName());
    assertEquals("testName4", testRoom.getName());
    assertEquals("testName5", testPlayer.getName());
  }

  @Test
  void testGetDescription() {
    assertEquals("testDescription1", testItem.getDescription());
    assertEquals("testDescription2", testPuzzle.getDescription());
    assertEquals("testDescription3", testMonster.getDescription());
    assertEquals("testDescription4", testRoom.getDescription());
    assertEquals("Default player", testPlayer.getDescription());
  }

}