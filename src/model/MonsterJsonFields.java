package model;

/**
 * The MonsterJsonFields enum represents the fields that will appear in a Monster entry
 * in an incoming JSON file. Each value has a field value.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public enum MonsterJsonFields {
  NAME("name"),
  ACTIVE("active"),
  AFFECTS_TARGET("affects_target"),
  AFFECTS_PLAYER("affects_player"),
  SOLUTION("solution"),
  VALUE("value"),
  DESCRIPTION("description"),
  EFFECTS("effects"),
  DAMAGE("damage"),
  TARGET("target"),
  CAN_ATTACK("can_attack"),
  ATTACK("attack"),
  PICTURE("picture");


  private final String value;

  /**
   * The constructor initializes each enum's field value.
   *
   * @param value String of the field value.
   */
  MonsterJsonFields(String value) {
    this.value = value;
  }

  /**
   * The getter for the enum's field value.
   *
   * @return String of the field value.
   */
  String getValue() {
    return this.value;
  }
}
