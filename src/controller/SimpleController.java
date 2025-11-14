package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

import model.InputProcessor;

/**
 * The SimpleController class is a simplified version of the controller for the
 * adventure game app. SimpleControllers have a game file name, a source of input,
 * an Appendable output, a boolean indicating if this is a new game, a boolean
 * indicating if the game is over, and a Map of commandFunctions that is triggered
 * by user input.
 *
 * @author Joe Sikowitz
 */
public class SimpleController {
  // Should this be a singleton or static?

  // attributes
  private String gameFileName;
  private Readable source;
  private Appendable output;
  private boolean newGame;
  private boolean gameOver;
  private Map<String, Runnable> commandFunctions;

  // constants
  private static final int COMMAND_LIMIT = 2;
  private static final int COMMAND_TYPE_INDEX = 0;
  private static final int COMMAND_ARGUMENT_INDEX = 1;

  /**
   * The constructor initializes the attributes of the SimpleController class. It runs
   * initializeGameCommandFunctions() to add the user commands to a hashmap that will be
   * used to issue commands.
   *
   * @param gameFileName String of the input file to initialize the game.
   * @param source Readable input source from the user.
   * @param output Appendable output source for game data.
   */
  public SimpleController(String gameFileName, Readable source, Appendable output) {
    this.gameFileName = gameFileName;
    this.source = source;
    this.output = output;
    this.newGame = true;
    this.gameOver = false;

    this.commandFunctions = new HashMap<>();
    this.initializeGameCommandFunctions();
    this.initializePlayerDirectionCommandFunctions();
    this.initializePlayerActionCommands();
  }

  /**
   * The go() method starts the adventure game and interacts with the model and
   * the user.
   *
   * @throws IOException if given an invalid file name.
   */
  public void go() throws IOException {
    try {
      InputProcessor inputProcessor = new InputProcessor(this.gameFileName, new HashMap<>());
      this.newGame = inputProcessor.setUpGame();
      this.playGame();
    } catch (IOException e) {
      throw new IOException(e);
    }
  }

  /**
   * The playGame() method runs the game using user input until the player falls asleep
   * or the user quits.
   *
   * @throws IOException if there is a problem reading input or appending to output.
   */
  private void playGame() throws IOException {
    try {
      if (this.newGame) {
        this.newGameInitialization();
      } else {
        // Welcome back {player.playerName}
        // Your score: {player.playerScore}
      }

      this.userChoicePrompt();

      while (!this.gameOver) {
        this.playGameDriver();
      }
    } catch (IOException e) {
      throw new IOException(e);
    }
  }

  /**
   * The gamePlayDriver() method drives the main gameplay by prompting the
   * user to enter commands and outputting the results. It is a helper method
   * of playGame().
   *
   * @throws IOException if there is an issue reading input or appending to output.
   */
  private void playGameDriver() throws IOException {
    try {
      Scanner userInput = new Scanner(this.source);
      String input = userInput.nextLine();
      String[] commandList = this.parseCommand(input);

      if (this.commandFunctions.containsKey(commandList[COMMAND_TYPE_INDEX].toLowerCase())) {
        this.commandFunctions.get(commandList[COMMAND_TYPE_INDEX].toLowerCase()).run();
        if (!(UserCommands.QUIT.getCommand().equalsIgnoreCase(
                commandList[COMMAND_TYPE_INDEX])
                || UserCommands.QUIT.getShortcut().equalsIgnoreCase(
                        commandList[COMMAND_TYPE_INDEX]))) {
          this.userChoicePrompt();
        }
      } else {
        this.output.append(UserPrompts.UNKNOWN_COMMAND.getPrompt());
        this.userChoicePrompt();
      }
    } catch (IOException e) {
      throw new IOException(e);
    }
  }

