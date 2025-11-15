package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * The UserCommands enum contains the commands that users are allowed to input
 * into the game. Each command has a full command and a shortcut.
 *
 * @author Joe Sikowitz
 */
public enum UserCommands {
  NORTH("north", "n"),
  SOUTH("south", "s"),
  EAST("east", "e"),
  WEST("west", "w"),
  INVENTORY("inventory", "i"),
  LOOK("look", "l"),
  USE("use", "u"),
  TAKE("take", "t"),
  DROP("drop", "d"),
  EXAMINE("examine", "x"),
  ANSWER("answer", "a");

  private final String command;
  private final String shortcut;

  /**
   * The UserCommands constructor initializes the attributes of the command.
   *
   * @param command String of the user command.
   * @param shortcut String of the user command's shortcut.
   */
  UserCommands(String command, String shortcut) {
    this.command = command;
    this.shortcut = shortcut;
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
}
