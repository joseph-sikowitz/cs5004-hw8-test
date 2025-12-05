package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * Handles invalid arguments to commands that require arguments.
 */
public class InvalidArgumentCommand extends AbstractCommand {
  private static final String REQUIRED_ARGUMENT = " requires an argument!\n";

  public InvalidArgumentCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(this.processor.getLastUserInputCommand()
            + REQUIRED_ARGUMENT);
    return true;
  }
}
