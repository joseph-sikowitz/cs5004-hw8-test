package controller;

import model.IAdventureGameModel;

/**
 * The InventoryCommand class is used to check a player's inventory. It has a model
 * attribute.
 */
public class InventoryCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the InventoryCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the Inventory command for.
   */
  public InventoryCommand(IAdventureGameModel model) {
    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.checkInventory();
  }
}
