package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The LookCommand class is used to look around a room. It has a model attribute.
 */
public class LookCommand extends AbstractCommand {


  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public LookCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.updateRoom(this.model.lookAround());
    this.processor.updateFixtures(this.model.getFixturesInRoom());
    this.processor.updateItems(this.model.getItemsInRoom());
    return super.execute();
  }
}
