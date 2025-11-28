package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The UseCommand class is used to by a player to use an item. It has a model attribute.
 */
public class UseCommand extends AbstractCommand {

  /**
   * The constructor for the UseCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public UseCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public void execute() throws IOException {
     this.processor.messageToPlayer(model.useItem(this.processor.getUserInputArgument()));
     if (this.model.roomChanged())
       this.processor.updateRoom(this.model.lookAround());
  }
}
