package model;

/**
 * The RoomJsonFields represent each value a Room will have in a JSON input file. Each
 * value is mapped to a name.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public enum RoomJsonFields {
  ROOM_NAME("room_name"),
  ROOM_NUMBER("room_number"),
  DESCRIPTION("description"),
  NORTH("N"),
  SOUTH("S"),
  EAST("E"),
  WEST("W"),
  PUZZLE("puzzle"),
  MONSTER("monster"),
  ITEMS("items"),
  FIXTURES("fixtures"),
  PICTURE("picture");

  private String name;

  /**
   * The constructor for the RoomJsonFields initializes the field's name.
   *
   * @param name String of the room field's name.
   */
  RoomJsonFields(String name) {
    this.name = name;
  }

  /**
   * The getter for the room field's name.
   *
   * @return String fo the room field's name.
   */
  String getValue() {
    return this.name;
  }
}
