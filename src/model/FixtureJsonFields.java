package model;

/**
 * The FixtureJsonFields enum represents the fields of a fixture in a JSON
 * input file. Each field has a field value.
 */
public enum FixtureJsonFields {
  NAME("name"),
  WEIGHT("weight"),
  PUZZLE("puzzle"),
  STATES("states"),
  DESCRIPTION("description"),
  PICTURE("picture");


  private final String value;

  /**
   * The constructor initializes the enum's field value.
   *
   * @param value String of the field value.
   */
  FixtureJsonFields(String value) {
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
