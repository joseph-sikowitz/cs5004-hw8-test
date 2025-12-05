package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * Quits the game, displaying a message from the model.
 */
public class QuitCommand extends AbstractCommand {

  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public QuitCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.quit(this.model.quitMessage());
    return false;
  }
}
