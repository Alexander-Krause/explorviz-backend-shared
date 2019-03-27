package net.explorviz.shared.landscape.model.landscape;



import static org.junit.jupiter.api.Assertions.assertEquals;

import net.explorviz.shared.common.idgen.AtomicEntityIdGenerator;
import net.explorviz.shared.common.idgen.IdGenerator;
import net.explorviz.shared.common.idgen.UuidServiceIdGenerator;
import net.explorviz.shared.landscape.model.event.EEventType;
import net.explorviz.shared.landscape.model.event.Event;
import net.explorviz.shared.landscape.model.helper.BaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LandscapeTest {

  private Landscape l;

  @BeforeEach
  public void setUp() {
    BaseEntity.initialize(
        new IdGenerator("landscape", new UuidServiceIdGenerator(), new AtomicEntityIdGenerator()));
    l = new Landscape();
  }

  /**
   * Check if newly created event is really added to landscape.
   *
   * @see Landscape
   */
  @Test
  public void testEventCreation() {
    // declare the expected new application event
    final long currentMillis = java.lang.System.currentTimeMillis();

    final String expectedEventMessage = "New application 'jPetStore' on node 'node1' detected";
    final Event expectedEvent =
        new Event(currentMillis, EEventType.NEWAPPLICATION, expectedEventMessage);

    // test the method and verify
    l.createNewEvent(EEventType.NEWAPPLICATION, expectedEventMessage);

    final Event actualEvent = l.getEvents().get(0);

    // the attributes must be equal
    assertEquals(expectedEvent.getEventType(), actualEvent.getEventType());
    assertEquals(expectedEvent.getEventMessage(), actualEvent.getEventMessage());
  }

  /**
   * Tests the creation of a new exception event
   */
  @Test
  public void testCreateNewException() {

    // declare the expected exception event
    final long currentMillis = java.lang.System.currentTimeMillis();

    final String expectedCause =
        "Exception thrown in application 'sampleApplication' by class 'boolean java.sql.Statement.execute(String)':\\n ...";
    final Event expectedEvent = new Event(currentMillis, EEventType.EXCEPTION, expectedCause);

    // test the method and verify
    l.createNewException(expectedCause);

    final Event actualEvent = l.getEvents().get(0);

    // the attributes must be equal
    assertEquals(expectedEvent.getEventType(), actualEvent.getEventType());
    assertEquals(expectedEvent.getEventMessage(), actualEvent.getEventMessage());
  }

}
