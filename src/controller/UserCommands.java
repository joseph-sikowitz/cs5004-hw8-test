package controller;

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
  INVALID_COMMAND_ARGUMENT(null, null, false, false);

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
  String getCommand() {
    return this.command;
  }

  /**
   * The getter for the user command shortcut.
   *
   * @return String of the user command shortcut.
   */
  String getShortcut() {
    return this.shortcut;
  }


  /**
   * Returns whether the command counts as a turn in the game.
   * @return true if the command counts as a turn in the game, false otherwise.
   */
  boolean isPlayerCommand() {
    return this.playerCommand;
  }

  /**
   *
   * @return
   */
  boolean requiresArgument() {
    return this.requiresArgument;
  }
}
