package controller;

import model.IAdventureGameModel;

/**
 * The RestoreCommand class is used to restore a game's state from a save file. It has
 * a model attribute.
 */
public class RestoreCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the RestoreCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public RestoreCommand(IAdventureGameModel model) {
    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.restoreGame(userArgument);
  }
}
