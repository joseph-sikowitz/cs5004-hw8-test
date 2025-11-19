package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * Testes the TakeItemStatus enum class.
 */
class TakeItemStatusTest {

  @Test
  void getStatus() {
    assertEquals(" added to your inventory.", TakeItemStatus.ITEM_ADDED.getStatus());
    assertEquals(" was not added to your inventory "
            + "as you cannot carry anymore weight!",
            TakeItemStatus.ITEM_NOT_ADDED_OVER_CAPACITY.getStatus());
    assertEquals(" not found in this Room.", TakeItemStatus.ITEM_NOT_FOUND.getStatus());
  }
}