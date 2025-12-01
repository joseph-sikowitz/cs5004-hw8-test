package controller;

import java.io.IOException;
import java.util.Arrays;

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
  public boolean execute() throws IOException {
     this.processor.messageToPlayer(Arrays.asList(model.useItem(this.processor.getUserInputArgument()), null));
     if (this.model.roomChanged()) {
       this.processor.updateRoom(this.model.lookAround());
       this.processor.updateFixtures(model.getFixturesInRoom());
       this.processor.updateItems(model.getItemsInRoom());
     }
    return super.execute();
  }
}
