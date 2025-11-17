package model;

/**
 * The PuzzleJsonFields enum represents the fields that will appear in an incoming JSON
 * game file. Each value is mapped to a field name.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
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

  /**
   * The constructor for the PuzzleJsonFields enum initializes the value's field name.
   *
   * @param value String of the field name.
   */
  PuzzleJsonFields(String value) {
    this.value = value;
  }

  /**
   * The getter for the enum's field name.
   *
   * @return String of the field name.
   */
  String getValue() {
    return this.value;
  }
}
