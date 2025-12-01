package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import utilities.UserCommands;
import view.AdventureGameGraphicView;
import view.IAdventureGameView;

public class GameGraphicInputOutputProcessor implements GameInputOutputProcessor, ActionListener {

  private final IAdventureGameView<List<String>, GameGraphicInputOutputProcessor> gameView;
  private String userCommand;

  private static final String DEFAULT_PICTURE = "generic_location.png";

  private static final String DATA_DIR = System.getProperty("user.dir")
          + System.getProperty("file.separator") + "resources"
          + System.getProperty("file.separator") + "images"
          + System.getProperty("file.separator");

  public GameGraphicInputOutputProcessor() {
    this.userCommand = "";
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
  public UserCommands getUserInputCommand() {
    UserCommands command = UserCommands.findUserCommand(this.getRawUserInputCommand(),
            "");
    this.userCommand = "";
    return command;
  }

  @Override
  public String getRawUserInputCommand() {
    return this.userCommand;
  }

  @Override
  public String getUserInputArgument() {
    return "";
  }

  @Override
  public void messageToPlayer(List<String> data) throws IOException {
    // use list get first and get last
    this.gameView.messageToPlayer(data);
  }

  @Override
  public void messageToPlayer(String data) throws IOException {

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
    if (picturePath == null || picturePath.isEmpty()) {
      data.add(DATA_DIR + DEFAULT_PICTURE);
    } else {
      data.add(DATA_DIR + picturePath);
    }
    this.gameView.updateRoom(data);
  }

  @Override
  public void updateExaminer(List<String> data) throws IOException {

  }

  @Override
  public void updateInventory(List<String> data) throws IOException {
    this.gameView.updateInventory(data);
  }

  @Override
  public void updatePlayerAffector(List<String> data) throws IOException {
    String affectorList = String.join("\n", data);
    data.addFirst(affectorList);
    this.gameView.updatePlayerAffector(data);
  }

  @Override
  public void promptPlayer(String data) throws IOException {
    this.gameView.promptPlayer(data);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "north":
        this.userCommand = "north";
        break;
      case "south":
        this.userCommand = "south";
        break;
      case "west":
        this.userCommand = "west";
        break;
      case "east":
        this.userCommand = "east";
        break;
    }
  }
}
