package view;

import java.awt.event.ActionListener;

public interface IAdventureGameGraphicView<T> extends IAdventureGameView<T> {


  void setEventHandler(ActionListener ioProcessor);

}
