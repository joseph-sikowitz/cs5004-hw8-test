package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * The GameCommandReaderTest performs trivial tests of the GameTextInputOutputProcessor class
 * since its functionality is based on user input.
 */
class GameCommandReaderTest {

  /**
   * The testConstructor() method tests the GameTextInputOutputProcessor's constructor by
   * asserting that a new GameTextInputOutputProcessor object is created.
   */
  @Test
  void testConstructor() {
    BufferedReader stringReader = new BufferedReader(new StringReader("Joe"));
    GameTextInputOutputProcessor gcr = new GameTextInputOutputProcessor(stringReader, System.out);
    assertInstanceOf(GameTextInputOutputProcessor.class, gcr);
  }

}