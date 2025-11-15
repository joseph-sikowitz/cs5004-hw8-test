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
          this.out.append(model.Directions.NORTH.toString());
          //this.model.player.walk(NORTH)
        } else if (commandReader.getUserInputCommand().equalsIgnoreCase(UserCommands.SOUTH.getCommand())
                || commandReader.getUserInputCommand().equalsIgnoreCase(
                        UserCommands.SOUTH.getShortcut())) {
          this.out.append(model.Directions.SOUTH.toString());
          //this.model.player.walk(SOUTH)
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
