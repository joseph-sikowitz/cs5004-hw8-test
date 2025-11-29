package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * Quits the game, displaying a message from the model.
 */
public class QuitCommand extends AbstractCommand {

  public QuitCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(this.model.quitMessage());
    return false;
  }
}
