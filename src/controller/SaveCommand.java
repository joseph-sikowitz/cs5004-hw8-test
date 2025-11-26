package controller;

import model.IAdventureGameModel;

/**
 * The SaveCommand class is used to save a game's state. It has a model attribute.
 */
public class SaveCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the SaveCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public SaveCommand(IAdventureGameModel model) {
    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.saveGame(userArgument);
  }
}
