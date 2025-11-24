package enginedriver;

import static java.nio.file.Files.exists;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import controller.GameController;
import model.AdventureGameModel;
import model.IAdventureGameModel;
import view.ConcreteTextView;
import view.ITextView;

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
   * @throws FileNotFoundException if the given file was not found.
   */
  public void start() throws IOException {
    if (!exists(Path.of(this.gameFileName)) && !exists(Path.of(System.getProperty("user.dir")
            + this.gameFileName))) {
      throw new FileNotFoundException("File does not exist at the end of the given path: "
              + this.gameFileName);
    }

    IAdventureGameModel model = new AdventureGameModel(this.gameFileName);
    ITextView view = new ConcreteTextView(this.output);
    GameController controller = new GameController(this.source, model, view);
    controller.go();
  }
}
