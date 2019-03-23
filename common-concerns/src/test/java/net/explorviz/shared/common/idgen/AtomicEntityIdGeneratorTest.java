package net.explorviz.shared.common.idgen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for {@link AtomicEntityIdGenerator}.
 */
public class AtomicEntityIdGeneratorTest {


  /**
   * Tests whether the first generated id is greater than 0.
   */
  @Test
  public void testNonZero() {
    final AtomicEntityIdGenerator idGenerator = new AtomicEntityIdGenerator();
    assertTrue("Id is 0 or less then 0.", Long.parseLong(idGenerator.getId()) > 0);
  }


  /**
   * Tests if a generated id is the increment of the id generated before.
   */
  @Test
  public void testIncrement() {
    final AtomicEntityIdGenerator idGenerator = new AtomicEntityIdGenerator();
    final long id = Long.parseLong(idGenerator.getId());
    final long next = Long.parseLong(idGenerator.getId());

    assertEquals("Something went wrong", next, id + 1);


  }

}
