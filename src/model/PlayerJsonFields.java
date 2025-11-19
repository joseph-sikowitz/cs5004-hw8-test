package model;

/**
 * The PlayerJsonFields enum holds the fields that are stored in JSON for players
 * after saving a game to a file. PlayerJsonFields have a name for their values.
 */
public enum PlayerJsonFields {
  NAME("name"),
  DESCRIPTION("description"),
  SCORE("value"),
  HEALTH("health"),
  MAX_WEIGHT("max_weight"),
  INVENTORY("inventory"),
  ACTIVE_ROOM("active_room"),
  ITEMS_ADDED("items_added");

  private final String name;

  /**
   * The constructor for the PlayerJsonFields enum sets the name attribute to the
   * enum's name.
   *
   * @param name String of the enum's value.
   */
  PlayerJsonFields(String name) {
    this.name = name;
  }

  /**
   * The getValue() method is a getter for the enum's JSON field value.
   *
   * @return String of the enum's JSON field value.
   */
  String getValue() {
    return this.name;
  }
}
