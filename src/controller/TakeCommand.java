package controller;

import java.io.IOException;
import java.util.Arrays;

import model.IAdventureGameModel;

/**
 * The TakeCommand class is used to by a player to take an item. It has a model attribute.
 */
public class TakeCommand extends AbstractCommand {


  /**
   * The constructor for the TakeCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public TakeCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(Arrays.asList(this.model.takeItem(this.processor.getUserInputArgument()), null));
    this.processor.updateRoom(this.model.lookAround());
    this.processor.updateFixtures(this.model.getFixturesInRoom());
    this.processor.updateItems(this.model.getItemsInRoom());
    this.processor.updateInventory(this.model.checkInventory());
    return super.execute();
  }
}
