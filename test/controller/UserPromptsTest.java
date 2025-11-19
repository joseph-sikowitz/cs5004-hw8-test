package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test suite for the UserPrompts enum's getter.
 */
class UserPromptsTest {

  /**
   * Tests the getter for the enum's prompts.
   */
  @Test
  void getPrompt() {
    assertEquals("""
          \n==============
          To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
          Other actions: (I)nventory, (L)ook around the location, (U)se an item,
          (T)ake an item, (D)rop an item, or e(X)amine something.
          (A)nswer a question or provide a text solution.
          To end the game, enter (Q)uit to quit and exit.
          """, UserPrompts.BASIC_PROMPT.getPrompt());

    assertEquals("Enter a name for your player avatar: ", UserPrompts.NEW_PLAYER_PROMPT.getPrompt());

    assertEquals("Your rank: ", UserPrompts.PLAYER_RANK_PROMPT.getPrompt());

    assertEquals("You shalt now be named: ", UserPrompts.NEW_PLAYER_NAME_PROMPT.getPrompt());

    assertEquals("Your choice: ", UserPrompts.USER_CHOICE.getPrompt());

    assertEquals("Game Saved\n", UserPrompts.GAME_SAVED.getPrompt());

    assertEquals("Loaded your previous game\n", UserPrompts.GAME_RESTORED.getPrompt());
  }
}