package model;

/**
 * The UserPrompts enum contains the prompts that are provided by the controller
 * to the user for game flow. It has an attribute prompt.
 *
 * @author Joe Sikowitz
 */
public enum UserPrompts {
  BASIC_PROMPT("""
          \n==============
          To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
          Other actions: (I)nventory, (L)ook around the location, (U)se an item,
          (T)ake an item, (D)rop an item, or e(X)amine something.
          (A)nswer a question or provide a text solution.
          To end the game, enter (Q)uit to quit and exit.
          """),
  NEW_PLAYER_PROMPT("Enter a name for your player avatar: "),
  PLAYER_RANK_PROMPT("Your rank: "),
  NEW_PLAYER_NAME_PROMPT("You shalt now be named: "),
  USER_CHOICE("Your choice: "),
  GAME_SAVED("Game Saved\n"),
  GAME_RESTORED("Loaded your previous game\n");

  private final String prompt;

  /**
   * The constructor for the UserPrompts enum initializes prompt.
   *
   * @param prompt String of the prompt to display.
   */
  UserPrompts(String prompt) {
    this.prompt = prompt;
  }

  /**
   * The getPrompt() method returns the display prompt.
   *
   * @return String of the display prompt.
   */
  String getPrompt() {
    return this.prompt;
  }
}
