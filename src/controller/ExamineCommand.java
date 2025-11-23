package controller;

import model.IAdventureGameModel;

/**
 * The ExamineCommand class is used to by a player to examine an element. It has a model attribute.
 */
public class ExamineCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the ExamineCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public ExamineCommand(IAdventureGameModel model) {

    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.examine(userArgument);
  }
}
