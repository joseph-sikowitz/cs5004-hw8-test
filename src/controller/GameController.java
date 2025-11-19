package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.IAdventureGameModel;

/**
 * The GameController class gets input from the user and sends it to the model
 * for processing. It appends the results from the model to the given output.
 * GameControllers have a Readable input, an Appendable output, and a GameModel
 * to interact with the model through.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class GameController implements Controller {

  // attributes
  private IAdventureGameModel model;
  private final Readable in;
  private final Appendable out;

  // constants
  private static final String UNKNOWN_COMMAND = "Unknown command!\n";
  private static final String DATA_DIR = System.getProperty("user.dir") + "/data/";
  private static final String DEFAULT_SAVE_FILE = "save_file.json";
  private static final String REQUIRED_ARGUMENT = " requires an argument!\n";

  /**
   * The GameController constructor instantiates a GameController object to be
   * used to take user input in order to interact with the model.
   *
   * @param source Readable of the source of the game's input.
   * @param output Appendable of the game's output.
   */
  public GameController(Readable source, Appendable output, IAdventureGameModel model) {
    this.in = source;
    this.out = output;
    this.model = model;
  }

  /**
   * Evaluates where a command from the commandReader matches a valid command from UserCommands
   * to pass to the IAdventureGameModel.
   * @param commandReader an instance of GameCommandReader
   * @param userCommand an instance of a UserCommands Enum.
   * @return true if command isn't null and matches a command
   *      (either full word or shortcut), otherwise false.
   */
  private boolean commandMatches(GameCommandReader commandReader, UserCommands userCommand) {
    return commandReader.getUserInputCommand() != null
            && (commandReader.getUserInputCommand().equalsIgnoreCase(userCommand.getCommand())
            || (userCommand.getShortcut() != null
            && commandReader.getUserInputCommand().equalsIgnoreCase(userCommand.getShortcut())));
  }

  /**
   * Evaluates where a command and an argument from the commandReader
   * matches a valid command from UserCommands
   * to pass to the IAdventureGameModel.
   * @param commandReader an instance of GameCommandReader
   * @param userCommand an instance of a UserCommands Enum.
   * @return true if command isn't null and matches a command (either full word or shortcut)
   *     and the argument isn't null, otherwise false.
   */
  private boolean commandAndArgumentMatches(GameCommandReader commandReader,
                                            UserCommands userCommand) {
    return commandReader.getUserInputArgument() != null
            && this.commandMatches(commandReader, userCommand);
  }

  @Override
  public void go() throws IOException {
    try {
      GameCommandReader commandReader = new GameCommandReader(this.in, this.out);
      this.model.setPlayerName(commandReader.startGame());
      this.model.loadGameData();
      //print any warnings about the data from the model.
      this.printGameFileWarnings();

      //initial look
      this.out.append(this.model.lookAround());

      this.executeEndOfTurnModelActions(true);

      //get user input while command is quit and model is still reporting that game isn't over.
      while (!this.model.gameOver() && commandReader.getUserInput()) {
        boolean playerCommandExecuted = true;
        if (this.isValidCommand(commandReader.getUserInputCommand())) {
          if (this.commandMatches(commandReader, UserCommands.NORTH)) {
            this.out.append(this.model.movePlayerNorth());

          } else if (this.commandMatches(commandReader, UserCommands.SOUTH)) {
            this.out.append(this.model.movePlayerSouth());

          } else if (this.commandMatches(commandReader, UserCommands.EAST)) {
            this.out.append(this.model.movePlayerEast());

          } else if (this.commandMatches(commandReader, UserCommands.WEST)) {
            this.out.append(this.model.movePlayerWest());

          } else if (this.commandMatches(commandReader, UserCommands.INVENTORY)) {
            this.out.append(this.model.checkInventory());

          } else if (this.commandMatches(commandReader, UserCommands.LOOK)) {
            this.out.append(this.model.lookAround());

          } else if (this.commandAndArgumentMatches(commandReader, UserCommands.USE)) {
            this.out.append(this.model.useItem(commandReader.getUserInputArgument()));

          } else if (this.commandAndArgumentMatches(commandReader, UserCommands.TAKE)) {
            this.out.append(this.model.takeItem(commandReader.getUserInputArgument()));

          } else if (this.commandAndArgumentMatches(commandReader, UserCommands.DROP)) {
            this.out.append(this.model.dropItem(commandReader.getUserInputArgument()));

          } else if (this.commandAndArgumentMatches(commandReader, UserCommands.EXAMINE)) {
            this.out.append(this.model.examine(commandReader.getUserInputArgument()));

          } else if (this.commandAndArgumentMatches(commandReader, UserCommands.ANSWER)) {
            this.out.append(this.model.answer(commandReader.getUserInputArgument()));

          } else if (commandMatches(commandReader, UserCommands.SAVE)) {
            this.out.append("Game saved!\n");
            this.model.saveGame(DATA_DIR + DEFAULT_SAVE_FILE);
            playerCommandExecuted = false;

          } else if (commandMatches(commandReader, UserCommands.RESTORE)) {
            this.model.restoreGame(DATA_DIR + DEFAULT_SAVE_FILE);
            this.out.append("Game restored!\n");
            this.out.append(this.model.restoreMessage());
            playerCommandExecuted = false;

          } else if (this.isValidCommand(commandReader.getUserInputCommand())
                  && commandReader.getUserInputArgument() == null) {
            this.out.append(commandReader.getUserInputCommand()).append(REQUIRED_ARGUMENT);
          }
        } else if (this.isValidCommand(commandReader.getUserInputCommand())
                && commandReader.getUserInputArgument() == null) {
          this.out.append(commandReader.getUserInputCommand()).append(REQUIRED_ARGUMENT);
        } else {
          this.out.append(UNKNOWN_COMMAND);
        }
        this.executeEndOfTurnModelActions(playerCommandExecuted);
      }
      //displays player stats
      this.out.append(this.model.quitMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Executes model actions that affect the player and
   * prints player status if it has changed since the last command.
   */
  private void executeEndOfTurnModelActions(boolean playerCommandExecuted) throws IOException {
    if (playerCommandExecuted) {
      //if there is a Monster in the room, have it "affect" the Player in the model.
      this.out.append(this.model.affectPlayer());
      //display player's health status if it has changed since last command.
      if (this.model.changeInPlayerHealthStatus())
        this.out.append(this.model.getPlayerHealthStatus());
      //display player's score if it has changed since last command.
      if (this.model.changeInPlayerScore())
        this.out.append(this.model.getPlayerScoreFormatted());
      //display player's rank if it has changed since last command.
      if (this.model.changeInPlayerRank())
        this.out.append(this.model.getPlayerRank());
    }
  }

  /**
   * Prints warnings to the Player/user about possible errors in game data file.
   */
  private void printGameFileWarnings() throws IOException {
    this.out.append(this.model.getGameFileWarnings());
  }

  /**
   * The isValidCommand() method checks if the command input by a user is valid.
   *
   * @param command String of user command.
   * @return boolean indicating if the given command is valid.
   */
  private boolean isValidCommand(String command) {
    if (command == null)
      return false;

    UserCommands[] options = UserCommands.values();
    ArrayList<String> allCommands = new ArrayList<>();

    for (UserCommands userCommand : options) {
      allCommands.add(userCommand.getCommand());
      allCommands.add(userCommand.getShortcut());
    }

    return allCommands.contains(command.toLowerCase());
  }

  /**
   * The getUserCommand() method converts a user's String command into the
   * enum that it corresponds to in UserCommands.
   *
   * @param command String of command entered by user.
   * @return UserCommand with a value matching the command entered by user.
   */
  private UserCommands getUserCommand(String command) {
    UserCommands[] options = UserCommands.values();

    for (UserCommands userCommand : options) {
      if (userCommand.getCommand().equalsIgnoreCase(command)
              || userCommand.getShortcut().equalsIgnoreCase(command)) {
        return userCommand;
      }
    }

    return null;
  }

}
