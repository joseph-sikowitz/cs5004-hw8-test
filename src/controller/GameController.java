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
  private Map<UserCommands, ICommand> commands;
  private GameInputOutputProcessor ioProcessor;
  private ICommand startGameCommand;
  private ICommand endOfTurnActions;

  /**
   * The GameController constructor instantiates a GameController object to be
   * used to take user input in order to interact with the model.
   *
   * @param source Readable of the source of the game's input.
   * @param output Appendable of the game's output.
   */
  public GameController(Readable source, Appendable output, IAdventureGameModel model) {
    this.ioProcessor = new GameTextInputOutputProcessor(source, output);
    this.loadCommands(model);
    this.startGameCommand = new StartGameCommand(model, this.ioProcessor);
    this.endOfTurnActions = new EndOfTurnActionsCommand(model, this.ioProcessor);
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
   * @param model an instance of IAdventureGameModel.
   */
  private void loadCommands(IAdventureGameModel model) {
    this.commands = new HashMap<>();

    this.commands.put(UserCommands.NORTH, new NorthCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.SOUTH, new SouthCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.EAST, new EastCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.WEST, new WestCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.INVENTORY, new InventoryCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.LOOK, new LookCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.USE, new UseCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.TAKE, new TakeCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.DROP, new DropCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.EXAMINE, new ExamineCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.ANSWER, new AnswerCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.SAVE, new SaveCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.RESTORE, new RestoreCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.QUIT, new QuitCommand(model, this.ioProcessor));
    this.commands.put(UserCommands.INVALID_COMMAND, new InvalidCommand(null, this.ioProcessor));
    this.commands.put(UserCommands.INVALID_COMMAND_ARGUMENT,
            new InvalidArgumentCommand(null, this.ioProcessor));
  }

}
