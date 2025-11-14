package controller;

/**
 * The PlayerRanks enum contains the ranks that players can have during a
 * game. Each rank has a name used to print to the screen and a value used
 * to determine if the player's score has met the rank.
 *
 * @author Joe Sikowitz
 */
public enum PlayerRanks {
  NOVICE("Novice", 0),
  SQUIRE("Squire", 100),
  KNIGHT("Knight", 250),
  BARON("Baron", 500),
  PRINCE("Prince", 1000),
  KING("King", 2500);

  private final String name;
  private final int value;

  /**
   * The PlayerRanks constructor initializes its name and value.
   *
   * @param name String of the player's rank.
   * @param value int of the rank's value to compare to a player's score.
   */
  PlayerRanks(String name, int value) {
    this.name = name;
    this.value = value;
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
   * The getter for the rank value.
   *
   * @return int of the rank value.
   */
  int getValue() {
    return this.value;
  }
}
