package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The RestoreCommand class is used to restore a game's state from a save file. It has
 * a model attribute.
 */
public class RestoreCommand extends AbstractCommand {


  /**
   * The constructor for the RestoreCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public RestoreCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public void execute() throws IOException {
    this.model.restoreGame(this.processor.getUserInputArgument());
  }
}
