package controller;

import model.IAdventureGameModel;


/**
 * The SouthCommand class is used to move a player in the model south. It has a model
 * attribute.
 */
public class SouthCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the SouthCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the move south command for.
   */
  public SouthCommand(IAdventureGameModel model) {
    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.movePlayerSouth();
  }
}
