package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import utilities.UserCommands;
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
  public boolean getUserCommand() throws IOException {
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
    this.gameView.updatePlayerStats(String.join("\n", data) + "\n");
  }

  @Override
  public void updateRoom(List<String> data) throws IOException {
    //remove room name
    data.removeFirst();
    //remove picture path
    data.removeLast();
    this.gameView.updateRoom(String.join("\n", data) + "\n");
  }

  @Override
  public void updateExaminer(List<String> data) throws IOException {
    this.gameView.updateExaminer(data.getFirst() + "\n");
  }

  @Override
  public void updateInventory(List<String> data) throws IOException {
    if (data.isEmpty())
      this.gameView.updateInventory("You have no items in your inventory!\n");
    else
      this.gameView.updateInventory(String.join(", ", data) + "\n");
  }

  @Override
  public void updatePlayerAffector(List<String> data) throws IOException {
    this.gameView.updatePlayerAffector(data.getFirst() + "\n");
  }

  @Override
  public void promptPlayer(String data) throws IOException {
    this.gameView.promptPlayer(data);
  }

  @Override
  public void updateFixtures(List<String> data) throws IOException {
    String fixtures = String.join(", ", data);
    String fixturesFormatted = fixtures.isEmpty() ? "" : "Fixtures you see here: "
            + fixtures + "\n";
    this.gameView.updateFixtures(fixturesFormatted);
  }

  @Override
  public void updateItems(List<String> data) throws IOException {
    String items = String.join(", ", data);
    String itemsFormatted = items.isEmpty() ? "" : "Items you see here: " + items + "\n";
    this.gameView.updateItems(itemsFormatted);
  }

  @Override
  public void updateTitle(String data) throws IOException {
    this.gameView.updateTitle(data);
  }

  @Override
  public void setUserCommands(Map<UserCommands, ICommand> commands) {}

  @Override
  public void setEndOfTurnActions(ICommand endOfTurnActions) {}

}
