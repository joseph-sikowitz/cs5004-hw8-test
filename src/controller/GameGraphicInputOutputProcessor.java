package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import view.IAdventureGameGraphicView;

public class GameGraphicInputOutputProcessor implements GameInputOutputProcessor, ActionListener {

  private final IAdventureGameGraphicView<List<String>> gameView;
  private String rawUserCommand;
  private String lastUserCommand;
  private String userInputArgument;
  private Map<UserCommands, ICommand> commands;
  private List<String> roomData;

  private static final String DEFAULT_PICTURE = "generic_location.png";
  private static final String DEFAULT_ITEM = "generic_item.png";

  private static final String IMG_DIR = System.getProperty("user.dir")
          + System.getProperty("file.separator") + "resources"
          + System.getProperty("file.separator") + "images"
          + System.getProperty("file.separator");

  /**
   * Initializes the processor with a IAdventureGameGraphicView type.
   * @param gameView an IAdventureGameGraphicView type
   *      that receives a List of Strings as its data.
   */
  public GameGraphicInputOutputProcessor(IAdventureGameGraphicView<List<String>> gameView) {
    this.rawUserCommand = null;
    this.lastUserCommand = null;
    this.userInputArgument = null;
    this.gameView = gameView;
    this.gameView.setEventHandler(this);
  }

  @Override
  public String getUserMessage() throws IOException {
    return this.gameView.getCommand();
  }

  @Override
  public boolean getUserCommand() throws IOException {
    return true; //TODO: Refactor this.
  }

  @Override
  public synchronized UserCommands getUserInputCommand() {
    return UserCommands.findUserCommand(this.getRawUserInputCommand(),
            this.getUserInputArgument());
  }

  @Override
  public String getLastUserInputCommand() {
    return this.lastUserCommand;
  }

  @Override
  public String getRawUserInputCommand() {
    this.lastUserCommand = this.rawUserCommand;
    this.rawUserCommand = null;
    return this.lastUserCommand;
  }

  @Override
  public String getUserInputArgument() {
    return this.userInputArgument;
  }

  @Override
  public void messageToPlayer(List<String> data) throws IOException {
    // use list get first and get last
    if (!data.isEmpty()) {
      String picturePath = data.removeLast();
      if (picturePath == null || picturePath.isEmpty()) {
        data.add(IMG_DIR + DEFAULT_ITEM);
      } else {
        data.add(IMG_DIR + picturePath);
      }
      this.gameView.messageToPlayer(data);
    }
  }

  @Override
  public void messageToPlayer(String data) throws IOException {
    List<String> dataList = new ArrayList<>();
    dataList.add(data);
    this.gameView.messageToPlayer(dataList);
  }

  @Override
  public void updatePlayerStats(List<String> data) throws IOException {
    this.gameView.updatePlayerStats(data);
  }

  @Override
  public void updateRoom(List<String> data) throws IOException {
    String picturePath = data.removeLast();
    data.add(data.removeLast() + "\n");
    if (picturePath == null || picturePath.isEmpty()) {
      data.add(IMG_DIR + DEFAULT_PICTURE);
    } else {
      data.add(IMG_DIR + picturePath);
    }
    this.roomData = data;
    this.gameView.updateRoom(data);
  }

  @Override
  public void updateInventory(List<String> data) throws IOException {
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
  public void quit(String data) throws IOException {
    this.gameView.quit(data);
  }

  @Override
  public synchronized void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    this.rawUserCommand = command.split(" \r ")[0];
    this.userInputArgument = (command.split(" \r ").length >= 2) ? command.split(" \r ")[1] : null;
  }
}
