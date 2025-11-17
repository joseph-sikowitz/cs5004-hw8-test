package model;

import java.io.IOException;
import java.util.Map;

public class AdventureGameModel implements IAdventureGameModel {

  // constants
  private String gameFileName;
  private String playerName;
  private Player player;
  private double playersLastHealth;
  private String warnings;
  private FileProcessor fileProcessor;

  // attributes
  private static final String ENTER = " You enter: ";

  public AdventureGameModel(String gameFileName) {
    this.gameFileName = gameFileName;
    //set to value outside range to so that health status is communicated to user at start of game.
    this.playersLastHealth = -1.0;
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
      case BLOCKED -> RoomStatus.BLOCKED.getStatus() + "\n";
      case NO_PASSAGE -> RoomStatus.NO_PASSAGE.getStatus() + "\n";
      case OPEN -> RoomStatus.OPEN.getStatus() + ENTER
              + this.player.getActiveRoom().getName() + "\n"
              + this.lookAround() + "\n";
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


  private String getElementNames(Map<String, ? extends Element> map) {
    StringBuilder elements = new StringBuilder();
    return map.keySet().stream().reduce(elements, StringBuilder::append, StringBuilder::append).toString();
  }

  @Override
  public String checkInventory() {
    return getElementNames(this.player.getInventory());
  }

  @Override
  public String lookAround() {
    Room activeRoom = this.player.getActiveRoom();
    return activeRoom.getDescription() + "\n"
            + "Fixtures you see here: " + getElementNames(activeRoom.getFixtures()) + "\n"
            + "Items you see here: " +  getElementNames(activeRoom.getItems()) + "\n";
  }

  @Override
  public String useItem(String item) {
    UseSuccessful getUse = this.player.useItem(item);
    String returnMessage = getUse.getUse();
    if (getUse.getUseSuccessful())
      returnMessage += "\n" + lookAround();
    return returnMessage;
  }

  @Override
  public String takeItem(String item) {
    if (this.player.takeItem(item))
      return item + " added to your inventory!\n";
    return item + " not added to your inventory!\n";
  }

  @Override
  public String dropItem(String item) {
    if (this.player.dropItem(item))
      return item + " removed from your inventory!\n";
    return item + " not found in your inventory!\n";
  }

  @Override
  public String examine(String element) {
    return this.player.examine(element);
  }

  @Override
  public String answer(String answer) {
    if (this.player.answer(answer))
      return this.lookAround();
    return "Answer had no effect!\n";
  }

  @Override
  public boolean changeInHealthStatus() {
    if (this.playersLastHealth != this.player.getHealth()) {
      this.playersLastHealth = this.player.getHealth();
      return true;
    }
    return false;
  }

  @Override
  public String playerHealthStatus() {
    String adjective = this.player.getHealth() == this.player.getHealthStatus().getMaxHealth()
            ? "fully " : "still ";
    return "You are " + adjective + this.player.getHealthStatus().getHealthStatus() + "\n";
  }

  @Override
  public String affectPlayer() {
    Monster enemy = this.player.getActiveRoom().getMonster();
    if (enemy != null && enemy.attack(this.player)) {
      String returnMessage = enemy.getName() + " " + enemy.getAttackDescription();
      returnMessage += "\nYou take " + enemy.getDamage() + " damage!\n";
      return returnMessage;
    }
    return "";
  }

  @Override
  public void saveGame(String saveFile) throws IOException {
    this.fileProcessor.saveGame(saveFile);
  }

  @Override
  public void restoreGame(String saveFile) throws IOException {
    this.fileProcessor = new FileProcessor(saveFile, this.playerName);
    this.player = this.fileProcessor.setUpGame();
    this.warnings = this.fileProcessor.getGameFileWarnings();
  }


  @Override
  public String getGameFileWarnings() {
    return this.warnings;
  }


  @Override
  public String quitMessage() {
    return "Thanks for playing!\nFinal score: " + this.player.getScore() + " \n";
  }

  @Override
  public boolean gameOver() {
    return this.player.getHealthStatus() != HealthStatus.SLEEP;
  }
}
