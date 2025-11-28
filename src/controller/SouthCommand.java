package controller;

import java.io.IOException;

import model.IAdventureGameModel;


/**
 * The SouthCommand class is used to move a player in the model south. It has a model
 * attribute.
 */
public class SouthCommand extends AbstractCommand {


  /**
   * The constructor for the SouthCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the move south command for.
   */
  public SouthCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public void execute() throws IOException {
    String response = this.model.movePlayerSouth();
    this.processor.messageToPlayer(response);
    if (this.model.roomChanged())
      this.processor.updateRoom(this.model.lookAround());
  }
}
