package controller;

import java.io.IOException;
import java.util.List;

import utilities.UserCommands;
import view.AdventureGameGraphicView;
import view.IAdventureGameView;

public class GameGraphicInputOutputProcessor implements GameInputOutputProcessor {

  private final IAdventureGameView<String, GameGraphicInputOutputProcessor> gameView;

  public GameGraphicInputOutputProcessor() {
    this.gameView = new AdventureGameGraphicView();
  }

  @Override
  public String getUserMessage() throws IOException {
    return this.gameView.getCommand();
  }

  @Override
  public boolean getUserInput() throws IOException {
    return false;
  }

  @Override
  public UserCommands getUserInputCommand() {
    return null;
  }

  @Override
  public String getRawUserInputCommand() {
    return "";
  }

  @Override
  public String getUserInputArgument() {
    return "";
  }

  @Override
  public void messageToPlayer(List<String> data) throws IOException {
    // use list get first and get last
  }

  @Override
  public void messageToPlayer(String data) throws IOException {
    this.gameView.messageToPlayer(data);
  }

  @Override
  public void updatePlayerStats(List<String> data) throws IOException {

  }

  @Override
  public void updateRoom(List<String> data) throws IOException {

  }

  @Override
  public void updateExaminer(List<String> data) throws IOException {

  }

  @Override
  public void updateInventory(List<String> data) throws IOException {

  }

  @Override
  public void updatePlayerAffector(List<String> data) throws IOException {

  }

  @Override
  public void promptPlayer(String data) throws IOException {
    this.gameView.promptPlayer(data);
  }
}
