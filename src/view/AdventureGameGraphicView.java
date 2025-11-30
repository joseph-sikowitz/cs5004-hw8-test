package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.GameGraphicInputOutputProcessor;

public class AdventureGameGraphicView extends JFrame
        implements IAdventureGameView<String, GameGraphicInputOutputProcessor> {

  private String userInput;

  private static final String CAPTION = "Adventure Game";

  public AdventureGameGraphicView() {
    super(CAPTION);

    this.setSize(1000, 750);
    this.setLocation(50, 50);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new GridLayout(0, 2));
    this.setJMenuBar(this.buildMenu());

    JPanel leftPanel = new JPanel(new GridLayout(0, 1));
    this.add(leftPanel);

    JPanel viewPanel = new JPanel();
    JLabel viewLabel = new JLabel("View ");
    viewPanel.add(viewLabel);
    JLabel newImage = new JLabel(new ImageIcon("resources/images/game_engine.png"));
    viewPanel.add(newImage);
    leftPanel.add(viewPanel);

    JPanel descriptionPanel = new JPanel(new GridLayout(0, 1));
    JLabel descriptionLabel = new JLabel("Description");
    descriptionPanel.add(descriptionLabel);
    JLabel descriptionText = new JLabel("description text goes here");
    JPanel descriptionDisplayBox = new JPanel();
    descriptionDisplayBox.add(descriptionText);
    descriptionPanel.add(descriptionDisplayBox);
    leftPanel.add(descriptionPanel);

    JPanel rightPanel = new JPanel(new GridLayout(0, 1));
    this.add(rightPanel);

    JPanel navigationPanel = new JPanel(new GridLayout(0, 1));
    JLabel navigationLabel = new JLabel("Navigation");
    navigationPanel.add(navigationLabel);
    JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
    JButton northButton = new JButton(new ImageIcon("resources/images/north.png"));
    buttonPanel.add(northButton);
    JPanel eastWestPanel = new JPanel(new GridLayout(1, 2));
    JButton eastButton = new JButton(new ImageIcon("resources/images/west.png"));
    JButton westButton = new JButton(new ImageIcon("resources/images/east.png"));
    eastWestPanel.add(eastButton);
    eastWestPanel.add(westButton);
    buttonPanel.add(eastWestPanel);
    JButton southButton = new JButton(new ImageIcon("resources/images/south.png"));
    buttonPanel.add(southButton);
    navigationPanel.add(buttonPanel);
    rightPanel.add(navigationPanel);

    JPanel actionsPanel = new JPanel(new GridLayout(2, 1));
    JLabel actionsLabel = new JLabel("Actions");
    actionsPanel.add(actionsLabel);
    JPanel buttonsPanel = new JPanel(new GridLayout(0, 3));
    JButton takeButton = new JButton("Take");
    JButton examineButton = new JButton("Examine");
    JButton answerButton = new JButton("Answer");
    buttonsPanel.add(takeButton);
    buttonsPanel.add(examineButton);
    buttonsPanel.add(answerButton);
    actionsPanel.add(buttonsPanel);
    rightPanel.add(actionsPanel);

    JPanel inventoryPanel = new JPanel(new GridLayout(0, 1));
    JLabel inventoryLabel = new JLabel("Inventory");
    inventoryPanel.add(inventoryLabel);
    JPanel inventoryDisplayBox = new JPanel();
    JLabel inventoryDisplayLabel = new JLabel("Inventory Display goes here");
    inventoryDisplayBox.add(inventoryDisplayLabel);
    inventoryPanel.add(inventoryDisplayBox);
    JPanel inventoryButtonPanel = new JPanel(new GridLayout(0, 3));
    JButton inspectButton = new JButton("Inspect");
    JButton useButton = new JButton("Use");
    JButton dropButton = new JButton("Drop");
    inventoryButtonPanel.add(inspectButton);
    inventoryButtonPanel.add(useButton);
    inventoryButtonPanel.add(dropButton);
    inventoryPanel.add(inventoryButtonPanel);
    rightPanel.add(inventoryPanel);

    JPanel statusPanel = new JPanel(new GridLayout(0, 1));
    JLabel statusLabel = new JLabel("Status");
    statusPanel.add(statusLabel);
    JPanel statusDisplayBox = new JPanel();
    JLabel statusDisplayLabel = new JLabel("Player Status Display goes here");
    statusDisplayBox.add(statusDisplayLabel);
    statusPanel.add(statusDisplayBox);
    rightPanel.add(statusPanel);

    this.display();
  }

  private JMenuBar buildMenu() {
    JMenu menu = new JMenu("File");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem restore = new JMenuItem("Restore");
    JMenuItem about = new JMenuItem("About...");
    JMenuItem exit = new JMenuItem("Exit");
    menu.add(save);
    menu.add(restore);
    menu.add(about);
    menu.add(exit);
    about.addActionListener(
            event -> JOptionPane.showMessageDialog(null,
                    "An adventure from Vasilios & Joe"));
    exit.addActionListener(
            event -> System.exit(0));
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(menu);
    return menuBar;
  }

  private void display() {
    this.setVisible(true);
  }

  @Override
  public void setEventHandler(GameGraphicInputOutputProcessor ioProcessor) {

  }

  @Override
  public String getCommand() {
    return this.userInput;
  }

  @Override
  public void messageToPlayer(String data) throws IOException {
    JOptionPane.showMessageDialog(this, data);
  }

  @Override
  public void updatePlayerStats(String data) throws IOException {

  }

  @Override
  public void updateRoom(String data) throws IOException {

  }

  @Override
  public void updateExaminer(String data) throws IOException {

  }

  @Override
  public void updateInventory(String data) throws IOException {

  }

  @Override
  public void updatePlayerAffector(String data) throws IOException {

  }

  @Override
  public void promptPlayer(String data) throws IOException {
    this.userInput = JOptionPane.showInputDialog(this, data);
  }
}
