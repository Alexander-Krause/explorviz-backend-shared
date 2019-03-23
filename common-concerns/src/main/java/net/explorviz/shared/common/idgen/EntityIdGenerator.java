package net.explorviz.shared.common.idgen;

import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;

/**
 * Interface for entity id generators, used inside of services.
 *
 * @see AtomicEntityIdGenerator
 */
@Service
@PerLookup
public interface EntityIdGenerator {


  /**
   * Creates an identifier for a new entity.
   * 
   * @return the identifier.
   */
  public String getId();

}
