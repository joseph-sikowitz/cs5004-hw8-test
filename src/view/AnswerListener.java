package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AnswerListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    JOptionPane.showInputDialog("Enter answer:");
  }
}
