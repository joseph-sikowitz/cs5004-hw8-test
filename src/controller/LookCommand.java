package controller;

import model.IAdventureGameModel;

/**
 * The LookCommand class is used to look around a room. It has a model attribute.
 */
public class LookCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the LookCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the look command for.
   */
  public LookCommand(IAdventureGameModel model) {
    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.lookAround();
  }
}
