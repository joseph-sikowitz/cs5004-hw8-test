package controller;

import java.io.IOException;

/**
 * ICommand is the interface for controller commands in the adventure game.
 */
public interface ICommand {

  /**
   * The execute() method executes the command defined by the class.
   * @throws IOException If I/O error occurs.
   */
  void execute() throws IOException;
}
