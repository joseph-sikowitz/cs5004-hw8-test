package model;

/**
 * An Enum type containing error message states for RoomService.
 */
public enum RoomStatus {
  BLOCKED("No passage to room!"),
  NO_PASSAGE("Wall or no Passage!");

  final String status;

  RoomStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
