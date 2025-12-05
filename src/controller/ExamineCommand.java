package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The ExamineCommand class is used to by a player to examine an element. It has a model attribute.
 */
public class ExamineCommand extends AbstractCommand {


  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public ExamineCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(this.model.examine(this.processor.getUserInputArgument()));
    return super.execute();
  }
}
