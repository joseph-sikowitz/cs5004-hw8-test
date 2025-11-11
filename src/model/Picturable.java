package model;

/**
 * The Picturable interface defines types that have a picture associated with them.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public interface Picturable {

  /**
   * The getPicturePath() method gets the filepath of a Picturable's picture.
   *
   * @return String of Picturable's picture filepath.
   */
  String getPicturePath();
}
