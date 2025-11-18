package model;

/**
 * The ConcretePuzzle class represents a Puzzle in an adventure game. ConcretePuzzles
 * can be solved and affect targets and players. ConcretePuzzle extends AbstractPuzzle
 * and implements the Puzzle interface. ConcretePuzzles have no local attributes or
 * methods other than the constructor.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public class ConcretePuzzle extends AbstractPuzzle implements Puzzle {

  /**
   * The ConcretePuzzle constructor initializes all of its inherited attributes using the
   * inherited constructor of its superclass.
   *
   * @param name String of the puzzle's name.
   * @param description String of the puzzle's description.
   * @param active boolean indicating if the puzzle's active status.
   * @param affectsTarget boolean indicating if the puzzle affects its target.
   * @param target String of the puzzle's target.
   * @param affectsPlayer boolean indicating if the puzzle affects the player.
   * @param solutionText String of the puzzle's text-based solution.
   * @param solutionItem String of the puzzle's item-based solution.
   * @param score double of the puzzle's score upon completion.
   * @param effects String of the effects of the puzzle.
   * @param damage double of the damage the puzzle does to the player's health.
   * @param picture String of the puzzle's file name path.
   */
  public ConcretePuzzle(String name, String description, boolean active, boolean affectsTarget,
                        String target, boolean affectsPlayer, String solutionText,
                        String solutionItem, double score, String effects, double damage,
                        String picture) {
    super(name, description, score, active, affectsTarget, target, affectsPlayer, solutionText,
            solutionItem, effects, damage, picture);
  }

}
