package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The AnswerCommand class is used to by a player to examine an element. It has a model attribute.
 */
public class AnswerCommand extends AbstractCommand {


  /**
   * The constructor for the AnswerCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public AnswerCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public void execute() throws IOException {
    this.processor.messageToPlayer(model.answer(this.processor.getUserInputArgument()));
    if (this.model.roomChanged())
      this.processor.updateRoom(this.model.lookAround());
  }
}
