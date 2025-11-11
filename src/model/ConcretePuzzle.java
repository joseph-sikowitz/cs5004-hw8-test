package model;

/**
 * The ConcretePuzzle class represents a Puzzle in an adventure game. ConcretePuzzles
 * can be solved and affect targets and players. ConcretePuzzle extends AbstractEnemy
 * and implements the Puzzle interface. ConcretePuzzles have no local attributes or
 * methods other than the constructor.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class ConcretePuzzle extends AbstractEnemy implements Puzzle {

  public ConcretePuzzle(String name, String description, boolean active, boolean affectsTarget,
                        String target, boolean affectsPlayer, String solution,
                        double score, String effects, double damage, String picture) {
    super(name, description, score, active, affectsTarget, target, affectsPlayer, solution,
            effects, damage, picture);
  }
}
