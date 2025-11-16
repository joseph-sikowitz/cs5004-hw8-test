package model;

import java.util.Map;

public class AdventureGameModel implements IAdventureGameModel {

  // constants
  private String gameFileName;
  private String playerName;
  private Player player;

  // attributes
  private static final String ENTER = " You enter.";

  public AdventureGameModel(String gameFileName) {
    this.gameFileName = gameFileName;
  }

  @Override
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public void loadGameData() {
    InputProcessor inputProcessor = new InputProcessor(this.gameFileName, this.playerName);
    this.player = inputProcessor.setUpGame();
  }

  private String move(Directions direction) {
    RoomStatus status = this.player.walk(direction);

    if (status == RoomStatus.BLOCKED) {
      return RoomStatus.BLOCKED.getStatus();
    } else if (status == RoomStatus.NO_PASSAGE) {
      return RoomStatus.NO_PASSAGE.getStatus();
    } else {
      String successMessage = status.getStatus() + ENTER + "\n"
              + this.player.getActiveRoom().getDescription();
      return successMessage;
    }
  }

  @Override
  public String movePlayerNorth() {
    return move(Directions.NORTH);
  }

  @Override
  public String movePlayerSouth() {
    return move(Directions.SOUTH);
  }

  @Override
  public String movePlayerEast() {
    return move(Directions.EAST);
  }

  @Override
  public String movePlayerWest() {
    return move(Directions.WEST);
  }

  @Override
  public Map<String, Item> checkInventory() {
    return Map.of();
  }

  @Override
  public String lookAround() {
    return this.player.getActiveRoom().getDescription();
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
