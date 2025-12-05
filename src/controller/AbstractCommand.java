package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * Physical code reuse for command pattern.
 */
public abstract class AbstractCommand implements ICommand {
  protected static final String DATA_DIR = System.getProperty("user.dir")
          + System.getProperty("file.separator") + "resources"
          + System.getProperty("file.separator");
  protected static final String DEFAULT_SAVE_FILE = "save_file.json";

  protected final IAdventureGameModel model;
  protected final GameInputOutputProcessor processor;

  /**
   * Constructor initializes the IAdventureGameModel and the GameInputOutputProcessor.
   * @param model an instance of IAdventureGameModel type.
   * @param processor and instance of GameInputOutputProcessor type.
   */
  public AbstractCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    this.model = model;
    this.processor = processor;
  }

  @Override
  public boolean execute() throws IOException {
    return this.model != null && !this.model.gameOver();
  }

}
