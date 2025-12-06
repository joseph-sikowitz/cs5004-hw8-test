package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests that AdventureGameModelTest don't throw exceptions.
 */
class AdventureGameModelTest {

  IAdventureGameModel model;

  @BeforeEach
  void setUp() {
    model = new AdventureGameModel("resources/simple_hallway.json");
  }

  @Test
  void testLoadGameData() {
    assertDoesNotThrow(model::loadGameData);

  }

  @Test
  void testSetPlayerName() {
    model.setPlayerName("player");
    assertDoesNotThrow(model::loadGameData);
  }



  @Test
  void testMovePlayerNorth() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(model::movePlayerNorth);
  }

  @Test
  void testMovePlayerSouth() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(model::movePlayerSouth);
  }

  @Test
  void testMovePlayerEast() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(model::movePlayerEast);
  }

  @Test
  void testMovePlayerWest() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(model::movePlayerWest);
  }

  @Test
  void testCheckInventory() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(model::checkInventory);
  }

  @Test
  void testLookAround() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(model::lookAround);
  }
  @Test
  void testExamine() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.examine("Notebook"));
    assertDoesNotThrow(() -> model.examine("Random thing not in room"));
    assertDoesNotThrow(() -> model.examine(null));
  }


  @Test
  void testTakeItem() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.takeItem("Notebook"));
    assertDoesNotThrow(() -> model.takeItem("Random thing not in room"));
    assertDoesNotThrow(() -> model.takeItem(null));
  }

  @Test
  void testDropItem() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.dropItem("Notebook"));
    assertDoesNotThrow(() -> model.takeItem("Notebook"));
    assertDoesNotThrow(() -> model.dropItem("Notebook"));
    assertDoesNotThrow(() -> model.takeItem("Random thing not in room"));
    assertDoesNotThrow(() -> model.dropItem(null));
  }

  @Test
  void testUseItem() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.useItem("Notebook"));
    assertDoesNotThrow(() -> model.takeItem("Notebook"));
    assertDoesNotThrow(() -> model.useItem("Notebook"));

  }

  @Test
  void testAnswer() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.answer("answer"));
    assertDoesNotThrow(() -> model.answer(""));
    assertDoesNotThrow(() -> model.answer(null));
  }


  @Test
  void testChangeInPlayerHealthStatus() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(model::changeInPlayerHealthStatus);
  }

  @Test
  void testGetPlayerHealthStatus() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(model::getPlayerHealthStatus);
  }

  @Test
  void testAffectPlayer() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(model::affectPlayer);
  }

  @Test
  void testSaveGame() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.saveGame("resources/test_save_file.json"));
  }

  @Test
  void testRestoreGame() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.restoreGame("resources/test_save_file.json"));

  }

  @Test
  void testGetGameFileWarnings() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.getGameFileWarnings());
  }

  @Test
  void testRestoreMessage() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.restoreMessage());
  }

  @Test
  void testQuitMessage() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.quitMessage());
  }

  @Test
  void testGameOver() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.gameOver());
  }

  @Test
  void testGetPlayerName() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.getPlayerName());
  }

  @Test
  void testChangeInPlayerScore() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.changeInPlayerScore());
  }

  @Test
  void testGetPlayerScore() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.getPlayerScore());
  }

  @Test
  void testGetPlayerScoreFormatted() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.getPlayerScoreFormatted());
  }

  @Test
  void testChangeInPlayerRank() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.changeInPlayerRank());
  }

  @Test
  void testGetPlayerRank() {
    assertDoesNotThrow(model::loadGameData);
    assertDoesNotThrow(() -> model.getPlayerRank());
  }
}