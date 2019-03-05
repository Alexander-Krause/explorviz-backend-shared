package net.explorviz.shared.common.idgen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for {@link EntityIdGenerator}.
 */
public class EntityIdGeneratorTest {


  /**
   * Tests whether the first generated id is greater than 0.
   */
  @Test
  public void testNonZero() {
    final EntityIdGenerator idGenerator = new EntityIdGenerator();
    assertTrue("Id is 0 or less then 0.", Long.parseLong(idGenerator.getId()) > 0);
  }


  /**
   * Tests if a generated id is the increment of the id generated before.
   */
  @Test
  public void testIncrement() {
    final EntityIdGenerator idGenerator = new EntityIdGenerator();
    final long id = Long.parseLong(idGenerator.getId());
    final long next = Long.parseLong(idGenerator.getId());

    assertEquals("Something went wrong", next, id + 1);


  }

}
