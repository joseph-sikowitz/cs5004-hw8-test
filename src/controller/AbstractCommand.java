package controller;

import java.io.IOException;

import model.IAdventureGameModel;

/**
 * Physical code reuse for command pattern.
 */
public abstract class AbstractCommand implements ICommand {
  protected static final String DATA_DIR = System.getProperty("user.dir") + "/resources/";
  protected static final String DEFAULT_SAVE_FILE = "save_file.json";

  protected final IAdventureGameModel model;
  protected final GameInputOutputProcessor processor;

  public AbstractCommand(IAdventureGameModel model, GameInputOutputProcessor processor) {
    this.model = model;
    this.processor = processor;
  }

  @Override
  public boolean execute() throws IOException {
    return !this.model.gameOver();
  }

}
