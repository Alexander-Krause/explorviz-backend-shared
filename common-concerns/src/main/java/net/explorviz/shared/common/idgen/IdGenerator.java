package net.explorviz.shared.common.idgen;

import javax.inject.Inject;
import net.explorviz.shared.config.annotations.Config;
import net.explorviz.shared.config.annotations.ConfigValues;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;

/**
 * Helper class to generate system-wide unique identifiers for entities. Each identifier consists of
 * <ol>
 * <li>The prefix of the service</li>
 * <li>The services identifier</li>
 * <li>The entity-id</li>
 * </ol>
 *
 */
@Service
@PerLookup
public class IdGenerator {


  @Inject
  private ServiceIdGenerator serviceIdGenerator;

  @Inject
  private EntityIdGenerator entityIdGenerator;

  private String serviceId;

  private final String prefix;

  @ConfigValues(@Config("service.prefix"))
  public IdGenerator(final String servicePrefix) {
    this.prefix = servicePrefix;
  }

  /**
   * Generates a new unique identifier.
   * 
   * @return the newly generated identifier.
   */
  public String generateId() {
    if (serviceId == null || serviceId.isEmpty()) {
      this.serviceId = serviceIdGenerator.getId();
    }

    final String entityId = entityIdGenerator.getId();

    return prefix + serviceId + "-" + entityId;
  }

}
