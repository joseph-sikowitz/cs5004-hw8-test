package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The NorthCommand class is used to move a player in the model north. It has a model
 * attribute.
 */
public class NorthCommand extends AbstractCommand {

  /**
   * The constructor for the NorthCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the move north command for.
   */
  public NorthCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    String response = this.model.movePlayerNorth();
    this.processor.messageToPlayer(response);
    if (this.model.roomChanged()) {
      this.processor.updateRoom(this.model.lookAround());
      this.processor.updateFixtures(this.model.getFixturesInRoom());
      this.processor.updateItems(this.model.getItemsInRoom());
    }
    return super.execute();
  }
}
