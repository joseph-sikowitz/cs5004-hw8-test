package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The DropCommand class is used to by a player to drop an item. It has a model attribute.
 */
public class DropCommand extends AbstractCommand {


  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public DropCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(this.model.dropItem(this.processor.getUserInputArgument()));
    this.processor.updateInventory(this.model.checkInventory());
    this.processor.updateRoom(this.model.lookAround());
    this.processor.updateFixtures(this.model.getFixturesInRoom());
    this.processor.updateItems(this.model.getItemsInRoom());
    return super.execute();
  }
}
