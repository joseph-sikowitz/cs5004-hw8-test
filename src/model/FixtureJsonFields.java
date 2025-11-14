package model;

public enum FixtureJsonFields {
  NAME("name"),
  WEIGHT("weight"),
  PUZZLE("puzzle"),
  STATES("states"),
  DESCRIPTION("description"),
  PICTURE("picture");


  private final String value;

  FixtureJsonFields(String value) {
    this.value = value;
  }

  String getValue() {
    return this.value;
  }
}
