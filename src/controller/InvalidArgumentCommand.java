package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * Handles invalid arguments to commands that require arguments.
 */
public class InvalidArgumentCommand extends AbstractCommand {
  private static final String REQUIRED_ARGUMENT = " requires an argument!";

  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
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
