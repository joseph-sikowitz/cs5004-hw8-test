package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.*;

public class ExamineListener implements ActionListener {

  private List<String> items;
  private List<String> fixtures;
  private List<String> combined;

  public ExamineListener(List<String> items, List<String> fixtures) {
    this.items = items;
    this.fixtures = fixtures;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.items != null && this.fixtures != null) {
      combined = Stream.concat(this.items.stream(), this.fixtures.stream()).toList();
    } else if (this.items != null) {
      combined = this.items;
    } else if (this.fixtures != null) {
      combined = this.fixtures;
    }

    if (!combined.isEmpty()) {
      String[] strList = this.combined.toArray(new String[0]);
      JList<String> list = new JList<>();
      list.setListData(strList);
      JScrollPane scrollPane = new JScrollPane(list);
      JOptionPane.showMessageDialog(null, scrollPane, "Examine", JOptionPane.PLAIN_MESSAGE);
    }
  }
}
