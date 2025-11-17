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
  private static final double WEIGHT_LOWER_BOUND = 200.0;
  // attributes
  private final double weight;
  private Puzzle puzzle;
  private String states;
  private final String picture;

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

    if (weight < WEIGHT_LOWER_BOUND) {
      throw new IllegalArgumentException("Weight must be greater than " + WEIGHT_LOWER_BOUND);
    }

    this.weight = weight;
    this.puzzle = puzzle;
    this.states = states;
    this.picture = picture;
  }

  @Override
  public String getDescription() {
    if (this.puzzle != null && this.puzzle.isActive())
      return puzzle.getEffect();
    return super.getDescription();
  }

  @Override
  public String getPicturePath() {
    return this.picture;
  }

  @Override
  public double getWeight() {
    return this.weight;
  }

  @Override
  public String getStates() {
    return this.states;
  }

  @Override
  public Puzzle getPuzzle() {
    return this.puzzle;
  }

  @Override
  public boolean affectorAffectsPlayer() {
    return this.puzzle != null && puzzle.isActive()
            && puzzle.affectsPlayer();
  }
}
