package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import controller.GameGraphicInputOutputProcessor;

public class AdventureGameGraphicView extends JFrame
        implements IAdventureGameView<List<String>, GameGraphicInputOutputProcessor> {

  private String userInput;
  private final JButton northButton;
  private final JButton southButton;
  private final JButton eastButton;
  private final JButton westButton;
  private final JButton answerButton;
  private JLabel viewImage;
  private JTextArea descriptionText;
  private JTextArea statusText;
  private JList<String> inventoryText;

  private static final String CAPTION = "Adventure Game";
  private static final String SPLASH_IMAGE = "resources/images/game_engine.png";
  private static final String NAMED = "Thou shalt be named ";

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
    TitledBorder viewBorder = BorderFactory.createTitledBorder("View");
    viewPanel.setBorder(viewBorder);
    this.viewImage = new JLabel(new ImageIcon(SPLASH_IMAGE));
    this.viewImage.setPreferredSize(new Dimension(450, 300));
    viewPanel.add(this.viewImage);
    leftPanel.add(viewPanel);

    JPanel descriptionPanel = new JPanel(new GridLayout(0, 1));
    TitledBorder descriptionBorder = BorderFactory.createTitledBorder("Description");
    descriptionPanel.setBorder(descriptionBorder);
    this.descriptionText = new JTextArea(" ");
    this.descriptionText.setLineWrap(true);
    this.descriptionText.setPreferredSize(new Dimension(450, 300));
    JPanel descriptionDisplayBox = new JPanel();
    descriptionDisplayBox.add(this.descriptionText, BorderLayout.SOUTH);
    descriptionPanel.add(descriptionDisplayBox);
    leftPanel.add(descriptionPanel);

    JPanel rightPanel = new JPanel(new GridLayout(0, 1));
    this.add(rightPanel);

    JPanel navigationPanel = new JPanel(new GridLayout(0, 1));
    TitledBorder navBorder = BorderFactory.createTitledBorder("Navigation");
    navigationPanel.setBorder(navBorder);
    JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
    this.northButton = new JButton(new ImageIcon("resources/images/north.png"));
    this.northButton.setActionCommand("north");
    buttonPanel.add(northButton);

    JPanel eastWestPanel = new JPanel(new GridLayout(1, 2));
    this.westButton = new JButton(new ImageIcon("resources/images/west.png"));
    this.westButton.setActionCommand("west");
    eastWestPanel.add(this.westButton);

    this.eastButton = new JButton(new ImageIcon("resources/images/east.png"));
    this.eastButton.setActionCommand("east");
    eastWestPanel.add(this.eastButton);
    buttonPanel.add(eastWestPanel);

    this.southButton = new JButton(new ImageIcon("resources/images/south.png"));
    this.southButton.setActionCommand("south");
    buttonPanel.add(this.southButton);
    navigationPanel.add(buttonPanel);
    rightPanel.add(navigationPanel);

    JPanel actionsPanel = new JPanel(new GridLayout(2, 1));
    TitledBorder actionsBorder = BorderFactory.createTitledBorder("Actions");
    actionsPanel.setBorder(actionsBorder);
    JPanel buttonsPanel = new JPanel(new GridLayout(0, 3));
    JButton takeButton = new JButton("Take");
    JButton examineButton = new JButton("Examine");
    this.answerButton = new JButton("Answer");
    this.answerButton.setActionCommand("answer");
    buttonsPanel.add(takeButton);
    buttonsPanel.add(examineButton);
    buttonsPanel.add(this.answerButton);
    actionsPanel.add(buttonsPanel);
    rightPanel.add(actionsPanel);

    JPanel inventoryPanel = new JPanel(new GridLayout(0, 1));
    TitledBorder inventoryBorder = BorderFactory.createTitledBorder("Inventory");
    inventoryPanel.setBorder(inventoryBorder);
    String[] inventoryItems = {};
    this.inventoryText = new JList<>(inventoryItems);
    inventoryPanel.add(this.inventoryText);
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
    TitledBorder statusBorder = BorderFactory.createTitledBorder("Status");
    statusPanel.setBorder(statusBorder);
    this.statusText = new JTextArea(" ");
    this.statusText.setPreferredSize(new Dimension(450, 25));
    statusPanel.add(this.statusText);
    rightPanel.add(statusPanel);
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
    this.setActionListener(ioProcessor);
  }

  private void setActionListener(ActionListener actionListener) {
    this.northButton.addActionListener(actionListener);
    this.eastButton.addActionListener(actionListener);
    this.westButton.addActionListener(actionListener);
    this.southButton.addActionListener(actionListener);
  }

  @Override
  public String getCommand() {
    return this.userInput;
  }

  @Override
  public void messageToPlayer(List<String> data) throws IOException {
    JOptionPane.showMessageDialog(this, data.getFirst());
  }

  @Override
  public void updatePlayerStats(List<String> data) throws IOException {
    this.statusText.setText(data.getFirst());
  }

  @Override
  public void updateRoom(List<String> data) throws IOException {
    this.viewImage.setIcon(new ImageIcon(data.getLast()));
    // update description text here
    String roomDescription = data.get(1);
    this.descriptionText.setText(roomDescription);
  }


  @Override
  public void updateInventory(List<String> data) throws IOException {
    String[] inventoryItems = data.toArray(new String[0]);
    this.inventoryText.setListData(inventoryItems);
  }

  @Override
  public void updatePlayerAffector(List<String> data) throws IOException {
    //this.descriptionText.setText(data.getFirst());
  }

  @Override
  public void promptPlayer(String data) throws IOException {
    this.userInput = JOptionPane.showInputDialog(this, data);
    this.display();
  }

  @Override
  public void updateFixtures(List<String> data) throws IOException {

  }

  @Override
  public void updateItems(List<String> data) throws IOException {

  }

  @Override
  public void updateTitle(String data) throws IOException {

  }
}
