package net.explorviz.shared.common.idgen;

import javax.inject.Inject;
import net.explorviz.shared.config.annotations.Config;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static final Logger LOGGER = LoggerFactory.getLogger(IdGenerator.class);

  private final ServiceIdGenerator serviceIdGenerator;

  private final EntityIdGenerator entityIdGenerator;

  private String serviceId;

  private final String prefix;


  /**
   * Creates a new IdGenerator based on the given service and entitiy id generators.
   *
   * @param servicePrefix the prefix of the service using the generator.
   * @param serviceIdGen the {@link ServiceIdGenerator} to use.
   * @param entityIdGen the {@link EntityIdGenerator} to use.
   */
  @Inject
  public IdGenerator(final ServiceIdGenerator serviceIdGen, final EntityIdGenerator entityIdGen,
      @Config("service.prefix") final String servicePrefix) {
    this.serviceIdGenerator = serviceIdGen;
    this.entityIdGenerator = entityIdGen;
    this.prefix = servicePrefix;
  }


  /**
   * Generates a new unique identifier.
   *
   * @return the newly generated identifier.
   */
  public String generateId() {

    /*
     * On first call we need to generate the service id. It's impossible to do this when
     * constructing the object, since injection will take place after instantiation.
     */
    if (serviceId == null || serviceId.isEmpty()) {
      this.serviceId = serviceIdGenerator.getId();
    }
    final String entityId = entityIdGenerator.getId();
    final String id = prefix + "-" + serviceId + "-" + entityId;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Generated id {}", id);
    }
    return id;
  }



}
