package net.explorviz.shared.common.idgen;

import java.util.concurrent.atomic.AtomicLong;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;

/**
 * Creates unique ids per entity. These ids are <b>not</b> unique across the system and should only
 * be used in association with {@link ServiceIdGenerator}.
 * 
 * @see IdGenerator
 */
@Service
@PerLookup
public class AtomicEntityIdGenerator implements EntityIdGenerator {

  private final AtomicLong counter;

  public AtomicEntityIdGenerator() {
    counter = new AtomicLong(0);
  }


  public String getId() {
    return Long.toString(counter.incrementAndGet());
  }

}
