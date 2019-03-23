package net.explorviz.shared.common.idgen;

import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

/**
 * Interface for services id generators.
 *
 * @see UuidServiceIdGenerator
 */
@Service
@Singleton
public interface ServiceIdGenerator {

  /**
   * Creates an identifier for a new service.
   * 
   * @return the identifier.
   */
  public String getId();

}
