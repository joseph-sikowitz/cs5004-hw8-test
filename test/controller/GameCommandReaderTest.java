package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * The GameCommandReaderTest performs trivial tests of the GameCommandReader class
 * since its functionality is based on user input.
 */
class GameCommandReaderTest {

  /**
   * The testConstructor() method tests the GameCommandReader's constructor by
   * asserting that a new GameCommandReader object is created.
   */
  @Test
  void testConstructor() {
    BufferedReader stringReader = new BufferedReader(new StringReader("Joe"));
    GameCommandReader gcr = new GameCommandReader(stringReader, System.out);
    assertInstanceOf(GameCommandReader.class, gcr);
  }

  /**
   * The testStartGame() method tests startGame() by checking that the input
   * provided is the result of starting the game.
   */
  @Test
  void testStartGame() {
    BufferedReader stringReader = new BufferedReader(new StringReader("Joe"));
    GameCommandReader gcr = new GameCommandReader(stringReader, System.out);

    String result = "";
    try {
      result = gcr.startGame();
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertEquals("Joe", result);
  }

}