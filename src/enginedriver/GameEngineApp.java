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
    GameController controller = new GameController(this.source, this.output, model);
    controller.go();
  }

  /**
   * Runs GameEngineApp game with a json data file.
   * @param args not used.
   * @throws IOException if file not found.
   */
  public static void main(String[] args) throws IOException {
    Reader in = null;
    Appendable out = null;
    FileWriter targetFileWriter = null;
    String targetFileName = null;

    try {
      if (args.length <= 1) {
        throw new IllegalArgumentException();
      }
      String fileName = formatFileName(args[0]);
      String option = args[1].toLowerCase();

      switch (option) {
        case TEXT:
          in = new InputStreamReader(System.in);
          out = System.out;
          break;
        case GRAPHICS:
          System.out.println("Not implemented yet!");
          return;
        case BATCH:
          if (args.length < 3 || args.length > 4) {
            throw new IllegalArgumentException();
          }
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
          break;
        default:
          throw new IllegalArgumentException();
      }


      GameEngineApp game = new GameEngineApp(fileName,
              in, out);
      game.start();
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
