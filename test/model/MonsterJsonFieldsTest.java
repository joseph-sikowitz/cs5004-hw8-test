package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the MonsterJsonFields enum.
 */
class MonsterJsonFieldsTest {

  /**
   * Tests the getter for the enum's value.
   */
  @Test
  void getValue() {
    assertEquals("name",  MonsterJsonFields.NAME.getValue());
    assertEquals("active", MonsterJsonFields.ACTIVE.getValue());
    assertEquals("affects_target", MonsterJsonFields.AFFECTS_TARGET.getValue());
    assertEquals("affects_player", MonsterJsonFields.AFFECTS_PLAYER.getValue());
    assertEquals("solution", MonsterJsonFields.SOLUTION.getValue());
    assertEquals("value", MonsterJsonFields.VALUE.getValue());
    assertEquals("description", MonsterJsonFields.DESCRIPTION.getValue());
    assertEquals("effects", MonsterJsonFields.EFFECTS.getValue());
    assertEquals("damage", MonsterJsonFields.DAMAGE.getValue());
    assertEquals("target", MonsterJsonFields.TARGET.getValue());
    assertEquals("can_attack", MonsterJsonFields.CAN_ATTACK.getValue());
    assertEquals("attack", MonsterJsonFields.ATTACK.getValue());
    assertEquals("picture", MonsterJsonFields.PICTURE.getValue());
  }

}