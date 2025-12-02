package utilities;

/**
 * The UserCommands enum contains the commands that users are allowed to input
 * into the game. Each command has a full command and a shortcut.
 *
 * @author Joe Sikowitz
 */
public enum UserCommands {
  NORTH("north", "n", true, false),
  SOUTH("south", "s", true, false),
  EAST("east", "e", true, false),
  WEST("west", "w",true, false),
  INVENTORY("inventory", "i", true, false),
  LOOK("look", "l", true, false),
  USE("use", "u", true, true),
  TAKE("take", "t", true, true),
  DROP("drop", "d", true, true),
  EXAMINE("examine", "x", true, true),
  ANSWER("answer", "a", true, true),
  SAVE("save", "save", false, false),
  RESTORE("restore", "restore", false, false),
  QUIT("quit", "q", false, false),
  INVALID_COMMAND(null, null, false, false),
  INVALID_COMMAND_ARGUMENT(null, null, false, false),
  WAIT(null, null, false, false);

  private final String command;
  private final String shortcut;
  private final boolean playerCommand;
  private final boolean requiresArgument;

  /**
   * The UserCommands constructor initializes the attributes of the command.
   *
   * @param command String of the user command.
   * @param shortcut String of the user command's shortcut.
   */
  UserCommands(String command, String shortcut, boolean playerCommand, boolean requiresArgument) {
    this.command = command;
    this.shortcut = shortcut;
    this.playerCommand = playerCommand;
    this.requiresArgument = requiresArgument;
  }

  /**
   * The getter for the user command.
   *
   * @return String of the user command.
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * The getter for the user command shortcut.
   *
   * @return String of the user command shortcut.
   */
  public String getShortcut() {
    return this.shortcut;
  }


  /**
   * Returns whether the command counts as a turn in the game.
   * @return true if the command counts as a turn in the game, false otherwise.
   */
  public boolean isPlayerCommand() {
    return this.playerCommand;
  }

  /**
   * Returns whether the UserCommand requires an argument.
   * @return true if the UserCommand requires an argument, false otherwise.
   */
  public boolean requiresArgument() {
    return this.requiresArgument;
  }

  /**
   * The findUserCommand() method matches a player's command input to the UserCommands
   * enum and returns the correct enum value. This is used to call commands in the
   * controller's commands Map.
   *
   * @param command String of command entered by user.
   * @param argument String of argument entered by user.
   * @return UserCommands enum that corresponds to the user command String.
   */
  public static UserCommands findUserCommand(String command, String argument) {
    if (command != null) {
      for (UserCommands userCommand : UserCommands.values()) {
        if ((userCommand.getCommand() != null && userCommand.getCommand().equalsIgnoreCase(command))
                || (userCommand.getShortcut() != null
                && userCommand.getShortcut().equalsIgnoreCase(command))) {
          //check if userCommand requires Argument and check if argument is not null.
          return (!userCommand.requiresArgument() || argument != null)
                  ? userCommand : UserCommands.INVALID_COMMAND_ARGUMENT;
        }
      }
    }
    return command == null ? UserCommands.WAIT :  UserCommands.INVALID_COMMAND;
  }
}
