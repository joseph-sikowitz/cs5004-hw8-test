package controller;

import java.io.IOException;

/**
 * The Controller interface defines the Controller type. A Controller takes user input
 * and communicates with the model to process game functions.
 */
public interface Controller {

  /**
   * The go() method starts the game by prompting the user to enter their username and
   * then capturing it. After that, it accepts user input commands that make calls to
   * the model for interactive game play until the user quits the game.
   *
   * @throws IOException if there is an error using the provided input or output.
   */
  void go() throws IOException;
}
