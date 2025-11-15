package model;

/**
 * Service class the returns the use of an item and its whether its use was successful or not.
 */
public class UseSuccessful {
  private final String use;
  private final boolean useSuccessful;

  /**
   * Initializes use and useSuccessful based on parameters.
   * @param use A String describing the use of an Item.
   * @param useSuccessful a boolean describing whether an Item's use was successful.
   */
  public UseSuccessful(String use,  boolean useSuccessful) {
    this.use = use;
    this.useSuccessful = useSuccessful;
  }

  /**
   * Returns the use of an Item.
   * @return A String describing the use of an Item.
   */
  public String getUse() {
    return this.use;
  }

  /**
   * Returns whether an Item's use was successful.
   * @return a boolean describing whether an Item's use was successful.
   */
  public boolean getUseSuccessful() {
    return this.useSuccessful;
  }
}
