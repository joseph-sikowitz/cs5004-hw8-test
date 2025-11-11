package model;

public class ConcreteItem extends AbstractElement implements Item {

  // attributes
  private double score;
  private double weight;
  private String picture;
  private int maxUses;
  private int usesRemaining;
  private String useDescription;
  private boolean active;


  public ConcreteItem(String name, String description, double score, double weight, String picture,
                      int maxUses, int usesRemaining, String useDescription) {
    super(name, description);

    this.score = score;
    this.weight = weight;
    this.picture = picture;
    this.maxUses = maxUses;
    this.usesRemaining = usesRemaining;
    this.useDescription = useDescription;
    this.active = usesRemaining > 0;
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

  @Override
  public void flipActive() {
    this.active = !this.active;
  }
}
