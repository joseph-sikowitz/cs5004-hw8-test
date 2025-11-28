package view;

import java.io.IOException;
import java.util.Scanner;

import controller.GameTextInputOutputProcessor;

/**
 * An IAdventureGameView that receives input through Readable types and outputs to Appendable types.
 */
public class AdventureGameTextView implements
        IAdventureGameView<String, GameTextInputOutputProcessor> {

  private final Readable in;
  private final Appendable out;

  /**
   * Constructor initializes in and out fields based on parameters.
   * @param in A Readable instance.
   * @param out an Appendable instance.
   */
  public AdventureGameTextView(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void setEventHandler(GameTextInputOutputProcessor commandInterpreter) {
  }

  @Override
  public String getCommand() {
    Scanner scanner = new Scanner(this.in);
    return scanner.nextLine();
  }

  @Override
  public void messageToPlayer(String data) throws IOException {
    this.out.append(data);
  }

  @Override
  public void updatePlayerStats(String data) throws IOException {
    this.out.append(data);
  }

  @Override
  public void updateRoom(String data) throws IOException {
    this.out.append(data);
  }

  @Override
  public void updateExaminer(String data) throws IOException {
    this.out.append(data);
  }

  @Override
  public void updateInventory(String data) throws IOException {
    this.out.append(data);
  }

  @Override
  public void updatePlayerAffector(String data) throws IOException {
    this.out.append(data);
  }
}
