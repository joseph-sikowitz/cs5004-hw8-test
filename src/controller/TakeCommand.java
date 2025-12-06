package controller;

import java.io.IOException;
import java.util.Arrays;

import model.IAdventureGameModel;

/**
 * The TakeCommand class is used to by a player to take an item. It has a model attribute.
 */
public class TakeCommand extends AbstractCommand {


  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public TakeCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.messageToPlayer(this.model.takeItem(this.processor.getUserInputArgument()));
    this.processor.updateInventory(this.model.checkInventory());
    this.processor.updateItems(this.model.getItemsInRoom());
    return super.execute();
  }
}
