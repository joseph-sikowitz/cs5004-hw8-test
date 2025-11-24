package view;

import java.io.IOException;

/**
 * The ConcreteTextView class handles the display for a text-based adventure game view. TextViews
 * have an Appendable out attribute.
 */
public class ConcreteTextView implements ITextView {

  private final Appendable out;

  /**
   * The constructor initializes the Appendable out attribute for writing during
   * a text-based game.
   *
   * @param out Appendable for game output.
   */
  public ConcreteTextView(Appendable out) {
    this.out = out;
  }

  @Override
  public Appendable getOutput() {
    return this.out;
  }

  @Override
  public void display(String message) throws IOException {
    try {
      this.out.append(message);
    } catch (IOException e) {
      System.out.println("Error displaying message: " + e.getMessage());
    }
  }
}
