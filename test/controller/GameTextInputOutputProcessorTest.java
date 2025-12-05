package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import view.AdventureGameTextView;
import view.IAdventureGameView;

/**
 * The GameTextInputOutputProcessorTest performs trivial tests of the GameTextInputOutputProcessor class
 * since its functionality is based on user input.
 */
class GameTextInputOutputProcessorTest {

  /**
   * The testConstructor() method tests the GameTextInputOutputProcessor's constructor by
   * asserting that a new GameTextInputOutputProcessor object is created.
   */
  @Test
  void testConstructor() {
    BufferedReader stringReader = new BufferedReader(new StringReader("Joe"));
    IAdventureGameView<String> view = new AdventureGameTextView(stringReader, System.out);
    GameTextInputOutputProcessor gcr = new GameTextInputOutputProcessor(view);
    assertInstanceOf(GameTextInputOutputProcessor.class, gcr);
  }

}