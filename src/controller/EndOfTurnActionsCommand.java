package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    List<String> message = new ArrayList<>();
    //if there is a Monster in the room, have it "affect" the Player in the model.
    //message.add(this.model.affectPlayer());
    this.processor.messageToPlayer(this.model.affectPlayer());

    if (this.model.changeInPlayerHealthStatus() | this.model.changeInPlayerScore()
            | this.model.changeInPlayerRank()) {
      //display player's health status if it has changed since last command.
      message.add(this.model.getPlayerHealthStatus());
      //display player's score if it has changed since last command.
      message.add(this.model.getPlayerScoreFormatted());
      //display player's rank if it has changed since last command.
      message.add(this.model.getPlayerRank());
      this.processor.updatePlayerStats(message);
    }

    boolean gameRunnable = super.execute();
    if (!gameRunnable)
      this.processor.quit(this.model.quitMessage());
    return gameRunnable;
  }
}
