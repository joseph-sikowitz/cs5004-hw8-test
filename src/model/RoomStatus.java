package model;

/**
 * An Enum type containing error message states for RoomService.
 */
public enum RoomStatus {
  BLOCKED("Passage to Room is blocked!"),
  NO_PASSAGE("Wall or no Passage in this direction!"),
  OPEN("The passage between rooms is open!");

  final String status;

  RoomStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
