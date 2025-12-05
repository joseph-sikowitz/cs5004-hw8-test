package view;

import java.awt.event.ActionListener;

/**
 * Represents a Graphics IAdventureGameView that uses an Event loop.
 * @param <T> a class Generic representing the type of Data to receive from the Controller.
 */
public interface IAdventureGameGraphicView<T> extends IAdventureGameView<T> {


  /**
   * Sets the event handler for the Graphics view.
   * @param actionListener an ActionListener type that reacts to ActionEvents.
   */
  void setEventHandler(ActionListener actionListener);

}
