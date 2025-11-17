package model;

/**
 * An Enum type containing error message states for RoomService.
 *
 * @author Joe Sikowitz
 * @author Vasilios Nicholas
 */
public enum RoomStatus {
  BLOCKED("Passage to Room is blocked!"),
  NO_PASSAGE("Wall or no Passage in this direction!"),
  OPEN("The passage between rooms is open!");

  final String status;

  /**
   * The constructor for the RoomStatus enum initializes the status attribute.
   *
   * @param status String of the RoomStatus' status message.
   */
  RoomStatus(String status) {
    this.status = status;
  }

  /**
   * The getter for the RoomStatus enum's status.
   *
   * @return String of the status message.
   */
  public String getStatus() {
    return status;
  }
}
