package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The NorthCommand class is used to move a player in the model north. It has a model
 * attribute.
 */
public class NorthCommand extends AbstractCommand {

  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
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
