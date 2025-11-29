package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * Handles Invalid User Commands.
 */
public class InvalidCommand extends AbstractCommand {
  private static final String UNKNOWN_COMMAND = "Unknown command!\n";


  public InvalidCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(UNKNOWN_COMMAND);
    return true;
  }
}
