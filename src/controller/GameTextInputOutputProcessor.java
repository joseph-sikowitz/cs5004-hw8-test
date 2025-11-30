package controller;

import java.io.IOException;
import java.util.List;

import view.AdventureGameTextView;
import view.IAdventureGameView;


/**
 * The GameTextInputOutputProcessor class processes user input during game play
 * and sends output to the View.
 * GameCommandReaders have an array of user input and
 * an AdventureGameTextView to receive inputs and send outputs to.
 */
public class GameTextInputOutputProcessor implements GameInputOutputProcessor {

  // attributes
  private String[] userInput;
  private final IAdventureGameView<String, GameTextInputOutputProcessor> gameView;

  // constants
  private static final int FIRST_COMMAND = 0;
  private static final int SECOND_COMMAND = 1;
  private static final int COMMAND_LIMIT = 2;
  private static final int ONE_COMMAND_LENGTH = 1;
  private static final int TWO_COMMAND_LENGTH = 2;
  private static final String QUIT_COMMAND = "quit";
  private static final String Q_COMMAND = "q";

  /**
   * The GameTextInputOutputProcessor constructor takes a Readable source as its input
   * and an Appendable output to print results of actions to.
   *
   * @param source Readable for user input.
   * @param output Appendable for game output.
   */
  public GameTextInputOutputProcessor(Readable source, Appendable output) {
    this.userInput = new String[COMMAND_LIMIT];
    this.gameView = new AdventureGameTextView(source, output);
    this.gameView.setEventHandler(this);
  }


  @Override
  public boolean getUserInput() throws IOException {
    try {
      this.gameView.messageToPlayer(UserPrompts.BASIC_PROMPT.getPrompt());
      this.gameView.messageToPlayer(UserPrompts.USER_CHOICE.getPrompt());
      String command = this.gameView.getCommand();
      if (command == null) {
        return false;
      }
      this.userInput = this.parseCommand(command);
      return true;

    } catch (IOException e) {
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public String getUserMessage() throws IOException {
    return this.gameView.getCommand();
  }

  @Override
  public UserCommands getUserInputCommand() {
    return UserCommands.findUserCommand(this.getRawUserInputCommand(),
            this.getUserInputArgument());
  }

  @Override
  public String getRawUserInputCommand() {
    return this.userInput.length
            >= ONE_COMMAND_LENGTH ? this.userInput[FIRST_COMMAND].trim() : null;
  }

  @Override
  public String getUserInputArgument() {
    return this.userInput.length
            >= TWO_COMMAND_LENGTH ? this.userInput[SECOND_COMMAND].trim() : null;
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

  /**
   * Concatenates a List of Strings into a single String with a delimiter.
   * @param data a List of Strings
   * @param delimiter A String representing the delimiter to concatenate between elements.
   * @return a String with names of elements separated by commas.
   */
  private String concatenateList(List<String> data, String delimiter) {
    StringBuilder elements = new StringBuilder();
    if (data.isEmpty())
      return "";
    String lastElement = data.removeLast();
    return data.stream().map((element) -> element + delimiter)
            .reduce(elements, StringBuilder::append,
            StringBuilder::append).append(lastElement).append("\n").toString();
  }

  @Override
  public void messageToPlayer(List<String> data) throws IOException {
    this.gameView.messageToPlayer(data.getFirst() + "\n");
  }

  @Override
  public void messageToPlayer(String data) throws IOException {
    this.gameView.messageToPlayer(data + "\n");
  }

  @Override
  public void updatePlayerStats(List<String> data) throws IOException {
    this.gameView.updatePlayerStats(concatenateList(data, "\n"));
  }

  @Override
  public void updateRoom(List<String> data) throws IOException {
    //remove room name
    data.removeFirst();
    //remove picture path
    data.removeLast();
    this.gameView.updateRoom(concatenateList(data, "\n"));
  }

  @Override
  public void updateExaminer(List<String> data) throws IOException {
    this.gameView.updateExaminer(data.getFirst() + "\n");
  }

  @Override
  public void updateInventory(List<String> data) throws IOException {
    this.gameView.updateInventory(concatenateList(data, ", "));
  }

  @Override
  public void updatePlayerAffector(List<String> data) throws IOException {
    this.gameView.updatePlayerAffector(data.getFirst() + "\n");
  }

  @Override
  public void promptPlayer(String data) throws IOException {
    this.gameView.promptPlayer(data);
  }



}
