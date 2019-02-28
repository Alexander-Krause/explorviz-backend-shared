package net.explorviz.shared.common.idgen;

import java.util.concurrent.atomic.AtomicLong;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;

@Service
@PerLookup
public class EntityIdGenerator {

  private AtomicLong counter;

  public EntityIdGenerator() {
    counter = new AtomicLong(0);
  }


  public String getId() {
    return Long.toString(counter.incrementAndGet());
  }

}
