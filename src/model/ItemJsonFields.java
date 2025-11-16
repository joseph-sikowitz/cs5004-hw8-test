package model;

public enum ItemJsonFields {
  NAME("name"),
  WEIGHT("weight"),
  MAX_USES("max_uses"),
  USES_REMAINING("uses_remaining"),
  VALUE("value"),
  WHEN_USED("when_used"),
  DESCRIPTION("description"),
  PICTURE("picture");

  private final String name;

  ItemJsonFields(String name) {
    this.name = name;
  }

  String getValue() {
    return this.name;
  }
}
