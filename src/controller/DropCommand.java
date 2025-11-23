package controller;

import model.IAdventureGameModel;

/**
 * The DropCommand class is used to by a player to drop an item. It has a model attribute.
 */
public class DropCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the DropCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public DropCommand(IAdventureGameModel model) {

    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.dropItem(userArgument);
  }
}
