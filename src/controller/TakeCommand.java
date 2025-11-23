package controller;

import model.IAdventureGameModel;

/**
 * The TakeCommand class is used to by a player to take an item. It has a model attribute.
 */
public class TakeCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the TakeCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public TakeCommand(IAdventureGameModel model) {

    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.takeItem(userArgument);
  }
}
