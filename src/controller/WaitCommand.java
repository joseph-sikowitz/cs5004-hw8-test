package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The WaitCommand performs no action on the model
 * and simply returns whether the next command can be performed on the model.
 */
public class WaitCommand extends AbstractCommand {

  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public WaitCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    return super.execute();
  }

}
