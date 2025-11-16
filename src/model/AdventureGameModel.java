package model;

import java.util.Map;

public class AdventureGameModel implements IAdventureGameModel {

  private String gameFileName;
  private String playerName;
  private Player player;

  public AdventureGameModel(String gameFileName) {
    this.gameFileName = gameFileName;
  }

  @Override
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public void loadGameData() {
    InputProcessor inputProcessor = new InputProcessor(this.gameFileName);
    boolean newGame = inputProcessor.setUpGame();

    if (newGame) {
      // create new player
      // this.player = inputProcessor.createNewPlayer(this.playerName);
      System.out.println(this.playerName + " has been loaded");
    } else {
      // use player from saved file
      // this.player = inputProcessor.createSavedPlayer();
    }
  }

  @Override
  public String movePlayerNorth() {
    return "";
  }

  @Override
  public String movePlayerSouth() {
    return "";

  }

  @Override
  public String movePlayerEast() {
    return "";

  }

  @Override
  public String movePlayerWest() {
    return "";
  }

  @Override
  public Map<String, Item> checkInventory() {
    return Map.of();
  }

  @Override
  public String lookAround() {
    return "";
  }

  @Override
  public void useItem(String item) {

  }

  @Override
  public void takeItem(String item) {

  }

  @Override
  public void dropItem(String item) {

  }

  @Override
  public String examine(Element element) {
    return "";
  }

  @Override
  public String answer(String answer) {
    return "";
  }

  @Override
  public void saveGame() {

  }

  @Override
  public void restoreGame() {

  }
}
