package controller;

import java.io.IOException;
import java.util.Scanner;

/**
 * The GameCommandReader class starts the game by getting the user's name
 * and processes user input during game play. GameCommandReaders have
 * an array of user input, a Readable to get user input, and an Appendable
 * to print results to. They also have a player name.
 */
public class GameCommandReader {

  // attributes
  private String[] userInput;
  private final Readable in;
  private final Appendable out;
  private String playerName;

  // constants
  private static final int FIRST_COMMAND = 0;
  private static final int SECOND_COMMAND = 1;
  private static final int COMMAND_LIMIT = 2;
  private static final String QUIT_COMMAND = "quit";
  private static final String Q_COMMAND = "q";

  /**
   * The GameCommandReader constructor takes a Readable source as its input
   * and an Appendable output to print results of actions to.
   *
   * @param source Readable for user input.
   * @param output Appendable for game output.
   */
  public GameCommandReader(Readable source, Appendable output) {
    this.in = source;
    this.out = output;
    this.userInput = new String[COMMAND_LIMIT];
  }

  /**
   * The startGame() method starts a game by prompting the user to put in their
   * name. It throws an IOException
   *
   * @return String of player's name.
   * @throws IOException if there is an error printing to output or getting user
   *                     data.
   */
  public String startGame() throws IOException {
    try {
      this.out.append(UserPrompts.PLAYER_RANK_PROMPT.getPrompt()).append(
              PlayerRanks.NOVICE.getName()).append("\n");
      this.out.append(UserPrompts.NEW_PLAYER_PROMPT.getPrompt());

      Scanner scanner = new Scanner(this.in);
      this.playerName = scanner.nextLine();

      this.out.append(UserPrompts.NEW_PLAYER_NAME_PROMPT.getPrompt()).append(
              this.playerName).append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

    return this.playerName;
  }

  /**
   * The getUserInput() method prompts the user for game actions and
   * accepts user input for game play.
   *
   * @return boolean indicating if the game is over.
   * @throws IOException if there is an error appending to output or receiving
   *                     user input.
   */
  public boolean getUserInput() throws IOException {
    try {
      this.out.append(UserPrompts.BASIC_PROMPT.getPrompt());
      this.out.append(UserPrompts.USER_CHOICE.getPrompt());

      Scanner scanner = new Scanner(this.in);
      String input = scanner.nextLine();
      this.userInput = this.parseCommand(input);

      return !this.userInput[0].equalsIgnoreCase(QUIT_COMMAND)
              && !this.userInput[0].equalsIgnoreCase(Q_COMMAND);

    } catch (IOException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * The getUserInputCommand() is the getter for a command entered by the user.
   * A command is the first word or character entered by a character in an
   * input String.
   *
   * @return String of user command.
   */
  public String getUserInputCommand() {
    return this.userInput.length >= 1 ? this.userInput[FIRST_COMMAND] : null;
  }

  /**
   * The getUserInputArgument() method is the getter for the command argument
   * entered by the user. A command argument is any text following the first
   * word and a space in the input String.
   *
   * @return String of user argument.
   */
  public String getUserInputArgument() {
    return this.userInput.length >= 2 ? this.userInput[SECOND_COMMAND] : null;
  }

  /**
   * The parseCommand() method splits a user's command into two parts. The command
   * and the command argument and returns them as an array of Strings.
   *
   * @param command String of the command input by the user.
   * @return String[] with the first index the command and the second index the argument.
   */
  private String[] parseCommand(String command) {
    String delimiter = " ";
    return command.split(delimiter, COMMAND_LIMIT);
  }
}
