package model;

/**
 * The ConcreteMonster class represents a Monster in an adventure game. Monsters extend
 * the AbstractEnemy class and implement the Monster interface. ConcreteMonsters have
 * a canAttack field indicating if they can attack a player and an attackDescription
 * describing their attack.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class ConcreteMonster extends AbstractEnemy implements Monster {

  // attributes
  private boolean canAttack;
  private final String attackDescription;

  /**
   * The ConcreteMonster constructor initializes most of its attributes using its superclass,
   * but initializes canAttack and attackDescription here.
   *
   * @param name String of Monster's name.
   * @param description String of Monster's description.
   * @param active boolean indicating if the Monster is active in the game.
   * @param affectsTarget boolean indicating if the Monster can affect its target.
   * @param target String of the Monster's target to affect.
   * @param affectsPlayer boolean indicating if the Monster affects the player.
   * @param solutionText String of the solution to the Monster if a typed solution.
   * @param solutionItem String of the name of the Item if defeated by an item.
   * @param score double of the score from beating the Monster.
   * @param effects String a description of the Monster's effects.
   * @param damage double of how much damage a Monster does to a player's health.
   * @param picture String of the file name path of the Monster's picture.
   * @param canAttack boolean indicating if the Monster can attack the player.
   * @param attackDescription String of the description of the Monster's attack.
   */
  public ConcreteMonster(String name, String description, boolean active, boolean affectsTarget,
                        String target, boolean affectsPlayer, String solutionText,
                         String solutionItem, double score, String effects, double damage,
                         String picture, boolean canAttack, String attackDescription) {
    super(name, description, score, active, affectsTarget, target, affectsPlayer, solutionText,
            solutionItem, effects, damage, picture);

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
