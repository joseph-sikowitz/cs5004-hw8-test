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
  private double score;
  private double weight;
  private String picture;
  private int maxUses;
  private int usesRemaining;
  private String useDescription;
  private boolean active;


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
    this.active = usesRemaining > 0;

    if (weight < 0) {
      throw new IllegalArgumentException("Weight cannot be negative");
    }
    this.weight = weight;
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
  public void addUse() {
    this.usesRemaining--;

    if (this.usesRemaining <= 0) {
      this.active = false;
    }
  }

  @Override
  public String use() {
    // TODO: probably need to do some other stuff here
    return this.useDescription;
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
    return this.active;
  }
}
