package model;

/**
 * The ItemJsonFields enum represents the fields in an ingested JSON file. Each value
 * has an associated field name.
 */
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

  /**
   * The constructor initializes the field name.
   *
   * @param name String of the enum's field name.
   */
  ItemJsonFields(String name) {
    this.name = name;
  }

  /**
   * The getter for the enum's field name.
   *
   * @return String of the enum's field name.
   */
  String getValue() {
    return this.name;
  }
}
