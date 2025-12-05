package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The EastCommand class is used to move a player in the model east. It has a model
 * attribute.
 */
public class EastCommand extends AbstractCommand {


  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public EastCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    String response = this.model.movePlayerEast();
    this.processor.messageToPlayer(response);
    if (this.model.roomChanged()) {
      this.processor.updateRoom(this.model.lookAround());
      this.processor.updateFixtures(this.model.getFixturesInRoom());
      this.processor.updateItems(this.model.getItemsInRoom());
    }
    return super.execute();
  }
}
