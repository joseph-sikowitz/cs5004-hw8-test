package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.GameCommandReader;

public class GameController implements Controller {

  // attributes
  //private GameModel model;
  private final Readable in;
  private final Appendable out;

  // constants
  private static final String UNKNOWN_COMMAND = "Unknown command\n";

  public GameController(Readable source, Appendable output) {
    this.in = source;
    this.out = output;

    // this.model = model
  }

  @Override
  public void go() throws IOException {
    GameCommandReader commandReader = new GameCommandReader(this.in, this.out);
    commandReader.startGame();

    while (commandReader.getUserInput()) {
      if (this.isValidCommand(commandReader.getUserInputCommand())) {
        if (commandReader.getUserInputCommand().equalsIgnoreCase(UserCommands.NORTH.getCommand())
                || commandReader.getUserInputCommand().equalsIgnoreCase(
                        UserCommands.NORTH.getShortcut())) {
          this.out.append(model.Directions.NORTH.toString()); // temp - remove
          //this.model.player.walk(model.Directions.NORTH)
          //TODO: figure out how to pass to player's walk method
        } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                UserCommands.SOUTH.getCommand())
                || commandReader.getUserInputCommand().equalsIgnoreCase(
                        UserCommands.SOUTH.getShortcut())) {
          this.out.append(model.Directions.SOUTH.toString()); // temp - remove
          //this.model.player.walk(model.Directions.SOUTH)
        } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                UserCommands.EAST.getCommand())
                || commandReader.getUserInputCommand().equalsIgnoreCase(
                UserCommands.EAST.getShortcut())) {
          this.out.append(model.Directions.EAST.toString()); // temp - remove
          //this.model.player.walk(model.Directions.EAST)
        } else if (commandReader.getUserInputCommand().equalsIgnoreCase(
                UserCommands.WEST.getCommand())
                || commandReader.getUserInputCommand().equalsIgnoreCase(
                UserCommands.WEST.getShortcut())) {
          this.out.append(model.Directions.WEST.toString()); // temp - remove
          //this.model.player.walk(model.Directions.WEST)
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
  }

  public boolean isValidCommand(String command) {
    UserCommands[] options = UserCommands.values();
    ArrayList<String> allCommands = new ArrayList<>();

    for (UserCommands userCommand : options) {
      allCommands.add(userCommand.getCommand());
      allCommands.add(userCommand.getShortcut());
    }

    return allCommands.contains(command.toLowerCase());
  }

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
