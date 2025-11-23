package controller;

import model.IAdventureGameModel;

/**
 * The AnswerCommand class is used to by a player to examine an element. It has a model attribute.
 */
public class AnswerCommand implements ICommand {

  private final IAdventureGameModel model;

  /**
   * The constructor for the AnswerCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public AnswerCommand(IAdventureGameModel model) {

    this.model = model;
  }

  @Override
  public String execute(String userArgument) {
    return this.model.answer(userArgument);
  }
}
