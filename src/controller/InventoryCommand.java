package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The InventoryCommand class is used to check a player's inventory. It has a model
 * attribute.
 */
public class InventoryCommand extends AbstractCommand {


  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public InventoryCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.updateInventory(this.model.checkInventory());
    return super.execute();
  }
}
