package model;

import static java.nio.file.Files.exists;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * The AdventureGameModel class defines the game play. It is the main point
 * of communication for the controller. AdventureGameModels have a game file name,
 * a player name, a player object, the last health value of the player, warnings,
 * a file processor object, and a first display boolean.
 */
public class AdventureGameModel implements IAdventureGameModel {

  // constants
  private String gameFileName;
  private String playerName;
  private Player player;
  private double playersLastHealth;
  private double playersLastScore;
  private PlayerRanks playersLastRank;
  private String warnings;
  private FileProcessor fileProcessor;
  private boolean firstDisplay;
  private String activeRoomName;
  private boolean roomEnvironmentChanged;

  private final DecimalFormat decimalFormat;

  // attributes
  private static final double DEFAULT_HEALTH = -1.0;
  private static final double DEFAULT_SCORE = -1.0;

  /**
   *  The constructor initializes the model by taking in a game file name,
   * initializing the player's last health to a default and the first display
   *  to true.
   * @param gameFileName String of the game file name to load into the model.
   * @param decimalFormat Decimal format of numerical data to output to controller.
   */
  public AdventureGameModel(String gameFileName, DecimalFormat decimalFormat) {
    this.gameFileName = gameFileName;
    //set to value outside range to so that status is communicated to user at start of game.
    this.playersLastHealth = DEFAULT_HEALTH;
    this.playersLastScore = DEFAULT_SCORE;
    this.playersLastRank = null;
    this.firstDisplay = true;
    this.decimalFormat = decimalFormat;
  }

  /**
   * The constructor initializes the model by taking in a game file name,
   * initializing the player's last health to a default and the first display
   * to true.
   *
   * @param gameFileName String of the game file name to load into the model.
   */
  public AdventureGameModel(String gameFileName) {
    this(gameFileName, new DecimalFormat("0.##"));
  }


  @Override
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public void loadGameData() {
    this.fileProcessor = (this.playerName != null && !this.playerName.isEmpty())
            ? new FileProcessor(this.gameFileName, this.playerName)
            : new FileProcessor(this.gameFileName);
    this.player = this.fileProcessor.setUpGame();
    this.warnings = this.fileProcessor.getGameFileWarnings();
    this.activeRoomName = this.player.getActiveRoom().getName();
  }

  @Override
  public boolean roomChanged() {
    boolean roomChanged = !this.getRoomName().equals(this.activeRoomName)
            || this.roomEnvironmentChanged;
    this.activeRoomName = this.getRoomName();
    this.roomEnvironmentChanged = false;
    return roomChanged;
  }

  @Override
  public String getRoomName() {
    return this.player.getActiveRoom().getName();
  }

