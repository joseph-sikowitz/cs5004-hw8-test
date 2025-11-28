package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The EastCommand class is used to move a player in the model east. It has a model
 * attribute.
 */
public class EastCommand extends AbstractCommand {


  /**
   * The constructor for the EastCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the move east command for.
   */
  public EastCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public void execute() throws IOException {
    String response = this.model.movePlayerEast();
    this.processor.messageToPlayer(response);
    if (this.model.roomChanged())
      this.processor.updateRoom(this.model.lookAround());
  }
}
