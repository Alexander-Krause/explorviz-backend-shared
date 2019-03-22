package net.explorviz.shared.common.idgen;

import javax.inject.Inject;
import net.explorviz.shared.config.annotations.Config;
import net.explorviz.shared.config.annotations.ConfigValues;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(IdGenerator.class.getSimpleName());

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
   * Constructor for manual instantiation. Use this when dependency injection is not possible.
   * Otherwise dependency injection should be preferred.
   * 
   * <p>
   * Creates a new IdGenerator based on the given service and entitiy id generators.
   * </p>
   * 
   * @param servicePrefix the prefix of the service using the generator.
   * @param serviceIdGen the {@link ServiceIdGenerator} to use.
   * @param entityIdGen the {@link EntityIdGenerator} to use.
   */
  public IdGenerator(final String servicePrefix, final ServiceIdGenerator serviceIdGen,
      final EntityIdGenerator entityIdGen) {
    this(servicePrefix);
    this.serviceIdGenerator = serviceIdGen;
    this.entityIdGenerator = entityIdGen;
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
    final String id = prefix + serviceId + "-" + entityId;
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("".format("Generated id %s", id));
    }
    return id;
  }



}
