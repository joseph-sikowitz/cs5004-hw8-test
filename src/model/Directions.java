package model;

/**
 * The Directions enum holds the four cardinal directions. Rooms have these four
 * directions for movement to other rooms and players can move in these directions.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public enum Directions {
  NORTH,
  SOUTH,
  EAST,
  WEST;

  /**
   * Returns the direction 180 degrees from the current direction.
   * @return  a Directions type enum.
   */
  public Directions getOppositeDirection() {
    return switch (this) {
      case NORTH -> SOUTH;
      case SOUTH -> NORTH;
      case EAST -> WEST;
      case WEST -> EAST;
    };
  }
}
