package net.explorviz.shared.common.idgen;

import java.util.UUID;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;


/**
 * Creates unique identifiers for services, backed by Uuid generator.
 *
 * @see IdGenerator
 */
@Service
@Singleton
public class UuidServiceIdGenerator implements ServiceIdGenerator {

  /**
   * Creates an Uuid-based identifier for a new service.
   * 
   * @return the identifier.
   */
  public String getId() {
    final UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }



}
