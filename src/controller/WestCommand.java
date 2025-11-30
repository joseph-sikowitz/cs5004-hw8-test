package controller;

import java.io.IOException;
import java.util.Arrays;

import model.IAdventureGameModel;

/**
 * The WestCommand class is used to move a player in the model west. It has a model
 * attribute.
 */
public class WestCommand extends AbstractCommand {

  /**
   * The constructor for the WestCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the move west command for.
   */
  public WestCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    String response = this.model.movePlayerWest();
    this.processor.messageToPlayer(response);
    if (this.model.roomChanged())
      this.processor.updateRoom(this.model.lookAround());
    return super.execute();
  }
}
