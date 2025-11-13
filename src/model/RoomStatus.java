package model;

/**
 * An Enum type containing error message states for RoomService.
 */
public enum RoomStatus {
  BLOCKED("Passage to Room is blocked!"),
  NO_PASSAGE("Wall or no Passage to this Room from current Room!");

  final String status;

  RoomStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
