package controller;

import model.IAdventureGameModel;

/**
 * The WestCommand class is used to move a player in the model west. It has a model
 * attribute.
 */
public class WestCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the WestCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the move west command for.
   */
  public WestCommand(IAdventureGameModel model) {
    this.model = model;
  }

  @Override
  public String execute() {
    return this.model.movePlayerWest();
  }
}
