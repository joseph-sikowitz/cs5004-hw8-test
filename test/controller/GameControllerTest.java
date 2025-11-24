package controller;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.InputStreamReader;

import model.AdventureGameModel;
import model.IAdventureGameModel;
import view.TextView;
import view.IView;

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
    IView<String> view = new TextView(System.out);
    GameController controller = new GameController(
            new InputStreamReader(System.in), model, view);
    assertInstanceOf(GameController.class, controller);
  }
}