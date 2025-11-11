package model;

/**
 * The PlayerAffector interface defines a type that can affect a player.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface PlayerAffector {

  /**
   * The affectsPlayer() method indicates if a PlayerAffector can affect a player.
   *
   * @return boolean indicating if the PlayerAffector can affect a player.
   */
  boolean affectsPlayer();
}
