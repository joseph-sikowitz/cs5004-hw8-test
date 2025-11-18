package model;

/**
 * The ConcreteItem class represents an item in an adventure game that can be
 * used to defeat Monsters or Puzzles. ConcreteItem extends the AbstractElement
 * class and implements Item. ConcreteItems have a name and description inherited
 * from AbstractElement as well as a score, weight, picture, maximum uses, number
 * of uses remaining, a use description, and an active status.
 */
public class ConcreteItem extends AbstractElement implements Item {

  // attributes
  private final double score;
  private final double weight;
  private final String picture;
  private final int maxUses;
  private int usesRemaining;
  private final String useDescription;
  public final String cannotUse;

  // constants
  private static final double MINIMUM_WEIGHT = 0;
  private static final double INACTIVE_USE_AMOUNT = 0;

  /**
   * The ConcreteItem constructor initializes its attributes and sets its active
   * status based on the number of uses remaining.
   *
   * @param name String of the item's name.
   * @param description String of the item's description.
   * @param score double of the item's score.
   * @param weight double of the item's weight.
   * @param picture String of the item's picture file path.
   * @param maxUses int of the item's maximum number of uses.
   * @param usesRemaining int of the item's remaining uses.
   * @param useDescription String of the item's use description.
   */
  public ConcreteItem(String name, String description, double score, double weight, String picture,
                      int maxUses, int usesRemaining, String useDescription)
          throws IllegalArgumentException {
    super(name, description);

    this.score = score;
    this.picture = picture;
    this.maxUses = maxUses;
    this.usesRemaining = usesRemaining;
    this.useDescription = useDescription;

    if (weight < MINIMUM_WEIGHT) {
      throw new IllegalArgumentException("Weight cannot be negative");
    }
    this.weight = weight;

    this.cannotUse = this.getName() + " can no longer be used!\n";
  }

  @Override
  public int getMaxUses() {
    return this.maxUses;
  }

  @Override
  public int getUsesRemaining() {
    return this.usesRemaining;
  }

  @Override
  public String use() {
    if (isActive())
      return this.useDescription;
    return this.cannotUse;
  }

  @Override
  public UseSuccessful use(Puzzle enemy)  {
    //get use of String before decrementing usesRemaining
    String use = this.use();

    if ((usesRemaining < 0 || enemy == null || !enemy.isActive())) {
      this.usesRemaining--;
      return new UseSuccessful(use, false);
    }
    boolean enemySolved = enemy.solve(this);
    this.usesRemaining--;
    return new UseSuccessful(enemySolved ? (use + "\n" + enemy.getName()
            +  " was deactivated by " + this.getName() + "!") : use + "\n"
            + this.getName() + " had no effect on " + enemy.getName() + "!\n", enemySolved);
  }

  @Override
  public String getPicturePath() {
    return this.picture;
  }

  @Override
  public double getScore() {
    return this.score;
  }

  @Override
  public double getWeight() {
    return this.weight;
  }

  @Override
  public boolean isActive() {
    return this.usesRemaining > INACTIVE_USE_AMOUNT;
  }

 @Override
  public String getUseDescription() {
    return this.useDescription;
  }
}
