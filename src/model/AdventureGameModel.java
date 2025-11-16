package model;

import java.io.IOException;
import java.util.Map;

public class AdventureGameModel implements IAdventureGameModel {

  // constants
  private String gameFileName;
  private String playerName;
  private Player player;
  private String warnings;
  private FileProcessor fileProcessor;

  // attributes
  private static final String ENTER = " You enter: ";

  public AdventureGameModel(String gameFileName) {
    this.gameFileName = gameFileName;
  }

  @Override
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public void loadGameData() {
    this.fileProcessor = new FileProcessor(this.gameFileName, this.playerName);
    this.player = this.fileProcessor.setUpGame();
    this.warnings = this.fileProcessor.getGameFileWarnings();
  }

  private String move(Directions direction) {
    RoomStatus status = this.player.walk(direction);

    return switch (status) {
      case BLOCKED -> RoomStatus.BLOCKED.getStatus();
      case NO_PASSAGE -> RoomStatus.NO_PASSAGE.getStatus();
      case OPEN -> RoomStatus.OPEN.getStatus() + ENTER
              + this.player.getActiveRoom().getName() + "\n"
              + this.player.getActiveRoom().getDescription();
    };
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
  public String examine(String element) {
    return this.player.examine(element);
  }

  @Override
  public String answer(String answer) {
    return "";
  }

  @Override
  public void saveGame() throws IOException {
    this.fileProcessor.saveGame(null);
  }

  @Override
  public void restoreGame() {
  }


  @Override
  public String getGameFileWarnings() {
    return this.warnings;
  }
}
