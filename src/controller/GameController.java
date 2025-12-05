package controller;

import java.io.IOException;
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
  private Map<UserCommands, ICommand> commands;
  private final GameInputOutputProcessor ioProcessor;
  private ICommand startGameCommand;
  private ICommand endOfTurnActions;

  /**
   * The GameController constructor instantiates a GameController object to be
   * used to take user input in order to interact with the model.
   *
   * @param ioProcessor an instance of GameInputOutputProcessor.
   * @param model and instance of IAdventureGameModel.
   */
  public GameController(GameInputOutputProcessor ioProcessor, IAdventureGameModel model) {
    this.ioProcessor = ioProcessor;
    this.loadCommands(model);
    this.startGameCommand = new StartGameCommand(model, this.ioProcessor);
    this.endOfTurnActions = new EndOfTurnActionsCommand(model, this.ioProcessor);
  }

  @Override
  public synchronized void go() throws IOException {
    try {
      //Run the start game command and check if next command is runnable.
      boolean gameRunnable = this.startGameCommand.execute();

      //first command is auto-executed during first phase of loop.
      UserCommands userCommand = UserCommands.LOOK;
      while (gameRunnable && this.commands.get(userCommand).execute()
              && this.executeEndOfTurnActions(userCommand.isPlayerCommand())) {

        //uses command structure in commands Map instead of if/else
        do {
          //get user input while commands can be executed.
          if (!this.ioProcessor.getUserCommand()) //TODO: refactor this such that getUserCommand throws the IOException.
            throw new IOException();
          userCommand = this.ioProcessor.getUserInputCommand();
        }
        while (userCommand == UserCommands.WAIT);

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
    this.commands.put(UserCommands.WAIT, new WaitCommand(model, this.ioProcessor));
  }

}
