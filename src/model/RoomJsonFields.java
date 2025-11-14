package model;

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

  RoomJsonFields(String name) {
    this.name = name;
  }

  String getValue() {
    return this.name;
  }
}
