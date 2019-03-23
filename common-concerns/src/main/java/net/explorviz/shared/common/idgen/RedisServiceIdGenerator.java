package net.explorviz.shared.common.idgen;

import javax.inject.Singleton;
import net.explorviz.shared.config.annotations.Config;
import net.explorviz.shared.config.annotations.ConfigValues;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;


/**
 * Creates unique identifiers for services, backed by redis atomic service counter. Can be used for
 * debugging in development.
 *
 * @see IdGenerator
 */
@Service
@Singleton
public class RedisServiceIdGenerator implements ServiceIdGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceIdGenerator.class);


  private static final String IDKEY = "id";


  private final JedisPool jedisPool;


  /**
   * Creates a new ServiceIdGenerator. Instantiation should be performed by dependency injections
   * framework
   * 
   * @param host the redis host.
   */
  @ConfigValues(@Config("redis.host"))
  public RedisServiceIdGenerator(final String host) {

    jedisPool = new JedisPool(new JedisPoolConfig(), host);

    try (Jedis conn = jedisPool.getResource()) {
      final String value = conn.get(IDKEY);
      if (value == null) {
        conn.set(IDKEY, "0");
        if (LOGGER.isWarnEnabled()) {
          LOGGER.warn("Id counter was not set. Is now 0.");
        }
      } else {
        try {
          Long.parseLong(value);
        } catch (NumberFormatException e) {
          conn.set(IDKEY, "0");
          LOGGER.warn("Id counter was set but not an integer. Is now 0.");
        }
      }
    } catch (JedisConnectionException e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("Could not setup redis client: " + e.getMessage());
      }

      throw new IllegalStateException(e);
    }

  }


  /**
   * Creates an identifier for a new service.
   * 
   * @return the identifier.
   */
  public String getId() {
    try (Jedis conn = jedisPool.getResource()) {
      return Long.toString(conn.incr(IDKEY));
    }
  }



}
