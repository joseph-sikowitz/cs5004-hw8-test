package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class TakeListener implements ActionListener {

  private final List<String> items;
  private String result;

  public TakeListener(List<String> items) {
    this.items = items;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.items != null) {
      String[] strList = this.items.toArray(new String[0]);
      JList<String> list = new JList<>();
      list.setListData(strList);
      JScrollPane scrollPane = new JScrollPane(list);
      JOptionPane.showMessageDialog(null, scrollPane, "Take", JOptionPane.PLAIN_MESSAGE);
      this.result = "take " + list.getSelectedValue();
    }
  }

  public String getResult() {
    return this.result;
  }

  public void removeResult() {
    this.result = null;
  }

}
