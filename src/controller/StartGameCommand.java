package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import model.IAdventureGameModel;

/**
 * The startGame() method starts a game by prompting the user to put in their
 * name.
 */
public class StartGameCommand extends AbstractCommand {

  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public StartGameCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.promptPlayer(UserPrompts.NEW_PLAYER_PROMPT.getPrompt());
    this.model.setPlayerName(processor.getUserMessage());
    this.model.loadGameData();
    this.processor.updateTitle(this.model.getGameName());
    this.processor.messageToPlayer(UserPrompts.NEW_PLAYER_NAME_PROMPT.getPrompt()
            + this.model.getPlayerName()  + "\n");
    if (!this.model.getGameFileWarnings().isEmpty())
      this.processor.messageToPlayer(this.model.getGameFileWarnings());
    return super.execute();
  }
}
