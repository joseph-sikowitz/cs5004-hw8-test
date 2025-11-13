package model;

/**
 * The ConcreteFixture class represents a game fixture that cannot move in a room. It
 * extends AbstractElement and implements the Fixture, Picturable, and Weightable interfaces.
 * ConcreteFixtures have a name, description, weight, puzzle, states, and a path to a picture.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class ConcreteFixture extends AbstractElement implements Fixture {

  // attributes
  private double weight;
  private Puzzle puzzle;
  private String states;
  private String picture;

  /**
   * The constructor for the Concrete fixture initializes its name and description attributes
   * using the constructor inherited from AbstractElement. Its other attributes are
   * initialized here.
   *
   * @param name String of the fixture's name.
   * @param description String of the fixture's description.
   * @param weight double of the fixture's weight.
   * @param puzzle Puzzle object that the fixture holds.
   * @param states String of possible states of the fixture.
   * @param picture String of the path to the fixture's picture.
   */
  public ConcreteFixture(String name, String description, double weight, Puzzle puzzle,
                         String states,  String picture) throws IllegalArgumentException {
    super(name , description);

    if (weight < 200.0) {
      throw new IllegalArgumentException("Weight must be greater than 200");
    }

    this.weight = weight;
    this.puzzle = puzzle;
    this.states = states;
    this.picture = picture;
  }

  @Override
  public String getPicturePath() {
    return this.picture;
  }

  @Override
  public double getWeight() {
    return this.weight;
  }
}
