package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.GameGraphicInputOutputProcessor;

/**
 * The AdventureGameGraphicView class provides a GUI view for the adventure game.
 * It extends JFrame in order to provide a holding window for the rest of the GUI
 * functionality. It implements the IAdventureGameView interface in order to provide
 * the expected functionality for an adventure game's view.
 */
public class AdventureGameGraphicView extends JFrame
        implements IAdventureGameView<List<String>, GameGraphicInputOutputProcessor> {

  private final GameGraphicInputOutputProcessor ioProcessor;
  private String userInput;
  private String inventorySelection;

  private final JButton northButton;
  private final JButton southButton;
  private final JButton eastButton;
  private final JButton westButton;
  private final JButton answerButton;
  private final JButton takeButton;
  private final JButton examineButton;
  private JButton inspectButton;
  private JButton useButton;
  private JButton dropButton;

  private JLabel viewImage;
  private JTextArea descriptionText;
  private JTextArea statusText;
  private JList<String> inventoryText;
  private List<String> fixtures;
  private List<String> items;
  private JPanel leftPanel;

  private static final String DEFAULT_CAPTION = "Adventure Game";
  private static final String SPLASH_IMAGE = "resources/images/game_engine.png";


  /**
   * The constructor initializes all the GUI view's JFrame elements.
   *
   * @param ioProcessor GameGraphicInputOutputProcessor is the action listener for
   *                    events in the GUI.
   */
  public AdventureGameGraphicView(GameGraphicInputOutputProcessor ioProcessor) {
    super();

    this.ioProcessor = ioProcessor;

    this.setSize(1000, 750);
    this.setLocation(50, 50);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new GridLayout(0, 2));
    this.setJMenuBar(this.buildMenu());

    this.leftPanel = new JPanel(new GridLayout(0, 1));
    this.add(leftPanel);
    leftPanel.add(new ViewPanel("View"));

    /*
    JPanel viewPanel = new JPanel();
    TitledBorder viewBorder = BorderFactory.createTitledBorder("View");
    viewPanel.setBorder(viewBorder);
    this.viewImage = new JLabel(new ImageIcon(SPLASH_IMAGE));
    this.viewImage.setPreferredSize(new Dimension(450, 300));
    viewPanel.add(this.viewImage);
    leftPanel.add(viewPanel);
     */

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

    String take = "Take";
    this.takeButton = new JButton(take);
    this.takeButton.addActionListener(
            e -> new TakeDialog(
                    AdventureGameGraphicView.this, take).setVisible(true));

    String examine = "Examine";
    this.examineButton = new JButton(examine);
    this.examineButton.addActionListener(
            e -> new ExamineDialog(
                    AdventureGameGraphicView.this, examine).setVisible(true));

    String answer = "Answer";
    this.answerButton = new JButton(answer);
    this.answerButton.addActionListener(
            e -> new AnswerDialog(
                    AdventureGameGraphicView.this, answer).setVisible(true));

    buttonsPanel.add(this.takeButton);
    buttonsPanel.add(this.examineButton);
    buttonsPanel.add(this.answerButton);
    actionsPanel.add(buttonsPanel);
    rightPanel.add(actionsPanel);

    InventoryPanel inventoryPanel = new InventoryPanel();
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
    save.addActionListener(this.ioProcessor);
    JMenuItem restore = new JMenuItem("Restore");
    restore.addActionListener(this.ioProcessor);
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
    //this.ioProcessor = ioProcessor;
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
  public void promptPlayer(String data) throws IOException {
    this.userInput = JOptionPane.showInputDialog(this, data);
    this.display();
  }

  @Override
  public void updateFixtures(List<String> data) throws IOException {
    this.fixtures = data;
  }

  @Override
  public void updateItems(List<String> data) throws IOException {
    this.items = data;
  }

  @Override
  public void updateTitle(String data) throws IOException {
    if (data.isEmpty()) {
      this.setTitle(DEFAULT_CAPTION);
    } else {
      this.setTitle(data);
    }
  }

  private class ViewPanel extends JPanel {

    public ViewPanel(String title) {
      TitledBorder viewBorder = BorderFactory.createTitledBorder(title);
      this.setBorder(viewBorder);
      viewImage = new JLabel(new ImageIcon(SPLASH_IMAGE));
      viewImage.setPreferredSize(new Dimension(450, 300));
      this.add(viewImage);
      leftPanel.add(this);
    }
  }

  /**
   * The InventoryPanel class is used to display a player's inventory and manipulate
   * the items in the inventory using buttons in the GUI view. It extends JPanel to
   * provide a panel in the JFrame window. It implements the ListSelectionListener
   * interface so that changes to its JList can be registered.
   */
  private class InventoryPanel extends JPanel implements ListSelectionListener {

    /**
     * The constructor for the InventoryPanel builds its elements and adds them
     * to the panel.
     */
    public InventoryPanel() {
      super(new GridLayout(0, 1));
      TitledBorder inventoryBorder = BorderFactory.createTitledBorder("Inventory");
      this.setBorder(inventoryBorder);
      String[] inventoryItems = {};
      inventoryText = new JList<>(inventoryItems);
      inventoryText.setListData(inventoryItems);
      inventoryText.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      this.add(inventoryText);
      inventoryText.addListSelectionListener(this);

      inspectButton = new JButton("Inspect");
      inspectButton.addActionListener(ioProcessor);
      inspectButton.setActionCommand("examine" + " \r " + inventorySelection);

      useButton = new JButton("Use");
      useButton.addActionListener(ioProcessor);
      useButton.setActionCommand("use" + " \r " + inventorySelection);

      dropButton = new JButton("Drop");
      dropButton.addActionListener(ioProcessor);
      dropButton.setActionCommand("drop " + " \r " + inventorySelection);

      JPanel inventoryButtonPanel = new JPanel(new GridLayout(0, 3));
      inventoryButtonPanel.add(inspectButton);
      inventoryButtonPanel.add(useButton);
      inventoryButtonPanel.add(dropButton);
      this.add(inventoryButtonPanel);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
      inspectButton.setActionCommand("examine" + " \r " + inventoryText.getSelectedValue());
      useButton.setActionCommand("use" + " \r " + inventoryText.getSelectedValue());
      dropButton.setActionCommand("drop" + " \r " + inventoryText.getSelectedValue());
    }
  }

  /**
   * The TakeDialog class provides a JDialog box of items the player can take. It extends
   * JDialog to use a dialog box as a wrapper for the other content. It implements
   * ListSelectionLister so that the contents of its JScrollPane can be sent to the
   * ioProcessor ActionListener. TakeDialog has a JButton take, a JList list, and a
   * String command attributes.
   */
  private class TakeDialog extends JDialog implements ListSelectionListener {
    JButton take;
    JList<String> list;
    String command;

    /**
     * The constructor initializes the dialog box, scroll pane, and buttons that are used
     * to take items from the room.
     *
     * @param parent JFrame parent to return value of selection to.
     * @param command String of the name of the take button's command.
     */
    public TakeDialog(JFrame parent, String command) {
      super(parent, command, true);
      if (items != null) {
        this.command = command;
        String[] itemsArray = items.toArray(new String[0]);
        list = new JList<>();
        list.setListData(itemsArray);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(list);
        take = new JButton(command);

        take.addActionListener(ioProcessor);
        take.addActionListener(     event -> {
          String[] updatedItems = items.toArray(new String[0]);
          list.setListData(updatedItems);
        });
        take.addActionListener(event -> this.dispose());

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(event -> this.dispose());
        this.add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(take);
        panel.add(cancel);
        this.add(panel, BorderLayout.SOUTH);
        this.setSize(200, 150);
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
      take.setActionCommand(command.toLowerCase()
              + " \r " + list.getSelectedValue());
    }
  }

  /**
   * The ExamineDialog class provides a JDialog box of items the player can take. It extends
   * JDialog to use a dialog box as a wrapper for the other content. It implements
   * ListSelectionLister so that the contents of its JScrollPane can be sent to the
   * ioProcessor ActionListener. ExamineDialog has List combined, JButton examine,
   * JList list, and String command attributes.
   */
  private class ExamineDialog extends JDialog implements ListSelectionListener {
    private List<String> combined;
    JButton examine;
    JList<String> list;
    String command;

    /**
     * The constructor initializes the dialog box, scroll pane, and buttons that are used
     * to examine items in the room. The combined attribute is set based on whether there
     * are items and fixtures or one of those types in the room.
     *
     * @param parent JFrame object that is the parent frame of the dialog box.
     * @param command String of command executed by dialog box button.
     */
    public ExamineDialog(JFrame parent, String command) {
      super(parent, command, true);
      this.command = command;
      if (items != null && fixtures != null) {
        combined = Stream.concat(items.stream(), fixtures.stream()).toList();
      } else if (items != null) {
        combined = items;
      } else if (fixtures != null) {
        combined = fixtures;
      }

      String[] strList = this.combined.toArray(new String[0]);
      list = new JList<>();
      list.setListData(strList);
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.addListSelectionListener(this);
      this.examine = new JButton(command);
      this.examine.addActionListener(ioProcessor);
      JButton cancel = new JButton("Cancel");
      cancel.addActionListener(event -> this.dispose());
      JScrollPane scrollPane = new JScrollPane(list);
      this.add(scrollPane, BorderLayout.CENTER);
      JPanel panel = new JPanel();
      panel.add(examine);
      panel.add(cancel);
      this.add(panel, BorderLayout.SOUTH);
      this.setSize(200, 150);
      this.setLocationRelativeTo(parent);
      this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
      examine.setActionCommand(command.toLowerCase()
              + " \r " + list.getSelectedValue());
    }
  }

  /**
   * The AnswerDialog class provides a JDialog box of items the player can take. It extends
   * JDialog to use a dialog box as a wrapper for the other content. It implements
   * DocumentLister so that the contents of its JTextArea can be sent to the
   * ioProcessor ActionListener. AnswerDialog has JButton answer, String command, and
   * JTextArea inputField attributes.
   */
  private class AnswerDialog extends JDialog implements DocumentListener {
    JButton answer;
    String command;
    JTextArea inputField;

    /**
     * The constructor initializes the dialog box, text area, and buttons that are used
     * to provide a puzzle's answer.
     *
     * @param parent JFrame object that is the parent frame of the dialog box.
     * @param command String of command executed by dialog box button.
     */
    public AnswerDialog(JFrame parent, String command) {
      super(parent, command, true);
      this.command = command;
      this.answer = new JButton(command);
      this.answer.addActionListener(ioProcessor);
      JButton cancel = new JButton("Cancel");
      cancel.addActionListener(event -> this.dispose());
      inputField = new JTextArea(10, 10);
      inputField.getDocument().addDocumentListener(this);
      this.setLayout(new GridLayout(2, 1));
      JPanel buttonPanel = new JPanel();
      this.add(inputField);
      buttonPanel.add(this.answer);
      buttonPanel.add(cancel);
      this.add(buttonPanel, BorderLayout.SOUTH);
      this.setSize(200, 150);
      this.setLocationRelativeTo(parent);
      this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
      updateActionCommand(this.inputField.getText());;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      updateActionCommand(this.inputField.getText());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
      // maybe we don't need anything here
    }

    /**
     * Updates the action command of the answer button within the AnswerDialog
     * box.
     *
     * @param argument String of the answer argument to provide to the answer a puzzle.
     */
    private void updateActionCommand(String argument) {
      this.answer.setActionCommand(this.command.toLowerCase() + " \r " + argument.trim());
    }
  }


}
