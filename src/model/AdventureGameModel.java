package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdventureGameModel implements IAdventureGameModel {

  // constants
  private String gameFileName;
  private String playerName;
  private Player player;
  private double playersLastHealth;
  private String warnings;
  private FileProcessor fileProcessor;
  private boolean firstDisplay;

  // attributes
  private static final String ENTER = " You enter: ";

  public AdventureGameModel(String gameFileName) {
    this.gameFileName = gameFileName;
    //set to value outside range to so that health status is communicated to user at start of game.
    this.playersLastHealth = -1.0;
    this.firstDisplay = true;
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

  /**
   * Concatenates String keys from a Map to a single String with keys seperated by commas.
   * @param map a Map with String as the key and a subtype of Element as the value.
   * @return a String with names of elements separated by commas.
   */
  private String getElementNames(Map<String, ? extends Element> map) {
    StringBuilder elements = new StringBuilder();
    List<String> names = new ArrayList<>(map.values().stream().map(Element::getName).toList());
    if (names.isEmpty())
      return "";
    String lastName = names.removeLast();
    return names.stream().map((key) -> key + ", ").reduce(elements, StringBuilder::append,
            StringBuilder::append).append(lastName).toString();
  }

  @Override
  public String checkInventory() {
    String playerInventory = getElementNames(this.player.getInventory());
    return playerInventory.isEmpty() ? "You have no items in your inventory!\n"
            : playerInventory + "\n";
  }

  @Override
  public String lookAround() {
    Room activeRoom = this.player.getActiveRoom();
    String roomDescription = firstDisplay ? "You start in " + activeRoom.getName() + ":\n" : "";
    roomDescription += activeRoom.getDescription() + "\n";
    this.firstDisplay = false;

    //hide visibility of items.
    if (activeRoom.getRoomEnvironmentAffector() != null
            && activeRoom.affectorAffectsPlayer()) {
      return roomDescription;
    }

    String fixtures = getElementNames(activeRoom.getFixtures());
    String fixturesFormatted = fixtures.isEmpty() ? "" : "Fixtures you see here: "
            + fixtures + "\n";
    String items = getElementNames(activeRoom.getItems());
    String itemsFormatted = items.isEmpty() ? "" : "Items you see here: " + items + "\n";

    return roomDescription + "\n"
            + fixturesFormatted
            + itemsFormatted;
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
    return item + this.player.takeItem(item).getStatus() + "\n";
  }

  @Override
  public String dropItem(String item) {
    if (this.player.dropItem(item))
      return item + " removed from your inventory!\n";
    return item + " not found in your inventory!\n";
  }

  @Override
  public String examine(String element) {
    return this.player.examine(element) + "\n";
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
    return this.player.getHealthStatus() == HealthStatus.SLEEP;
  }
}
