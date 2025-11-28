package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The LookCommand class is used to look around a room. It has a model attribute.
 */
public class LookCommand extends AbstractCommand {


  /**
   * The constructor for the LookCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the look command for.
   */
  public LookCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    this.processor.updateRoom(model.lookAround());
    return super.execute();
  }
}
