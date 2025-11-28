package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The startGame() method starts a game by prompting the user to put in their
 * name.
 */
public class StartGameCommand extends AbstractCommand {

  public StartGameCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public void execute() throws IOException {
    processor.messageToPlayer(UserPrompts.NEW_PLAYER_PROMPT.getPrompt());
    this.model.setPlayerName(processor.getUserMessage());
    processor.messageToPlayer(UserPrompts.NEW_PLAYER_NAME_PROMPT.getPrompt()
            + this.model.getPlayerName()  + "\n");
    this.model.loadGameData();
  }
}
