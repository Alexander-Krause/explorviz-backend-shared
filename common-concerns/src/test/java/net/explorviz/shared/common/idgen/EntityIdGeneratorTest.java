package net.explorviz.shared.common.idgen;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EntityIdGeneratorTest {


  @Test
  public void testNonZero() {
    EntityIdGenerator idGenerator = new EntityIdGenerator();
    assertTrue(Long.parseLong(idGenerator.getId()) > 0);
  }


  @Test
  public void testIncrement() {
    EntityIdGenerator idGenerator = new EntityIdGenerator();
    long id = Long.parseLong(idGenerator.getId());
    long next = Long.parseLong(idGenerator.getId());

    assertTrue(next == id + 1);


  }

}
