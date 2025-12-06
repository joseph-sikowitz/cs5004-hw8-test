package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

/**
 * The FileProcessorTest class tests the methods of the FileProcessor class.
 */
class FileProcessorTest {

  /**
   * The testConstructor() method tests that the constructor creates an instance of
   * FileProcessor.
   */
  @Test
  void testConstructor() {
    FileProcessor fp = new FileProcessor("resources/simple_hallway.json", "Joe");
    assertInstanceOf(FileProcessor.class, fp);
  }

  /**
   * The testGetGameFileName() tests the getter for the file name.
   */
  @Test
  void testGetGameFileName() {
    FileProcessor fp = new FileProcessor("resources/simple_hallway.json", "Joe");
    assertEquals("resources/simple_hallway.json", fp.getGameFileName());
  }

  /**
   * The testSetUpGame() method tests that setUpGame() works as expected by testing that it
   * returns a Player object after initializing the game.
   */
  @Test
  void testSetUpGame() {
    FileProcessor fp = new FileProcessor("resources/simple_hallway.json", "Joe");
    Player p1 = fp.setUpGame();
    assertEquals("Joe", p1.getName());
  }

  /**
   * The testGetGameFileWarnings() method tests that there are no warnings returned
   * from getGameFileWarnings() when the game is first initialized.
   */
  @Test
  void testGetGameFileWarnings() {
    FileProcessor fp = new FileProcessor("resources/simple_hallway.json", "Joe");
    assertEquals("", fp.getGameFileWarnings());
  }
}