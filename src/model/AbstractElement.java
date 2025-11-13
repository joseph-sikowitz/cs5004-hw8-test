package model;

/**
 * The AbstractElement class is an abstract representation of a game Element,
 * and it implements the Element interface. AbstractElements have a name and
 * description.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public abstract class AbstractElement implements Element {

  // attributes
  private String name;
  private final String description;

  /**
   * The AbstractElement constructor initializes the class attributes.
   *
   * @param name String of AbstractElement's name.
   * @param description String of AbstractElement's description.
   */
  public AbstractElement(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * The getter for the AbstractElement's name.
   *
   * @return String of the AbstractElement's name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * The getter for the AbstractElement's description.
   *
   * @return String of the AbstractElement's description.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * The setter for the AbstractElement's name.
   *
   * @param name String of the AbstractElement's name.
   */
  protected void setName(String name) {
    this.name = name;
  }

  /**
   * Checks whether a String is null or empty.
   * @param str a String to check if it is null or empty.
   * @return true if the String is null or empty, otherwise false.
   */
  protected boolean validateString(String str) {
    return str == null || str.isEmpty();
  }

}
