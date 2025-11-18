package model;

/**
 * The Fixture interface defines the Fixture type in an adventure game. A fixture
 * is an element in a Room that does not move. At this time, fixtures have no
 * required methods. The Fixture interface extends the Weightable and Picturable
 * interfaces.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Fixture extends Element, Weightable, Picturable, EnvironmentAffectedForPlayer {

  /**
   * Returns the states of the Fixture.
   * @return a String representing the states of the Fixture.
   */
  String getStates();

  /**
   * The getter for the fixture's Puzzle.
   *
   * @return Puzzle object associated with the fixture.
   */
  Puzzle getPuzzle();

  /**
   * Returns the Fixture's true description.
   * For use when saving Fixture into json.
   * @return A String representing the Fixture's true description.
   */
  String getTrueDescription();

}