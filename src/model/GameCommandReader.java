package model;

import java.io.IOException;
import java.util.Scanner;

import controller.UserCommands;

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

  public GameCommandReader(Readable source, Appendable output) {
    this.in = source;
    this.out = output;
    this.userInput = new String[COMMAND_LIMIT];
  }

  public void startGame() throws IOException {
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
  }

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

  public String getUserInputCommand() {
    return this.userInput[FIRST_COMMAND];
  }

  public String getUserInputArgument() {
    return this.userInput[SECOND_COMMAND];
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
