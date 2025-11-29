package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The ExamineCommand class is used to by a player to examine an element. It has a model attribute.
 */
public class ExamineCommand extends AbstractCommand {


  /**
   * The constructor for the ExamineCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public ExamineCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.updateExaminer(this.model.examine(this.processor.getUserInputArgument()));
    return super.execute();
  }
}
