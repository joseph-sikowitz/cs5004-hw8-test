package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import model.IAdventureGameModel;

/**
 * The UseCommand class is used to by a player to use an item. It has a model attribute.
 */
public class UseCommand extends AbstractCommand {

  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public UseCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(model.useItem(this.processor.getUserInputArgument()));
    if (this.model.roomChanged()) {
      this.processor.updateRoom(this.model.lookAround());
      this.processor.updateFixtures(model.getFixturesInRoom());
      this.processor.updateItems(model.getItemsInRoom());
    }
    return super.execute();
  }
}
