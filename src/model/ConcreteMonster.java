package model;

public class ConcreteMonster extends AbstractEnemy implements Monster {

  // attributes
  private boolean canAttack;
  private final String attackDescription;

  public ConcreteMonster(String name, String description, boolean active, boolean affectsTarget,
                        String target, boolean affectsPlayer, String solution,
                        double score, String effects, double damage, String picture,
                         boolean canAttack, String attackDescription) {
    super(name, description, score, active, affectsTarget, target, affectsPlayer, solution,
            effects, damage, picture);

    this.canAttack = canAttack;
    this.attackDescription = attackDescription;
  }

  @Override
  public double getDamage() {
    return super.getEnemyDamage();
  }

  @Override
  public boolean getCanAttack() {
    return this.canAttack;
  }

  @Override
  public void flipCanAttack() {
    this.canAttack = !this.canAttack;
  }

  @Override
  public String getAttackDescription() {
    return this.attackDescription;
  }
}
