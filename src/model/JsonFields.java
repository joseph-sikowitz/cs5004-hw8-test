package model;

/**
 * The JsonFields enum represents the high-level field names found in an adventure
 * game JSON file provided as input to initialize the game. Each enum has a String
 * value that corresponds to the field in the JSON.
 *
 * @author Joe Sikowitz
 */
public enum JsonFields {
  NAME("name"),
  VERSION("version"),
  ROOMS("rooms"),
  ITEMS("items"),
  FIXTURES("fixtures"),
  MONSTERS("monsters"),
  PUZZLES("puzzles"),
  PLAYER("player");

  private final String value;

  /**
   * The constructor for the JsonFields enum initializes the value of the enum.
   *
   * @param value String of the enum's value.
   */
  JsonFields(String value) {
    this.value = value;
  }

  /**
   * The getter for the enum's value.
   *
   * @return String of the enum's value.
   */
  String getValue() {
    return this.value;
  }
}
