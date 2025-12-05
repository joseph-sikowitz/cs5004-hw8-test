package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * Handles Invalid User Commands.
 */
public class InvalidCommand extends AbstractCommand {
  private static final String UNKNOWN_COMMAND = "Unknown command!\n";

  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public InvalidCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(UNKNOWN_COMMAND);
    return true;
  }
}
