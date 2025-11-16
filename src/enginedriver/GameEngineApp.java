package enginedriver;

import static java.nio.file.Files.exists;

import java.io.IOException;
import java.nio.file.Path;

import controller.GameController;
import model.AdventureGameModel;
import model.IAdventureGameModel;

/**
 * The GameEngineApp class is the entry point for an adventure game. It has a game filename
 * that is loaded to start the game, a source that reads user input, and output to store
 * the outcome of user interaction.
 */
public class GameEngineApp {

  private String gameFileName;
  private Readable source;
  private Appendable output;

  /**
   * The constructor for the GameEngineApp initializes its attributes and instantiates
   * a GameEngineApp object.
   *
   * @param gameFileName String of an adventure game file.
   * @param source Readable from a buffered source of characters, either a String or input stream.
   * @param output Appendable output, either the command line or a file.
   */
  public GameEngineApp(String gameFileName, Readable source, Appendable output) {
    this.gameFileName = gameFileName;
    this.source = source;
    this.output = output;
  }

  /**
   * The start() method creates the game and controller and tells the controller to
   * initialize the game.
   *
   * @throws IOException if the given file does not exist.
   */
  public void start() throws IOException {
    if (! exists(Path.of(this.gameFileName))) {
      throw new IOException("File does not exist: " + this.gameFileName);
    }

    IAdventureGameModel model = new AdventureGameModel(this.gameFileName);
    GameController controller = new GameController(this.source, this.output, model);
    controller.go();
  }
}
