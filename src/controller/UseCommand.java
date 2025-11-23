package controller;

import model.IAdventureGameModel;

/**
 * The UseCommand class is used to by a player to use an item. It has a model attribute.
 */
public class UseCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the UseCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public UseCommand(IAdventureGameModel model) {

    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.useItem(userArgument);
  }
}
