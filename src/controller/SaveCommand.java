package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The SaveCommand class is used to save a game's state. It has a model attribute.
 */
public class SaveCommand extends AbstractCommand {


  /**
   * The constructor for the SaveCommand class initializes the game model.
   *
   * @param model IAdventureGameModel object to execute the command for.
   */
  public SaveCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public void execute() throws IOException {
    try {
      this.processor.messageToPlayer(this.model.saveGame(DATA_DIR + DEFAULT_SAVE_FILE));
    } catch (Exception e) {
      this.processor.messageToPlayer("File not found: " + e.getMessage());
    }
  }
}
