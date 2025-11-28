package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
  private GameInputOutputProcessor ioProcessor;
  private ICommand startGameCommand;
  private ICommand endOfTurnActions;

  // constants
  private static final String UNKNOWN_COMMAND = "Unknown command!\n";
  private static final String DATA_DIR = System.getProperty("user.dir") + "/resources/";
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
    this.ioProcessor = new GameTextInputOutputProcessor(this.in, this.out);
    this.loadCommands();
    this.startGameCommand = new StartGameCommand(this.model, this.ioProcessor);
    this.endOfTurnActions = new EndOfTurnActionsCommand(this.model, this.ioProcessor);
  }


  /**
   * Evaluates where a command from the commandReader matches a valid command from UserCommands
   * to pass to the IAdventureGameModel.
   * @param commandReader an instance of GameTextInputOutputProcessor
   * @param userCommand an instance of a UserCommands Enum.
   * @return true if command isn't null and matches a command
   *      (either full word or shortcut), otherwise false.
   */
  private boolean commandMatches(GameTextInputOutputProcessor commandReader,
                                 UserCommands userCommand) {
    return commandReader.getUserInputCommand() != null
            && (commandReader.getUserInputCommand().equalsIgnoreCase(userCommand.getCommand())
            || (userCommand.getShortcut() != null
            && commandReader.getUserInputCommand().equalsIgnoreCase(userCommand.getShortcut())));
  }

  /**
   * Evaluates where a command and an argument from the commandReader
   * matches a valid command from UserCommands
   * to pass to the IAdventureGameModel.
   * @param commandReader an instance of GameTextInputOutputProcessor
   * @param userCommand an instance of a UserCommands Enum.
   * @return true if command isn't null and matches a command (either full word or shortcut)
   *     and the argument isn't null, otherwise false.
   */
  private boolean commandAndArgumentMatches(GameTextInputOutputProcessor commandReader,
                                            UserCommands userCommand) {
    return commandReader.getUserInputArgument() != null
            && this.commandMatches(commandReader, userCommand);
  }

  /**
   * The startGame() method starts a game by prompting the user to put in their
   * name. It throws an IOException
   * TODO: Make this behavior into an ICommand
   * @return String of player's name.
   * @throws IOException if there is an error printing to output or getting user
   *                     data.
   */
  private String startGame(GameInputOutputProcessor gameCommandReader) throws IOException {
    gameCommandReader.messageToPlayer(UserPrompts.NEW_PLAYER_PROMPT.getPrompt());
    String playerName = gameCommandReader.getUserMessage();
    gameCommandReader.messageToPlayer(UserPrompts.NEW_PLAYER_NAME_PROMPT.getPrompt()
            + playerName  + "\n");
    return playerName;
  }

  @Override
  public void go() throws IOException {
    try {

      //this.model.setPlayerName(this.startGame(ioProcessor));
      //this.model.loadGameData();
      this.startGameCommand.execute();

      //print any warnings about the data from the model.
      //this.printGameFileWarnings();

      //initial look
      //this.ioProcessor.updateRoom(this.model.lookAround());
      this.commands.get(UserCommands.LOOK).execute();

      //this.executeEndOfTurnModelActions(true);
      this.endOfTurnActions.execute();
      //get user input while command is quit and model is still reporting that game isn't over.
      while (!this.model.gameOver() && ioProcessor.getUserInput()) {
        boolean playerCommandExecuted = true;
        if (this.isValidCommand(ioProcessor.getUserInputCommand())) {

          //uses command structure in commands Map instead of if/else
          UserCommands userCommand = this.findUserCommand(ioProcessor.getUserInputCommand());

          if (userCommand != null && this.requiresArgument(userCommand)
                  && ioProcessor.getUserInputArgument() == null) {
            this.ioProcessor.messageToPlayer(ioProcessor.getUserInputCommand()
                    + REQUIRED_ARGUMENT);
          } else if (userCommand != null) {
            if (userCommand.equals(UserCommands.SAVE)
                    || userCommand.equals(UserCommands.RESTORE))
              playerCommandExecuted = false;
            this.commands.get(userCommand).execute();
          }
        } else {
          this.ioProcessor.messageToPlayer(UNKNOWN_COMMAND);
        }
        //this.executeEndOfTurnModelActions(playerCommandExecuted);
        if (playerCommandExecuted) {
          this.endOfTurnActions.execute();
        }
      }
      //displays player stats
      this.ioProcessor.messageToPlayer(this.model.quitMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Executes model actions that affect the player and
   * prints player status if it has changed since the last command.
   * TODO: Make this into an ICommand
   */
  private void executeEndOfTurnModelActions(boolean playerCommandExecuted) throws IOException {
    if (playerCommandExecuted) {
      StringBuilder message = new StringBuilder();
      //if there is a Monster in the room, have it "affect" the Player in the model.
      message.append(this.model.affectPlayer());
      //display player's health status if it has changed since last command.
      if (this.model.changeInPlayerHealthStatus())
        message.append(this.model.getPlayerHealthStatus());
      //display player's score if it has changed since last command.
      if (this.model.changeInPlayerScore())
        message.append(this.model.getPlayerScoreFormatted());
      //display player's rank if it has changed since last command.
      if (this.model.changeInPlayerRank())
        message.append(this.model.getPlayerRank());
      this.ioProcessor.messageToPlayer(message.toString());
    }
  }

  /**
   * Prints warnings to the Player/user about possible errors in game data file.
   * TODO: Incorporate this into StartGameCommand
   */
  private void printGameFileWarnings() throws IOException {
    this.ioProcessor.messageToPlayer(this.model.getGameFileWarnings());
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

    this.commands.put(UserCommands.NORTH, new NorthCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.SOUTH, new SouthCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.EAST, new EastCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.WEST, new WestCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.INVENTORY, new InventoryCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.LOOK, new LookCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.USE, new UseCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.TAKE, new TakeCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.DROP, new DropCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.EXAMINE, new ExamineCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.ANSWER, new AnswerCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.SAVE, new SaveCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.RESTORE, new RestoreCommand(this.model, this.ioProcessor));
  }

  /**
   * The requiresArgument() method checks if a command requires an argument and
   * returns a boolean indicating whether it does or not.
   *
   * @param userCommand UserCommands enum to check if an argument is required.
   * @return boolean indicating if an argument is required.
   */
  private boolean requiresArgument(UserCommands userCommand) {
    Set<UserCommands> argumentCommands = EnumSet.of(UserCommands.USE, UserCommands.TAKE,
            UserCommands.DROP, UserCommands.EXAMINE, UserCommands.ANSWER);
    return argumentCommands.contains(userCommand);
  }

}
