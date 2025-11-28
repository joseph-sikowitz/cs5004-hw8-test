package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The InventoryCommand class is used to check a player's inventory. It has a model
 * attribute.
 */
public class InventoryCommand extends AbstractCommand {


  /**
   * The constructor for the InventoryCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the Inventory command for.
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
