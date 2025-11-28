package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The DropCommand class is used to by a player to drop an item. It has a model attribute.
 */
public class DropCommand extends AbstractCommand {


  /**
   * The constructor for the DropCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public DropCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public void execute() throws IOException {
    this.processor.messageToPlayer(this.model.dropItem(this.processor.getUserInputArgument()));
  }
}
