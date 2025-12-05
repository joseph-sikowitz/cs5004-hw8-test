package controller;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.InputStreamReader;

import model.AdventureGameModel;
import model.IAdventureGameModel;
import view.AdventureGameTextView;
import view.IAdventureGameView;

import org.junit.jupiter.api.Test;

/**
 * The GameControllerTest performs a trivial test of the GameController class
 * by checking that the constructor instantiates a GameController object. Since
 * GameController's behavior is driven by user input, the go() method is not
 * tested.
 */
class GameControllerTest {

  /**
   * The testConstructor() method tests the GameController constructor to see
   * that it instantiates a GameController object.
   */
  @Test
  void testConstructor() {
    String gameFileName = "data/simple_hallway.json";
    IAdventureGameModel model = new AdventureGameModel(gameFileName);
    IAdventureGameView<String> view = new AdventureGameTextView(
            new InputStreamReader(System.in), System.out);
    GameInputOutputProcessor ioProcessor = new GameTextInputOutputProcessor(view);
    GameController controller = new GameController(ioProcessor, model);
    assertInstanceOf(GameController.class, controller);
  }
}