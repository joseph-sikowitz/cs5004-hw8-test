package model;

/**
 * The AbstractEnemy is an abstract class that represents enemies in an adventure
 * game that need to be defeated to stop their effect on a Room. AbstractEnemies
 * extends AbstractElement and implements Effector, Picturable, PlayerAffector,
 * Scorable, and Targeter interfaces. AbstractEnemy inherits name and description
 * from AbstractElement and has a score, active status, affects target status, target
 * affects player status, solution text, solution item text, effects text, damage
 * amount and picture file path.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public abstract class AbstractEnemy extends AbstractElement
        implements Effector, Picturable, PlayerAffector, Scorable, Targeter {

  // attributes
  private double score;
  private boolean active;
  private boolean affectsTarget;
  private final String target;
  private boolean affectsPlayer;
  private String solutionText;
  private String solutionItem;
  private final String effects;
  private double damage;
  private final String picture;

  /**
   * The AbstractEnemy constructor initializes its attributes and sets either solutionText
   * or solutionItem to a String, but not both. Whichever attribute is set is the solution
   * to the AbstractEnemy.
   *
   * @param name          String of the AbstractEnemy's name.
   * @param description   String of the AbstractEnemy's description.
   * @param score         double of the AbstractEnemy's score.
   * @param active        boolean indicating the active status of the AbstractEnemy.
   * @param affectsTarget boolean indicating if the AbstractEnemy can affect its target.
   * @param target        String of AbstractEnemy's target.
   * @param affectsPlayer boolean indicating if the AbstractEnemy can affect the player.
   * @param solutionText  String of the AbstractEnemy's solution if it is a text-based solution.
   * @param solutionItem  String of the AbstractEnemy's solution if it is an item object.
   * @param effects       String of the AbstractEnemy's effects.
   * @param damage        double of the damage an AbstractEnemy can inflict.
   * @param picture       String of the path to an AbstractEnemy's picture.
   */
  public AbstractEnemy(String name, String description, double score, boolean active,
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
      throw new IllegalArgumentException("solutionText and solutionItem cannot both be null/empty!");
    }

    //damage must be less than or equal 0.0
    if (damage > 0.0) {
      throw new IllegalArgumentException("damage cannot be positive!");
    }

    this.score = score;
    this.active = active;
    this.affectsTarget = affectsTarget;
    this.target = target;
    this.affectsPlayer = affectsPlayer;
    this.solutionText = solutionText;
    this.solutionItem = solutionItem;
    this.effects = effects;
    this.damage = damage;
    this.picture = picture;
  }

  /**
   * The getEnemyDamage() method is the getter for the AbstractEnemy's damage amount.
   *
   * @return double of amount of damage.
   */
  protected double getEnemyDamage() {
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
  public void flipActive() {
    this.active = !this.active;
  }
}
