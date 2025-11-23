package controller;

/**
 * ICommand is the interface for controller commands in the adventure game.
 */
public interface ICommand {

  /**
   * The execute() method executes the command defined by the class.
   *
   * @return String of result of attempting to move player.
   */
  String execute(String userArgument);
}
