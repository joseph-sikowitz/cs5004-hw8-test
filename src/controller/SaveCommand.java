package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The SaveCommand class is used to save a game's state. It has a model attribute.
 */
public class SaveCommand extends AbstractCommand {


  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public SaveCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    try {
      this.processor.messageToPlayer(this.model.saveGame(DATA_DIR + DEFAULT_SAVE_FILE));
    } catch (Exception e) {
      this.processor.messageToPlayer("Error: " + e.getMessage());
    }
    return super.execute();
  }
}
