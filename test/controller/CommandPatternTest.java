package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import enginedriver.GameEngineApp;

import org.junit.jupiter.api.Test;

/**
 * Tests the command pattern classes used to execute commands in the controller.
 */
public class CommandPatternTest {

  /**
   * Tests the start game command that starts the game and takes the user's name.
   */
  @Test
  void testStartGameCommand() {
    String s = "Joe\nQ";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the examine command by looking at the output of examining a ticket.
   */
  @Test
  void testExamineCommand() {
    String s = "Joe\nexamine ticket\nQ";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: A complimentary museum ticket. It says ADMIT ONE, pwd = Align.
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the take command by testing the output of taking a ticket.
   */
  @Test
  void testTakeCommand() {
    String s = "Joe\ntake ticket\nQ";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: ticket added to your inventory.
            Your inventory contains: Ticket
            You are fully Awake
            Your score: 5
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 5\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the drop command by taking and dropping a ticket and comparing the output.
   */
  @Test
  void testDropCommand() {
    String s = "Joe\ntake ticket\nd ticket\nQ";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: ticket added to your inventory.
            Your inventory contains: Ticket
            You are fully Awake
            Your score: 5
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: ticket removed from your inventory!
            You have no items in your inventory!
            Items you see here: Ticket
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 5\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the use command by taking and using a ticket and comparing the output.
   */
  @Test
  void testUseCommand() {
    String s = "Joe\ntake ticket\nu ticket\nQ";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: ticket added to your inventory.
            Your inventory contains: Ticket
            You are fully Awake
            Your score: 5
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: You insert the ticket. 'Swish! Beep!'
            TURNSTILE was deactivated by Ticket!
            You're standing at the entrance of the Museum of Natural History. There's a turnstile that takes tickets, but it's already been activated and opened.
            You are fully Awake
            Your score: 155
            Your rank: Squire
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 155\s
            Your rank: Squire
            """, output.toString());
  }

  /**
   * Tests the look command by looking and comparing the output.
   */
  @Test
  void testLookCommand() {
    String s = "Joe\nlook\nQ";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the answer command by moving into a room where an answer deactivates
   * a puzzle and comparing the output.
   */
  @Test
  void testAnswerCommand() {
    String s = "Joe\nt ticket\nu ticket\nn\na align\nquit";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: ticket added to your inventory.
            Your inventory contains: Ticket
            You are fully Awake
            Your score: 5
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: You insert the ticket. 'Swish! Beep!'
            TURNSTILE was deactivated by Ticket!
            You're standing at the entrance of the Museum of Natural History. There's a turnstile that takes tickets, but it's already been activated and opened.
            You are fully Awake
            Your score: 155
            Your rank: Squire
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: The passage between rooms is open! You enter: First Exhibit
            A computer that seems to control an invisible forcefield blocking your motion. A password screen is waiting for an entry.
            Fixtures you see here: Computer
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: PASSWORD was deactivated by align!
            This first exhibit shows the battle for earth, with the ape Caesar defeating the humans
            You are fully Awake
            Your score: 305
            Your rank: Knight
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 305\s
            Your rank: Knight
            """, output.toString());
  }

  /**
   * Tests the north command by comparing the output.
   */
  @Test
  void testNorthCommand() {
    String s = "Joe\nn\nquit";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Passage to Room is blocked!
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the south command by comparing the output.
   */
  @Test
  void testSouthCommand() {
    String s = "Joe\ns\nquit";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Wall or no Passage in this direction!
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the east command by comparing the output.
   */
  @Test
  void testEastCommand() {
    String s = "Joe\ne\nquit";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Wall or no Passage in this direction!
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the west command by comparing the output.
   */
  @Test
  void testWestCommand() {
    String s = "Joe\nw\nquit";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Wall or no Passage in this direction!
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the inventory command output by checking inventory, taking an item,
   * and rechecking inventory.
   */
  @Test
  void testInventoryCommand() {
    String s = "Joe\ni\nt ticket\ni\nquit";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: You have no items in your inventory!
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: ticket added to your inventory.
            Your inventory contains: Ticket
            You are fully Awake
            Your score: 5
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Your inventory contains: Ticket
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 5\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the game save command by checking output.
   */
  @Test
  void testSaveCommand() {
    String s = "Joe\nsave\nquit";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Game saved!
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the game restore command by checking output.
   */
  @Test
  void testRestoreCommand() {
    String s = "Joe\nrestore\nquit";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Game restored!
            Welcome back Joe
            You are fully Awake
            Your score: 0
            Your rank: Novice
            You are currently in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You have no items in your inventory!
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

  /**
   * Tests the game quit command by checking output.
   */
  @Test
  void testQuitCommand() {
    String s = "Joe\nquit";
    BufferedReader stringReader = new BufferedReader(new StringReader(s));
    Appendable output = new StringBuilder();
    GameEngineApp gameEngineApp = new GameEngineApp(
            "./resources/museum.json", stringReader, output);
    try {
      gameEngineApp.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("""
            Enter a name for your player avatar: Welcome to Museum of Planet of the Apes!
            You shalt now be named: Joe
            
            Possible error in game file: One or more passages between Rooms are not reflexive!
            
            You start in Museum Entrance:
            There is a turnstile to the north. It requires some type of payment or ticket to activate.
            Items you see here: Ticket
            You are fully Awake
            Your score: 0
            Your rank: Novice
            
            ==============
            To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
            Other actions: (I)nventory, (L)ook around the location, (U)se an item,
            (T)ake an item, (D)rop an item, or e(X)amine something.
            (A)nswer a question or provide a text solution.
            To end the game, enter (Q)uit to quit and exit.
            Your choice: Thanks for playing!
            Final score: 0\s
            Your rank: Novice
            """, output.toString());
  }

}
