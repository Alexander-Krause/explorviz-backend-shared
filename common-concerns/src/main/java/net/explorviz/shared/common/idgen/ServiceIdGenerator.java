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

@Service
@Singleton
public class ServiceIdGenerator {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ServiceIdGenerator.class.getSimpleName());
  private static final String IDKEY = "id";


  private JedisPool jedisPool;



  @ConfigValues(@Config("redis.host"))
  public ServiceIdGenerator(String host) {

    jedisPool = new JedisPool(new JedisPoolConfig(), host);

    try (Jedis conn = jedisPool.getResource()) {
      String value = conn.get(IDKEY);
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


  public String getServiceId() {
    try (Jedis conn = jedisPool.getResource()) {
      return Long.toString(conn.incr(IDKEY));
    }
  }



}
