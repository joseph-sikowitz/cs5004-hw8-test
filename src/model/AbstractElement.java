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
  public AbstractElement(String name, String description) throws IllegalArgumentException {
    if (checkIfInvalid(name) || checkIfInvalid(description)) {
      throw new IllegalArgumentException("Name and Description must be non-null,"
              + " non-empty Strings");
    }
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
   * Checks whether a String is null or empty.
   * @param str a String to check if it is null or empty.
   * @return true if the String is null or empty, otherwise false.
   */
  protected static boolean checkIfInvalid(String str) {
    return str == null || str.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractElement that = (AbstractElement) o;
    return this.name.equalsIgnoreCase(that.name);
  }

  @Override
  public int hashCode() {
    return this.name.toLowerCase().hashCode();
  }

}
