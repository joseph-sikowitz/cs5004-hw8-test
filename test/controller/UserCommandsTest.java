package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utilities.UserCommands;

/**
 * Test suite for the UserCommands enum.
 */
class UserCommandsTest {

  /**
   * Tests the getter for the UserCommands full command.
   */
  @Test
  void testGetCommand() {
    assertEquals("north", UserCommands.NORTH.getCommand());
    assertEquals("south", UserCommands.SOUTH.getCommand());
    assertEquals("east", UserCommands.EAST.getCommand());
    assertEquals("west", UserCommands.WEST.getCommand());
    assertEquals("inventory", UserCommands.INVENTORY.getCommand());
    assertEquals("look", UserCommands.LOOK.getCommand());
    assertEquals("use", UserCommands.USE.getCommand());
    assertEquals("take", UserCommands.TAKE.getCommand());
    assertEquals("drop", UserCommands.DROP.getCommand());
    assertEquals("examine", UserCommands.EXAMINE.getCommand());
    assertEquals("answer", UserCommands.ANSWER.getCommand());
    assertEquals("save", UserCommands.SAVE.getCommand());
    assertEquals("restore", UserCommands.RESTORE.getCommand());
  }

  /**
   * Tests the getter for the UserCommands shortcut command.
   */
  @Test
  void testGetShortcut() {
    assertEquals("n", UserCommands.NORTH.getShortcut());
    assertEquals("s", UserCommands.SOUTH.getShortcut());
    assertEquals("e", UserCommands.EAST.getShortcut());
    assertEquals("w", UserCommands.WEST.getShortcut());
    assertEquals("i", UserCommands.INVENTORY.getShortcut());
    assertEquals("l", UserCommands.LOOK.getShortcut());
    assertEquals("u", UserCommands.USE.getShortcut());
    assertEquals("t", UserCommands.TAKE.getShortcut());
    assertEquals("d", UserCommands.DROP.getShortcut());
    assertEquals("x", UserCommands.EXAMINE.getShortcut());
    assertEquals("a", UserCommands.ANSWER.getShortcut());
  }
}