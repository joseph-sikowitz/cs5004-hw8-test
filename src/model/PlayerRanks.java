package model;

/**
 * The PlayerRanks enum contains the ranks that players can have during a
 * game. Each rank has a name used to print to the screen and a value used
 * to determine if the player's score has met the rank.
 *
 * @author Joe Sikowitz
 */
public enum PlayerRanks {
  NOVICE("Novice", 0, 99),
  SQUIRE("Squire", 100, 249),
  KNIGHT("Knight", 250, 499),
  BARON("Baron", 500, 999),
  PRINCE("Prince", 1000, 2499),
  KING("King", 2500, 10000);

  private final String name;
  private final int lowValue;
  private final int highValue;

  /**
   * The PlayerRanks constructor initializes its name and value.
   *
   * @param name String of the player's rank.
   * @param lowValue int of the rank's low value to compare to a player's score.
   * @param highValue int of the rank's high value to compare to a player's score.
   */
  PlayerRanks(String name, int lowValue, int highValue) {
    this.name = name;
    this.lowValue = lowValue;
    this.highValue = highValue;
  }

  /**
   * The getter for the rank name.
   *
   * @return String of the rank name.
   */
  String getName() {
    return this.name;
  }

  /**
   * The getter for the rank's low value.
   *
   * @return int of the rank's low value.
   */
  int getLowValue() {
    return this.lowValue;
  }

  /**
   * The getter for the rank's high value.
   *
   * @return int of the rank's high value.
   */
  int getHighValue() {
    return this.highValue;
  }
}
