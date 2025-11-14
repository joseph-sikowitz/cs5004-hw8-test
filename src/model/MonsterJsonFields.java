package model;

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

  MonsterJsonFields(String value) {
    this.value = value;
  }

  String getValue() {
    return this.value;
  }
}
