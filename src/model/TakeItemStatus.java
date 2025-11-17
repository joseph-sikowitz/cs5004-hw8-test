package model;

/**
 * Discrete outcomes for Player picking up an Item.
 */
public enum TakeItemStatus {
  ITEM_ADDED(" added to your inventory"),
  ITEM_NOT_ADDED_OVER_CAPACITY(" was not added to your inventory "
          + "as you cannot carry anymore weight!"),
  ITEM_NOT_FOUND(" does not exist");
  private final String status;

  /**
   * Constructor for TakeItemStatus.
   * @param status the String status of the enum.
   */
  TakeItemStatus(String status) {
    this.status = status;
  }

  /**
   * Returns the String status of the enum.
   * @return String status of the enum.
   */
  public String getStatus() {
    return this.status;
  }
}
