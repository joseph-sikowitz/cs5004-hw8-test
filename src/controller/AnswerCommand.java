package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The AnswerCommand class is used to by a player to examine an element. It has a model attribute.
 */
public class AnswerCommand extends AbstractCommand {


  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public AnswerCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(model.answer(this.processor.getUserInputArgument()));
    if (this.model.roomChanged())
      this.processor.updateRoom(this.model.lookAround());
    return super.execute();
  }
}
