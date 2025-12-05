package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * The RestoreCommand class is used to restore a game's state from a save file. It has
 * a model attribute.
 */
public class RestoreCommand extends AbstractCommand {


  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public RestoreCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    super(model, processor);
  }

  @Override
  public boolean execute() throws IOException {
    try {
      this.processor.messageToPlayer(this.model.restoreGame(DATA_DIR + DEFAULT_SAVE_FILE));
      this.processor.messageToPlayer(this.model.restoreMessage());
      this.processor.updateRoom(this.model.lookAround());
    } catch (Exception e) {
      this.processor.messageToPlayer("File not found: " + e.getMessage());
    }
    return super.execute();
  }
}
