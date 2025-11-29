package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * Executes model actions that affect the player and
 * outputs player status if it has changed since the last command.
 */
public class EndOfTurnActionsCommand extends AbstractCommand {

  public EndOfTurnActionsCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    StringBuilder message = new StringBuilder();
    //if there is a Monster in the room, have it "affect" the Player in the model.
    message.append(this.model.affectPlayer());
    //display player's health status if it has changed since last command.
    if (this.model.changeInPlayerHealthStatus())
      message.append(this.model.getPlayerHealthStatus());
    //display player's score if it has changed since last command.
    if (this.model.changeInPlayerScore())
      message.append(this.model.getPlayerScoreFormatted());
    //display player's rank if it has changed since last command.
    if (this.model.changeInPlayerRank())
      message.append(this.model.getPlayerRank());
    this.processor.updatePlayerStats(message.toString());
    boolean gameRunnable = super.execute();
    if (!gameRunnable)
      this.processor.messageToPlayer(this.model.quitMessage());
    return gameRunnable;
  }
}
