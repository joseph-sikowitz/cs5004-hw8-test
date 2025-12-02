package enginedriver;

import static java.nio.file.Files.exists;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import controller.GameController;
import controller.GameGraphicInputOutputProcessor;
import controller.GameInputOutputProcessor;
import controller.GameTextInputOutputProcessor;
import model.AdventureGameModel;
import model.IAdventureGameModel;

/**
 * The GameEngineApp class is the entry point for an adventure game. It has a game filename
 * that is loaded to start the game, a source that reads user input, and output to store
 * the outcome of user interaction.
 */
public class GameEngineApp {
  private static final String DATA_DIR = System.getProperty("user.dir")
          + System.getProperty("file.separator") + "resources"
          + System.getProperty("file.separator");
  private static final String TEXT = "-text";
  private static final String GRAPHICS = "-graphics";
  private static final String BATCH = "-batch";

  private String gameFileName;
  private Readable source;
  private Appendable output;
  private GameInputOutputProcessor ioProcessor;

  /**
   * Constructor Initializes a Text-Based View with a given Readable and Appendable.
   * @param gameFileName String of an adventure game file.
   * @param source a Readable to read inputs from.
   * @param output an Appendable to append outputs to.
   */
  public GameEngineApp(String gameFileName, Readable source, Appendable output) {
    this(gameFileName, new GameTextInputOutputProcessor(source, output));
  }

  /**
   * The constructor for the GameEngineApp initializes its attributes and instantiates
   * a GameEngineApp object.
   *
   * @param gameFileName String of an adventure game file.
   * @param ioProcessor
   */
  public GameEngineApp(String gameFileName, GameInputOutputProcessor ioProcessor) {
    this.gameFileName = gameFileName;
    this.ioProcessor = ioProcessor;
  }

  /**
   * The start() method creates the game and controller and tells the controller to
   * initialize the game.
   *
   * @throws FileNotFoundException if the given file was not found.
   */
  public void start(String option) throws IOException {
    if (!exists(Path.of(this.gameFileName)) && !exists(Path.of(System.getProperty("user.dir")
            + this.gameFileName))) {
      throw new FileNotFoundException("File does not exist at the end of the given path: "
              + this.gameFileName);
    }

    IAdventureGameModel model = new AdventureGameModel(this.gameFileName);
    GameController controller = new GameController(this.ioProcessor, model);

    switch (option) {
      case TEXT:
        controller.go();
        break;
      case BATCH:
        controller.go();
        break;
      case GRAPHICS:
        controller.go();
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  /**
   * Runs GameEngineApp game with a json data file.
   * @param args not used.
   * @throws IOException if file not found.
   */
  public static void main(String[] args) throws IOException {
    FileWriter targetFileWriter = null;
    String targetFileName = null;

    GameInputOutputProcessor ioProcessor = null;

    try {
      if (args.length <= 1) {
        throw new IllegalArgumentException();
      }
      String fileName = formatFileName(args[0]);
      String option = args[1].toLowerCase();

      switch (option) {
        case TEXT:
          ioProcessor = new GameTextInputOutputProcessor(
                  new InputStreamReader(System.in), System.out);
          break;
        case GRAPHICS:
          ioProcessor = new GameGraphicInputOutputProcessor();
          break;
        case BATCH:
          if (args.length < 3 || args.length > 4) {
            throw new IllegalArgumentException();
          }
          Reader in = null;
          Appendable out = null;
          in = new FileReader(formatFileName(args[2]));

          if (args.length == 4) {
            targetFileName = args[3].contains(DATA_DIR) ? args[3] : DATA_DIR + args[3];
            if (Files.exists(Path.of(targetFileName))) {
              targetFileWriter = new FileWriter(targetFileName);
            } else {
              targetFileWriter = new FileWriter(new File(targetFileName));
            }
            out = targetFileWriter;

          } else {
            out = System.out;
          }
          ioProcessor = new GameTextInputOutputProcessor(in, out);
          break;
        default:
          throw new IllegalArgumentException();
      }


      GameEngineApp game = new GameEngineApp(fileName,
              ioProcessor);
      game.start(option);
      if (targetFileWriter != null) {
        System.out.println("See output at: "  + targetFileName);
        targetFileWriter.close();
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Incorrect command-line format for game engine."
              + "\nFormats allowed:\ngame_engine <filename> -text"
              + "\ngame_engine <filename> -graphics"
              + "\ngame_engine <filename> -batch <source file>"
              + "\ngame_engine <filename> -batch <source file> <target file>");
      if (targetFileWriter != null) {
        targetFileWriter.close();
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + e.getMessage());
      if (targetFileWriter != null) {
        targetFileWriter.close();
      }
    } catch (IOException e) {
      System.out.println("Error reading from or writing to file: " + e.getMessage());
      if (targetFileWriter != null) {
        targetFileWriter.close();
      }
    }
  }

  private static String formatFileName(String fileName) throws FileNotFoundException {
    if (!Files.exists(Path.of(fileName))
            && !Files.exists(Path.of(System.getProperty("user.dir") + fileName))
            && !Files.exists(Path.of(DATA_DIR + fileName))) {
      throw new FileNotFoundException(fileName);
    } else if (Files.exists(Path.of(fileName)))
      return fileName;
    else if (Files.exists(Path.of(DATA_DIR + fileName)))
      return DATA_DIR + fileName;
    return System.getProperty("user.dir") + fileName;
  }
}
