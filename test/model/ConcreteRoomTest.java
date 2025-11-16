package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ConcreteRoomTest {

  private ConcreteRoom r1;
  private Room r2;

  @BeforeEach
  void setUp() {
    Map<Directions, Integer> passages = new HashMap<>();
    passages.put(Directions.NORTH, 5);
    passages.put(Directions.SOUTH, -4);
    passages.put(Directions.EAST, 0);
    passages.put(Directions.WEST, 0);

    Item i1 = new ConcreteItem("Lamp", "An old oil lamp with flint to spark.",
            100, 3, "lamp.png", 100, 20,
            "You light the lamp with the flint.");
    Item i2 = new ConcreteItem("Thumb Drive", "A USB thumb drive for computers", 150,
            1, null, 1000, 1000, "You insert the thumb drive.");
    Map<String, Item> items = new HashMap<>();
    items.put("Lamp", i1);
    items.put("Thumb Drive", i2);

    Fixture f1 = new ConcreteFixture("Bookshelf", "A bookshelf filled with books of magic",
            250.0, null, null, "pictures/bookshelf.jpg");
    Fixture f2 = new ConcreteFixture("Table", "A table with a computer and pen",
            200.0, null, "Solid|Liquid|Gas", "pictures/table.jpg");
    Map<String, Fixture> fixtures = new HashMap<>();
    fixtures.put("Bookshelf", f1);
    fixtures.put("Table", f2);


  }

  @Test
  void getDescription() {
  }

  @Test
  void getRoomNumber() {
  }

  @Test
  void getPassageValue() {
  }

  @Test
  void setPassageValue() {
  }

  @Test
  void getFixture() {
  }

  @Test
  void getItem() {
  }

  @Test
  void getItems() {
  }

  @Test
  void getMonster() {
  }

  @Test
  void getPuzzle() {
  }

  @Test
  void getRoomEnvironmentEffector() {
  }

  @Test
  void addItem() {
  }

  @Test
  void removeItem() {
  }

  @Test
  void getPassageRoom() {
  }

  @Test
  void isPathBlocked() {
  }

  @Test
  void getPicturePath() {
  }
}