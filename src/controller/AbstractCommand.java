package controller;

import model.IAdventureGameModel;

/**
 * Physical code reuse for command pattern.
 */
public abstract class AbstractCommand implements ICommand {
  private static final String DATA_DIR = System.getProperty("user.dir") + "/data/";
  private static final String DEFAULT_SAVE_FILE = "save_file.json";

  protected final IAdventureGameModel model;
  protected final GameInputOutputProcessor processor;

  public AbstractCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    this.model = model;
    this.processor = processor;
  }

}
