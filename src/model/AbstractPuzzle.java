package model;

/**
 * The AbstractPuzzle is an abstract class that represents enemies in an adventure
 * game that need to be defeated to stop their effect on a Room. AbstractEnemies
 * extends AbstractElement and implements Puzzle, Picturable, Scorable and Targeter
 * interfaces. AbstractPuzzle inherits name and description from AbstractElement and
 * has a score, active status, affects target status, target affects player status,
 * solution text, solution item text, effects text, damage amount and picture file path.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public abstract class AbstractPuzzle extends AbstractElement
        implements Puzzle, Picturable, Scorable, Targeter {

  // attributes
  private double score;
  private boolean active;
  private boolean affectsTarget;
  private final String target;
  private boolean affectsPlayer;
  private final String solutionText;
  private final String solutionItem;
  private final String effects;
  private double damage;
  private final String picture;

  /**
   * The AbstractPuzzle constructor initializes its attributes and sets either solutionText
   * or solutionItem to a String, but not both. Whichever attribute is set is the solution
   * to the AbstractPuzzle.
   *
   * @param name          String of the AbstractPuzzle's name.
   * @param description   String of the AbstractPuzzle's description.
   * @param score         double of the AbstractPuzzle's score.
   * @param active        boolean indicating the active status of the AbstractPuzzle.
   * @param affectsTarget boolean indicating if the AbstractPuzzle can affect its target.
   * @param target        String of AbstractPuzzle's target.
   * @param affectsPlayer boolean indicating if the AbstractPuzzle can affect the player.
   * @param solutionText  String of the AbstractPuzzle's solution if it is a text-based solution.
   * @param solutionItem  String of the AbstractPuzzle's solution if it is an item object.
   * @param effects       String of the AbstractPuzzle's effects.
   * @param damage        double of the damage an AbstractPuzzle can inflict.
   * @param picture       String of the path to an AbstractPuzzle's picture.
   */
  public AbstractPuzzle(String name, String description, double score, boolean active,
                        boolean affectsTarget, String target, boolean affectsPlayer,
                        String solutionText, String solutionItem, String effects, double damage,
                        String picture) throws IllegalArgumentException {
    super(name, description);

    //both target and effects must be non-null and non-empty
    if (checkIfInvalid(target) || checkIfInvalid(effects)) {
      throw new IllegalArgumentException("target and effects cannot be null/empty!");
    }

    //either solutionText or solutionItem must be non-null and non-empty.
    if (checkIfInvalid(solutionText) && checkIfInvalid(solutionItem)) {
      throw new IllegalArgumentException("solutionText and solutionItem "
              + "cannot both be null/empty!");
    }

    //damage must be less than or equal 0.0
    if (damage > 0.0) {
      throw new IllegalArgumentException("damage cannot be positive!");
    }

    if (score < 0.0) {
      throw new IllegalArgumentException("score cannot be negative!");
    }

    this.score = score;
    this.active = active;

    if (affectsTarget && target == null) {
      throw new IllegalArgumentException("affects target but no target specified!");
    }

    this.affectsTarget = affectsTarget;
    this.target = target;
    this.affectsPlayer = affectsPlayer;

    if (checkIfInvalid(solutionText) && checkIfInvalid(solutionItem)) {
      throw new IllegalArgumentException("solutionText and solutionItem cannot"
              + " both be null/empty!");
    }

    this.solutionText = solutionText;
    this.solutionItem = solutionItem;
    this.effects = effects;
    this.damage = damage;
    this.picture = picture;
  }

  @Override
  public boolean solve(String answer) {
    if (this.active && this.solutionText != null && this.solutionText.equalsIgnoreCase(answer)) {
      this.active = false;
      return true;
    }
    return false;
  }

  @Override
  public boolean solve(Item item) {
    if (this.active && this.solutionItem != null && item.isActive()
            && this.solutionItem.equalsIgnoreCase(item.getName())) {
      this.active = false;
      return true;
    }
    return false;
  }

  /**
   * The getPuzzleDamage() method is the getter for the AbstractPuzzle's damage amount.
   *
   * @return double of amount of damage.
   */
  protected double getPuzzleDamage() {
    return this.damage;
  }

  @Override
  public String getPicturePath() {
    return this.picture;
  }

  @Override
  public boolean affectsPlayer() {
    return this.affectsPlayer;
  }

  @Override
  public double getScore() {
    return this.score;
  }

  @Override
  public String getTarget() {
    return this.target;
  }

  @Override
  public boolean affectsTarget() {
    return this.affectsTarget;
  }

  @Override
  public String getEffect() {
    return this.effects;
  }

  @Override
  public boolean isActive() {
    return this.active;
  }

  @Override
  public String getSolutionText() {
    return this.solutionText;
  }

  @Override
  public String getSolutionItem() {
    return this.solutionItem;
  }
}
