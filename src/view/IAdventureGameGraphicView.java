package view;

import java.awt.event.ActionListener;

/**
 * Represents a Graphics view for the adventure game that
 * @param <T>
 */
public interface IAdventureGameGraphicView<T> extends IAdventureGameView<T> {


  /**
   * Sets the event handler for the Graphics view.
   * @param actionListener
   */
  void setEventHandler(ActionListener actionListener);

}
