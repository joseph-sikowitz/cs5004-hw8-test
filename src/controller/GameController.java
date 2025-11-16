package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.AdventureGameModel;
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
  private static final String UNKNOWN_COMMAND = "Unknown command\n";

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
   * The go() method starts the game by prompting the user to enter their username and
   * then capturing it. After that, it accepts user input commands that make calls to
   * the model for interactive game play until the user quits the game.
   *
   * @throws IOException if there is an error using the provided input or output.
   */
  @Override
  public void go() throws IOException {
    try {
      GameCommandReader commandReader = new GameCommandReader(this.in, this.out);
      this.model.setPlayerName(commandReader.startGame());
      this.model.loadGameData();

      while (commandReader.getUserInput()) {
        if (this.isValidCommand(commandReader.getUserInputCommand())) {
          if (commandReader.getUserInputCommand().equalsIgnoreCase(UserCommands.NORTH.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.NORTH.getShortcut())) {
            this.out.append(this.model.movePlayerNorth());

          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.SOUTH.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.SOUTH.getShortcut())) {
            this.out.append(this.model.movePlayerSouth());

          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.EAST.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.EAST.getShortcut())) {
            this.out.append(this.model.movePlayerEast());

          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.WEST.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.WEST.getShortcut())) {
            this.out.append(this.model.movePlayerWest());

          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.INVENTORY.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.INVENTORY.getShortcut())) {
            this.out.append(UserCommands.INVENTORY.getCommand()); // temp - remove
            //this.model.player.getInventory()
          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.LOOK.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.LOOK.getShortcut())) {
            this.out.append(UserCommands.LOOK.getCommand()); // temp - remove
            //this.model.player.getActiveRoom().getDescription();
          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.USE.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.USE.getShortcut())) {
            this.out.append(UserCommands.USE.getCommand()); // temp - remove
            //this.model.player.useItem(commandReader.getUserInputArgument(),this.model.player.room.enemy);
          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.TAKE.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.TAKE.getShortcut())) {
            this.out.append(UserCommands.TAKE.getCommand()); // temp - remove
            //this.model.player.takeItem(commandReader.getUserInputArgument());
          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.DROP.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.DROP.getShortcut())) {
            this.out.append(UserCommands.DROP.getCommand()); // temp - remove
            //this.model.player.dropItem(commandReader.getUserInputArgument());
          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.EXAMINE.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.EXAMINE.getShortcut())) {
            this.out.append(UserCommands.EXAMINE.getCommand()); // temp - remove
            //this.model.player.examine(commandReader.getUserInputArgument());
          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.ANSWER.getCommand())
                  || commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.ANSWER.getShortcut())) {
            this.out.append(UserCommands.ANSWER.getCommand()); // temp - remove
            //this.model.player.answer(commandReader.getUserInputArgument());
          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.SAVE.getCommand())) {
            this.out.append(UserCommands.SAVE.getCommand()); // temp - remove
            //save game state to file
          } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                  UserCommands.RESTORE.getCommand())) {
            this.out.append(UserCommands.RESTORE.getCommand()); // temp - remove
            //restore game from file
          }
        } else {
          this.out.append(UNKNOWN_COMMAND);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * The isValidCommand() method checks if the command input by a user is valid.
   *
   * @param command String of user command.
   * @return boolean indicating if the given command is valid.
   */
  public boolean isValidCommand(String command) {
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
  public UserCommands getUserCommand(String command) {
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
