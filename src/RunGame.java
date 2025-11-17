import java.io.IOException;
import java.io.InputStreamReader;

import enginedriver.GameEngineApp;

/**
 * Class for running GameEngineApp.
 */
public class RunGame {

  /**
   * Runs GameEngineApp game with a json data file.
   * @param args not used.
   * @throws IOException if file not found.
   */
  public static void main(String[] args) throws IOException {
    GameEngineApp game = new GameEngineApp("data/museum.json",
            new InputStreamReader(System.in), System.out);
    game.start();
  }
}
