package controller;

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

  @Override
  public void go() throws IOException {
    try {
      //Run the start game command and check if next command is runnable.
      boolean gameRunnable = this.startGameCommand.execute();

      //first command is auto-executed during first phase of loop.
      UserCommands userCommand = UserCommands.LOOK;
      while (gameRunnable && this.commands.get(userCommand).execute()
              && this.executeEndOfTurnActions(userCommand.isPlayerCommand())) {
        //get user input while commands can be executed.
        if (!this.ioProcessor.getUserInput()) //TODO: refactor this such that getUserInput throws the IOException.
          throw new IOException();
        //uses command structure in commands Map instead of if/else
        userCommand = this.findUserCommand(ioProcessor.getUserInputCommand());
        if (userCommand != null && userCommand.requiresArgument()
                && ioProcessor.getUserInputArgument() == null) {
          userCommand = UserCommands.INVALID_COMMAND_ARGUMENT;
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Executes model actions that affect the player and
   * prints player status if it has changed since the last command.
   * @return true if game is still runnable after executing actions.
   */
  private boolean executeEndOfTurnActions(boolean playerCommandExecuted) throws IOException {
    if (playerCommandExecuted) {
      return this.endOfTurnActions.execute();
    }
    return true;
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
      if (userCommand.getCommand() != null && userCommand.getCommand().equalsIgnoreCase(command)) {
        return userCommand;
      } else if (userCommand.getShortcut() != null
              && userCommand.getShortcut().equalsIgnoreCase(command)) {
        return userCommand;
      }
    }

    return UserCommands.INVALID_COMMAND;
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
    this.commands.put(UserCommands.QUIT, new QuitCommand(this.model, this.ioProcessor));
    this.commands.put(UserCommands.INVALID_COMMAND, new InvalidCommand(null, this.ioProcessor));
    this.commands.put(UserCommands.INVALID_COMMAND_ARGUMENT,
            new InvalidArgumentCommand(null, this.ioProcessor));
  }

}
