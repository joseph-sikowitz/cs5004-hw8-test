package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utilities.UserCommands;
import view.AdventureGameGraphicView;
import view.IAdventureGameView;

public class GameGraphicInputOutputProcessor implements GameInputOutputProcessor, ActionListener {

  private final IAdventureGameView<List<String>, GameGraphicInputOutputProcessor> gameView;
  private UserCommands userCommand;
  private String rawUserCommand;
  private String userInputCommand;
  private String userInputArgument;
  private Map<UserCommands, ICommand> commands;
  private ICommand endOfTurnActions;
  private List<String> roomData;

  private static final String DEFAULT_PICTURE = "generic_location.png";

  private static final String DATA_DIR = System.getProperty("user.dir")
          + System.getProperty("file.separator") + "resources"
          + System.getProperty("file.separator") + "images"
          + System.getProperty("file.separator");

  public GameGraphicInputOutputProcessor() {
    this.rawUserCommand = null;
    this.userInputArgument = null;
    this.gameView = new AdventureGameGraphicView();
    this.gameView.setEventHandler(this);
  }

  @Override
  public String getUserMessage() throws IOException {
    return this.gameView.getCommand();
  }

  @Override
  public boolean getUserCommand() throws IOException {
    return true;
  }

  @Override
  public synchronized UserCommands getUserInputCommand() {
    //UserCommands command = UserCommands.findUserCommand(this.getRawUserInputCommand(), "");
    //this.userCommand = "";
    this.userCommand = UserCommands.findUserCommand(this.getRawUserInputCommand(),
            this.getUserInputArgument());
    System.out.println(this.gameView.getCommand());
    return this.userCommand;
  }

  @Override
  public String getRawUserInputCommand() {
    String rawUserCommand = this.rawUserCommand;
    this.rawUserCommand = null;
    return rawUserCommand;
  }

  @Override
  public String getUserInputArgument() {
    return "";
  }

  @Override
  public void messageToPlayer(List<String> data) throws IOException {
    // use list get first and get last
    if (!data.isEmpty())
      this.gameView.messageToPlayer(data);
  }

  @Override
  public void messageToPlayer(String data) throws IOException {
    List<String> dataList = new ArrayList<>();
    dataList.add(data);
    this.gameView.messageToPlayer(dataList);
  }

  @Override
  public void updatePlayerStats(List<String> data) throws IOException {
    String playerStatus = String.join("\n", data);
    data.addFirst(playerStatus);
    this.gameView.updatePlayerStats(data);
  }

  @Override
  public void updateRoom(List<String> data) throws IOException {
    String picturePath = data.removeLast();
    data.add(data.removeLast() + "\n");
    if (picturePath == null || picturePath.isEmpty()) {
      data.add(DATA_DIR + DEFAULT_PICTURE);
    } else {
      data.add(DATA_DIR + picturePath);
    }
    this.roomData = data;
    this.gameView.updateRoom(data);
  }

  @Override
  public void updateInventory(List<String> data) throws IOException {
    this.roomData = data;
    this.gameView.updateInventory(data);
  }


  @Override
  public void promptPlayer(String data) throws IOException {
    this.gameView.promptPlayer(data);
  }

  @Override
  public void updateFixtures(List<String> data) throws IOException {
    if (this.roomData != null && !data.isEmpty()) {
      String roomData = this.roomData.remove(1);
      roomData += "Fixtures you see here: " + String.join(", ", data) + "\n";
      this.roomData.add(1, roomData);
      this.gameView.updateRoom(this.roomData);
    }
    this.gameView.updateFixtures(data);
  }

  @Override
  public void updateItems(List<String> data) throws IOException {
    if (this.roomData != null && !data.isEmpty()) {
      String roomData = this.roomData.remove(1);
      roomData += "Items you see here: " + String.join(", ", data) + "\n";
      this.roomData.add(1, roomData);
      this.gameView.updateRoom(this.roomData);
    }
    this.gameView.updateItems(data);
  }

  @Override
  public void updateTitle(String data) throws IOException {
    this.gameView.updateTitle(data);
  }

  @Override
  public synchronized void actionPerformed(ActionEvent e) {
    //this.executeCommand(e.getActionCommand());
    this.rawUserCommand = e.getActionCommand();
    this.userInputArgument = null;
  }

  private void executeCommand(String actionCommand) {
    try {
      this.commands.get(UserCommands.findUserCommand(actionCommand, "")).execute();
      this.commands.get(UserCommands.INVENTORY).execute();
      this.endOfTurnActions.execute();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  @Override
  public void setUserCommands(Map<UserCommands, ICommand> commands) {
    this.commands = commands;
  }

  @Override
  public void setEndOfTurnActions(ICommand endOfTurnActions) {
    this.endOfTurnActions = endOfTurnActions;
  }

}
