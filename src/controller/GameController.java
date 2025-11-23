package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.IAdventureGameModel;

/**
 * The GameController class gets input from the user and sends it to the model
 * for processing. It appends the results from the model to the given output.
 * GameControllers have a Readable input, an Appendable output, commands, and a
 * GameModel to interact with the model through.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class GameController implements Controller {

  // attributes
  private IAdventureGameModel model;
  private final Readable in;
  private final Appendable out;
  private Map<UserCommands, ICommand> commands;

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
    this.loadCommands();
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
          if (!this.commandMatches(commandReader, UserCommands.SAVE)
                  && !this.commandMatches(commandReader, UserCommands.RESTORE)) {

            //uses command structure in commands Map instead of if/else
            UserCommands userCommand = this.findUserCommand(commandReader.getUserInputCommand());
            if (userCommand != null) {
              this.out.append(
                      this.commands.get(userCommand).execute(commandReader.getUserInputArgument()));
            }

          } else if (commandMatches(commandReader, UserCommands.SAVE)) {
            try {
              this.out.append("Game saved!\n");
              this.model.saveGame(DATA_DIR + DEFAULT_SAVE_FILE);
              playerCommandExecuted = false;
            } catch (Exception e) {
              this.out.append("Error saving game data!\n");
            }

          } else if (commandMatches(commandReader, UserCommands.RESTORE)) {
            try {
              this.model.restoreGame(DATA_DIR + DEFAULT_SAVE_FILE);
              this.out.append("Game restored!\n");
              this.out.append(this.model.restoreMessage());
            } catch (Exception e) {
              this.out.append("Error: could not restore game!\n");
            }
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
   * The findUserCommand() method matches a player's command input to the UserCommands
   * enum and returns the correct enum value. This is used to call commands in the
   * controller's commands Map.
   *
   * @param command String of command entered by user.
   * @return UserCommands enum that corresponds to the user command String.
   */
  private UserCommands findUserCommand(String command) {
    for (UserCommands userCommand : UserCommands.values()) {
      if (userCommand.getCommand().equalsIgnoreCase(command)) {
        return userCommand;
      } else if (userCommand.getShortcut().equalsIgnoreCase(command)) {
        return userCommand;
      }
    }

    return null;
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

  /**
   * The loadCommands() method loads the command classes into the controller's
   * commands Map so that they can be called by the user and executed.
   */
  private void loadCommands() {
    this.commands = new HashMap<>();

    this.commands.put(UserCommands.NORTH, new NorthCommand(this.model));
    this.commands.put(UserCommands.SOUTH, new SouthCommand(this.model));
    this.commands.put(UserCommands.EAST, new EastCommand(this.model));
    this.commands.put(UserCommands.WEST, new WestCommand(this.model));
    this.commands.put(UserCommands.INVENTORY, new InventoryCommand(this.model));
    this.commands.put(UserCommands.LOOK, new LookCommand(this.model));
    this.commands.put(UserCommands.USE, new UseCommand(this.model));
    this.commands.put(UserCommands.TAKE, new TakeCommand(this.model));
    this.commands.put(UserCommands.DROP, new DropCommand(this.model));
    this.commands.put(UserCommands.EXAMINE, new ExamineCommand(this.model));
    this.commands.put(UserCommands.ANSWER, new AnswerCommand(this.model));
  }

}
