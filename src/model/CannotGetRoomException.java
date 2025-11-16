package model;

/**
 * An IllegalArgumentException for attempting to get a Room from a blocked or non-existent passage.
 */
public class CannotGetRoomException extends IllegalArgumentException {
  private RoomStatus roomExceptionStatus;

  /**
   * Constructor Initializes the Exception.
   * @param roomExceptionStatus a RoomStatus enum type representing the
   *     specific error with the getting the Room.
   */
  public CannotGetRoomException(RoomStatus roomExceptionStatus) {
    super(roomExceptionStatus.getStatus());
    this.roomExceptionStatus = roomExceptionStatus;
  }

  public RoomStatus getRoomExceptionStatus() {
    return roomExceptionStatus;
  }
}
