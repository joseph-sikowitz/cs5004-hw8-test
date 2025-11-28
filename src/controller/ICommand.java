package controller;

import java.io.IOException;

/**
 * ICommand is the interface for controller commands in the adventure game.
 */
public interface ICommand {

  /**
   * The execute() method executes the command defined by the class.
   * @return true if commands can still be executed
   *      after this command finishes executing, otherwise false.
   * @throws IOException If I/O error occurs.
   */
  boolean execute() throws IOException;
}