  /**
   * The initializeGameCommandFunctions() method is a helper method of the
   * constructor that sets up the commands for managing the game such as
   * quit, save, and restore by adding them to the commandFunctions Map.
   */
  private void initializeGameCommandFunctions() {
    this.commandFunctions.put(UserCommands.QUIT.getCommand(), () -> this.quitAction());
    this.commandFunctions.put(UserCommands.QUIT.getShortcut(), () -> this.quitAction());

    this.commandFunctions.put(UserCommands.SAVE.getCommand(), () -> {
      try {
        this.saveAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.RESTORE.getCommand(), () -> {
      try {
        this.restoreAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );
  }

  /**
   * The initializePlayerDirectionCommandFunctions() method adds the directional
   * commands that a user can use and the functions that they call to a Map.
   */
  private void initializePlayerDirectionCommandFunctions() {
    this.commandFunctions.put(UserCommands.NORTH.getCommand(), () -> {
      try {
        this.northAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.NORTH.getShortcut(), () -> {
      try {
        this.northAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.SOUTH.getCommand(), () -> {
      try {
        this.southAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.SOUTH.getShortcut(), () -> {
      try {
        this.southAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.EAST.getCommand(), () -> {
      try {
        this.eastAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.EAST.getShortcut(), () -> {
      try {
        this.eastAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.WEST.getCommand(), () -> {
      try {
        this.westAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.WEST.getShortcut(), () -> {
      try {
        this.westAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );
  }

  /**
   * The initializePlayerActionCommands() method adds the commands that
   * a player uses to interact with elements in rooms to a Map for later
   * execution.
   */
  private void initializePlayerActionCommands() {
    this.commandFunctions.put(UserCommands.INVENTORY.getCommand(), () -> {
      try {
        this.inventoryAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.INVENTORY.getShortcut(), () -> {
      try {
        this.inventoryAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.LOOK.getCommand(), () -> {
      try {
        this.lookAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.LOOK.getShortcut(), () -> {
      try {
        this.lookAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.USE.getCommand(), () -> {
      try {
        this.useAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.USE.getShortcut(), () -> {
      try {
        this.useAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.TAKE.getCommand(), () -> {
      try {
        this.takeAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.TAKE.getShortcut(), () -> {
      try {
        this.takeAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.TAKE.getCommand(), () -> {
      try {
        this.takeAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.TAKE.getShortcut(), () -> {
      try {
        this.takeAction(); }
      catch (IOException e) {
       System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.DROP.getCommand(), () -> {
      try {
        this.dropAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.DROP.getShortcut(), () -> {
      try {
        this.dropAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.EXAMINE.getCommand(), () -> {
      try {
        this.examineAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.EXAMINE.getShortcut(), () -> {
      try {
        this.examineAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.ANSWER.getCommand(), () -> {
      try {
        this.answerAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

    this.commandFunctions.put(UserCommands.ANSWER.getShortcut(), () -> {
      try {
        this.answerAction(); }
      catch (IOException e) {
        System.out.println(e.getMessage()); } }
    );

  }

  private void quitAction() {
    // TODO: print player score
    this.gameOver = true;
  }

  private void saveAction() throws IOException {
    // TODO: save game to file
    this.output.append(UserPrompts.GAME_SAVED.getPrompt());
  }

  private void restoreAction() throws IOException {
    // TODO: restore game from file
    this.output.append(UserPrompts.GAME_RESTORED.getPrompt());
  }

  private void northAction() throws IOException {
    //TODO: move player north if possible
    // remove this output later
    this.output.append("Moving north\n");
  }

  private void southAction() throws IOException {
    //TODO: move player south if possible
    // remove this output later
    this.output.append("Moving south\n");
  }

  private void eastAction() throws IOException {
    //TODO: move player east if possible
    // remove this output later
    this.output.append("Moving east\n");
  }

  private void westAction() throws IOException {
    //TODO: move player west if possible
    // remove this output later
    this.output.append("Moving west\n");
  }

  private void inventoryAction() throws IOException {
      //TODO: display player inventory
      // remove this output later
      this.output.append("Checking inventory\n");
  }

  private void lookAction() throws IOException {
        //TODO: output what is in room
        // remove this output later
    this.output.append("Looking around\n");
  }

  private void useAction() throws IOException {
        //TODO: output what is in room
        // remove this output later
    this.output.append("Looking around\n");
  }

  private void takeAction() throws IOException {
      //TODO: output what is in room
      // remove this output later
      this.output.append("Taking something\n");
  }

  private void dropAction() throws IOException {
          //TODO: output what is in room
          // remove this output later
    this.output.append("Taking something\n");
  }

  private void examineAction() throws IOException {
        //TODO: output what is in room
        // remove this output later
    this.output.append("Taking something\n");
  }

  private void answerAction() throws IOException {
        //TODO: output what is in room
        // remove this output later
    this.output.append("Taking something\n");
  }

  /**
   * The userChoicePrompt() method is used to append to the output the basic
   * user choice menu.
   *
   * @throws IOException if there is an error appending to output.
   */
  private void userChoicePrompt() throws IOException {
    try {
      this.output.append(UserPrompts.BASIC_PROMPT.getPrompt());
      this.output.append(UserPrompts.USER_CHOICE.getPrompt());
    } catch (IOException e) {
      throw new IOException(e);
    }
  }

  /**
   * The newGameInitialization() method is a helper method of playGame() that sets up
   * a new player by prompting the user for the necessary input.
   *
   * @throws IOException if there is an error receiving input or appending to output.
   */
  private void newGameInitialization() throws IOException {
    try {
      this.output.append(UserPrompts.PLAYER_RANK_PROMPT.getPrompt()
              + PlayerRanks.NOVICE.getName() + "\n");
      this.output.append(UserPrompts.NEW_PLAYER_PROMPT.getPrompt());
      Scanner userInput = new Scanner(this.source);
      String playerName = userInput.nextLine();
      this.output.append(UserPrompts.NEW_PLAYER_NAME_PROMPT.getPrompt() + playerName + "\n");
      // TODO: use player setter to set playerName
    } catch (IOException e) {
      throw new IOException(e);
    }
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
