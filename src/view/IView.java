package view;

import java.io.IOException;

/**
 * The IView interface determines the types of views for an adventure game.
 */
public interface IView<T> {

  /**
   * The display() method displays the results of a user's action.
   *
   * @param message T to display.
   * @throws IOException if there is an error displaying output.
   */
  void display(T message) throws IOException;

  /**
   * The getter for the view's output.
   *
   * @return Appendable output of the view.
   */
  Appendable getOutput();
}
