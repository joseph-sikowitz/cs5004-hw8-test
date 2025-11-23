package controller;

import model.IAdventureGameModel;

/**
 * The EastCommand class is used to move a player in the model east. It has a model
 * attribute.
 */
public class EastCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the EastCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the move east command for.
   */
  public EastCommand(IAdventureGameModel model) {
    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.movePlayerEast();
  }
}
