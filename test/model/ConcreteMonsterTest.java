package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConcreteMonsterTest {

  private ConcreteMonster m1;
  private Monster m2;

  @BeforeEach
  void setUp() {
    m1 = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    m2 = new ConcreteMonster("Teddy Bear", "A peaceful, cute-looking teddy bear "
            + "with its hair clipped sits on the floor", true, true, "3:Foyer",
            true, null, "Hair Clippers", 200, "A monster "
            + "Teddy Bear growls at you! You cannot get past!", -5, "monster-teddy.png",
            true, "hits you with soft, fluffy paws! You might sneeze!");
  }

  @Test
  void testConstructor() {
    ConcreteMonster monster = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            "licks you with a giant tongue!");

    assertInstanceOf(ConcreteMonster.class, monster);
    assertEquals("Rabbit", monster.getName());
    assertEquals("Awww. A furry rabbit twitching its nose and eating a carrot. "
            + "Makes you want to pet him",  monster.getDescription());
    assertTrue(monster.isActive());
    assertTrue(monster.affectsTarget());
    assertEquals("7:Dining Room", monster.getTarget());
    assertTrue(monster.affectsPlayer());
    assertEquals(300, monster.getScore());
    assertEquals("A monster Rabbit moves towards you! He's blocking the way north. "
            + "\nI think you might be dinner!", monster.getEffect());
    assertEquals(-15, monster.getDamage());
    assertEquals("monster-rabbit.png", monster.getPicturePath());
    assertTrue(monster.canAttack());
    assertEquals("licks you with a giant tongue!", monster.getAttackDescription());
  }

  @Test
  void testConstructorCanAttackFalse() {
    assertThrows(IllegalArgumentException.class, () -> new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", false,
            "licks you with a giant tongue!"));
  }

  @Test
  void testConstructorDamageZero() {
    assertThrows(IllegalArgumentException.class, () -> new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", 0.0, "monster-rabbit.png", true,
            "licks you with a giant tongue!"));
  }

  @Test
  void testConstructorAttackDescriptionInvalid() {
    assertThrows(IllegalArgumentException.class, () -> new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            ""));

    assertThrows(IllegalArgumentException.class, () -> new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", true,
            null));
  }

  @Test
  void testConstructorCannotAttackAndDamage() {
    ConcreteMonster monster2 = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", 0.0, "monster-rabbit.png", false,
            "licks you with a giant tongue!");
    assertFalse(monster2.canAttack());
    assertEquals(0.0, monster2.getDamage());

    assertThrows(IllegalArgumentException.class, () -> new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -15, "monster-rabbit.png", false,
            "licks you with a giant tongue!"));
  }

  @Test
  void testGetDamage() {
    assertEquals(-15, m1.getDamage());
    assertEquals(-5, m2.getDamage());
  }

  @Test
  void testCanAttack() {
    assertTrue(m1.canAttack());
    assertTrue(m2.canAttack());
  }

  @Test
  void testGetAttackDescription() {
    assertEquals("licks you with a giant tongue!", m1.getAttackDescription());
    assertEquals("hits you with soft, fluffy paws! You might sneeze!",
            m2.getAttackDescription());
  }

  @Test
  void testAttack() {
    Monster monster = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", 0.0, "monster-rabbit.png", false,
            "licks you with a giant tongue!");

    Map<Directions, Integer> passages = new HashMap<>();
    passages.put(Directions.NORTH, 0);
    passages.put(Directions.SOUTH, 0);
    passages.put(Directions.EAST, 0);
    passages.put(Directions.WEST, 0);
    assertFalse(monster.attack(new ConcretePlayer("Joe", new HashMap<>(),
            new ConcreteRoom("room1", "empty room", 1, passages,
                    new HashMap<>(), new HashMap<>(), null, null, null))));

    assertFalse(monster.attack(null));

    Monster monster2 = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", false, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -5.0, "monster-rabbit.png", true,
            "licks you with a giant tongue!");
    assertFalse(monster2.attack(new ConcretePlayer("Joe", new HashMap<>(),
            new ConcreteRoom("room1", "empty room", 1, passages,
                    new HashMap<>(), new HashMap<>(), null, null, null))));

    Monster monster3 = new ConcreteMonster("Rabbit",
            "Awww. A furry rabbit twitching its nose and eating a carrot. "
                    + "Makes you want to pet him", true, true, "7:Dining Room",
            true, null,"Carrot", 300, "A monster "
            + "Rabbit moves towards you! He's blocking the way north. \nI think you might "
            + "be dinner!", -5.0, "monster-rabbit.png", true,
            "licks you with a giant tongue!");
    assertTrue(monster3.attack(new ConcretePlayer("Joe", new HashMap<>(),
            new ConcreteRoom("room1", "empty room", 1, passages,
                    new HashMap<>(), new HashMap<>(), null, null, null))));
  }
}