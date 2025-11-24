package view;

import java.io.IOException;

/**
 * The ITextView interface determines the types of views for an adventure game.
 */
public interface ITextView {

  /**
   * The display() method displays the results of a user's action.
   *
   * @param message String of message to display.
   * @throws IOException if there is an error displaying output.
   */
  void display(String message) throws IOException;

  /**
   * The getter for the view's output.
   *
   * @return Appendable output of the view.
   */
  Appendable getOutput();
}