  /**
   * The move() method moves a player into another room or returns an
   * error message indicating that the player cannot move into a blocked
   * or impassable room.
   *
   * @param direction Directions enum indicating the direction to move the player.
   * @return String of result of the player attempting to move.
   */
  private String move(Directions direction) {
    RoomStatus status;
    try {
      status = this.player.walk(direction);
    } catch (Exception e) {
      return e.getMessage();
    }

    return switch (status) {
      case BLOCKED -> RoomStatus.BLOCKED.getStatus();
      case NO_PASSAGE -> RoomStatus.NO_PASSAGE.getStatus();
      case OPEN -> RoomStatus.OPEN.getStatus() + " You enter: "
              + this.player.getActiveRoom().getName();
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
   * Concatenates String keys from a Map to a single String with keys separated by commas.
   * @param map a Map with String as the key and a subtype of Element as the value.
   * @return a String with names of elements separated by commas.
   */
  private String getElementNamesConcatenated(Map<String, ? extends Element> map) {
    StringBuilder elements = new StringBuilder();
    List<String> names = getElementNames(map);
    if (names.isEmpty())
      return "";
    String lastName = names.removeLast();
    return names.stream().map((key) -> key + ", ").reduce(elements, StringBuilder::append,
            StringBuilder::append).append(lastName).toString();
  }

  /**
   * Returns a List of Element names from a Map of Elements hashed by their names.
   * @param map a Map of Elements hashed by their names.
   * @return a List of Strings representing element names.
   */
  private List<String> getElementNames(Map<String, ? extends Element> map) {
    return new ArrayList<>(map.values().stream().map(Element::getName).toList());
  }

  @Override
  public List<String> checkInventory() {
    //String playerInventory = getElementNamesConcatenated(this.player.getInventory());
    List<String> playerInventory = getElementNames(this.player.getInventory());
    if (playerInventory.isEmpty())
      playerInventory.add("You have no items in your inventory!");
    return playerInventory;
  }

  @Override
  public List<String> lookAround() {
    Room activeRoom = this.player.getActiveRoom();
    String roomDescription = firstDisplay ? "You start in " + activeRoom.getName() + ":\n" : "";
    roomDescription += activeRoom.getDescription();
    this.firstDisplay = false;
    Puzzle enemy = this.player.getActiveRoom().getRoomEnvironmentAffector();
    String picture = (enemy != null && enemy.isActive()) ? enemy.getPicturePath() : this.player.getActiveRoom().getPicturePath();

    //hide visibility of items.
    if (activeRoom.getRoomEnvironmentAffector() != null
            && activeRoom.affectorAffectsPlayer()) {
      return  new ArrayList<>(Arrays.asList(this.getRoomName(), roomDescription,
             picture));
    }

    String fixtures = getElementNamesConcatenated(activeRoom.getFixtures());
    String fixturesFormatted = fixtures.isEmpty() ? "" : "Fixtures you see here: "
            + fixtures + "\n";
    String items = getElementNamesConcatenated(activeRoom.getItems());
    String itemsFormatted = items.isEmpty() ? "" : "Items you see here: " + items;
    roomDescription += "\n" + fixturesFormatted + itemsFormatted;
    return new ArrayList<>(Arrays.asList(this.getRoomName(), roomDescription,
            picture));
  }

  /**
   * Private helper for outputting return type from solving Puzzles/Monsters
   *     with items/answers.
   * @param getUse an instance of UseSuccessful.
   * @return a return messages depending on the states UseSuccessful fields.
   */
  private String solvePuzzle(UseSuccessful getUse) {
    String returnMessage = getUse.getUse();
    this.roomEnvironmentChanged = getUse.getUseSuccessful();
    return returnMessage;
  }

  @Override
  public String useItem(String item) {
    return solvePuzzle(this.player.useItem(item));
  }

  @Override
  public String answer(String answer) {
    return solvePuzzle(this.player.answer(answer));
  }

  @Override
  public String takeItem(String item) {
    return item + this.player.takeItem(item).getStatus();
  }

  @Override
  public String dropItem(String item) {
    if (this.player.dropItem(item))
      return item + " removed from your inventory!";
    return item + " not found in your inventory!";
  }

  @Override
  public List<String> examine(String element) {
    return this.player.examine(element);
  }


  @Override
  public boolean changeInPlayerHealthStatus() {
    if (this.playersLastHealth != this.player.getHealth()) {
      this.playersLastHealth = this.player.getHealth();
      return true;
    }
    return false;
  }

  @Override
  public String getPlayerHealthStatus() {
    String adjective = this.player.getHealthStatus()
            == HealthStatus.FULL_HEALTH ? "becoming less " : "becoming more ";
    if (this.player.getHealth() == this.player.getHealthStatus().getMaxHealth()
            || this.changeInPlayerHealthStatus())
      adjective = this.player.getHealthStatus() == HealthStatus.FULL_HEALTH ? "fully " : "now ";
    return "You are " + adjective + this.player.getHealthStatus().getHealthStatus();
  }

  @Override
  public List<String> affectPlayer() {
    Monster enemy = this.player.getActiveRoom().getMonster();
    if (enemy != null && enemy.attack(this.player)) {
      String returnMessage = enemy.getName() + " " + enemy.getAttackDescription();
      returnMessage += "\nYou take " + decimalFormat.format(enemy.getDamage()) + " damage!";
      return new ArrayList<>(Arrays.asList(returnMessage,  enemy.getPicturePath()));
    }
    return new ArrayList<>(Arrays.asList("", null));
  }

  @Override
  public String saveGame(String saveFile) throws FileNotFoundException {
    try {
      this.fileProcessor.saveGame(saveFile);
    } catch (Exception e) {
      throw new FileNotFoundException("File was not saved!");
    }
    return "Game saved!";
  }

  @Override
  public String restoreGame(String saveFile) throws FileNotFoundException {
    if (!exists(Path.of(saveFile)))
      throw new FileNotFoundException("File was not found!");
    this.fileProcessor = new FileProcessor(saveFile, this.playerName);
    this.player = this.fileProcessor.setUpGame();
    this.warnings = this.fileProcessor.getGameFileWarnings();
    return "Game restored!";
  }


  @Override
  public String getGameFileWarnings() {
    return this.warnings;
  }


  @Override
  public String restoreMessage() {
    return "Welcome back " + this.getPlayerName() + "\n" + this.getPlayerHealthStatus() + "\n"
            + this.getPlayerScoreFormatted() + "\n" + this.getPlayerRank()
            + "\nYou are currently in " + this.getRoomName() + ":";
  }

  @Override
  public String quitMessage() {
    return "Thanks for playing!\nFinal score: " + decimalFormat.format(this.player.getScore())
            + " \n" + this.getPlayerRank();
  }

  @Override
  public boolean gameOver() {
    return this.player.getHealthStatus() == HealthStatus.ZERO_HEALTH;
  }

  @Override
  public String getPlayerName() {
    return this.player.getName();
  }

  @Override
  public boolean changeInPlayerScore() {
    if (this.playersLastScore != this.getPlayerScore()) {
      this.playersLastScore = this.getPlayerScore();
      return true;
    }
    return false;
  }

  @Override
  public double getPlayerScore() {
    return this.player.getScore();
  }

  @Override
  public String getPlayerScoreFormatted() {
    return "Your current score: " + this.decimalFormat.format(this.getPlayerScore());
  }

  @Override
  public boolean changeInPlayerRank() {
    if (this.playersLastRank != this.getPlayerRanks()) {
      this.playersLastRank = this.getPlayerRanks();
      return true;
    }
    return false;
  }

  @Override
  public String getPlayerRank() {
    return "Your rank: " + getPlayerRanks().getName();
  }

  /**
   * Helper method that computes the Player's current rank from the PlayerRanks enum type.
   */
  private PlayerRanks getPlayerRanks() {
    for (PlayerRanks playerRank : PlayerRanks.values()) {
      if (this.getPlayerScore() >= playerRank.getLowValue()
              && this.getPlayerScore() < playerRank.getHighValue()) {
        return playerRank;
      }
    }
    return PlayerRanks.NOVICE;
  }
}
