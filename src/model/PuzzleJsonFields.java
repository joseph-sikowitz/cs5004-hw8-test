package model;

public enum PuzzleJsonFields {
  NAME("name"),
  ACTIVE("active"),
  AFFECTS_TARGET("affects_target"),
  AFFECTS_PLAYER("affects_player"),
  SOLUTION("solution"),
  VALUE("value"),
  DESCRIPTION("description"),
  EFFECTS("effects"),
  TARGET("target"),
  PICTURE("picture");

  private final String value;

  PuzzleJsonFields(String value) {
    this.value = value;
  }

  String getValue() {
    return this.value;
  }
}
