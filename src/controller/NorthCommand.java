package controller;

import model.IAdventureGameModel;

/**
 * The NorthCommand class is used to move a player in the model north. It has a model
 * attribute.
 */
public class NorthCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the NorthCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the move north command for.
   */
  public NorthCommand(IAdventureGameModel model) {
    this.model = model;
  }

  @Override
  public String execute() {
    return this.model.movePlayerNorth();
  }
}
