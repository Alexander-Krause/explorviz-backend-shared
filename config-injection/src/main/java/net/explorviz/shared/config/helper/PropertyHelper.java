package net.explorviz.shared.config.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Utility class to read configuration properties in non-DI contexts. Use the
 * {@link net.explorviz.shared.config.annotations.Config} instead.
 */
public final class PropertyHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(PropertyHelper.class);

  private static final String PROPERTIES_DEFAULT_FILENAME = "explorviz.properties";
  private static final String PROPERTIES_CUSTOM_FILENAME = "explorviz-custom.properties";
  private static final String PROPERTIES_TEST_FILENAME = "explorviz-test.properties";

  private static final String ACTIVE_PROPERTIES_FILENAME;

  private static final Properties PROP = new Properties();

  private static final IllegalStateException EXCEPTION = new IllegalStateException(
      "An internal server error occured. Contact your administrator.");

  private static final String UPDATE_MSG =
      "Updated static PropertyHelper due to passed properties.";

  private static Properties passedProperties = null;
  private static AtomicBoolean wasUpdatedViaPassedProperties = new AtomicBoolean(false);

  private PropertyHelper() {
    // don't instantiate
  }

  static {

    final ClassLoader loader = Thread.currentThread().getContextClassLoader();

    InputStream input = loader.getResourceAsStream(PROPERTIES_TEST_FILENAME);

    if (input == null) {
      // no testing environment, use custom or default file

      input = loader.getResourceAsStream(PROPERTIES_CUSTOM_FILENAME);

      if (input == null) {
        // use default properties
        input = loader.getResourceAsStream(PROPERTIES_DEFAULT_FILENAME);

        if (input == null) {
          LOGGER.error("Couldn't load default property file.");
          throw EXCEPTION;
        }

        ACTIVE_PROPERTIES_FILENAME = PROPERTIES_DEFAULT_FILENAME;
        LOGGER.info("Using default property file.");
      } else {
        ACTIVE_PROPERTIES_FILENAME = PROPERTIES_CUSTOM_FILENAME;
        LOGGER.info("Using custom property file.");
      }
    } else {
      ACTIVE_PROPERTIES_FILENAME = PROPERTIES_TEST_FILENAME;
      LOGGER.info("Using test property file.");
    }

    try {
      PROP.load(input);
    } catch (final IOException e) {
      LOGGER.error("Couldn't load property file.");
      throw EXCEPTION;
    }
  }

  private static void updateProperties(Properties props) {
    PROP.putAll(props);
    wasUpdatedViaPassedProperties.set(true);
  }

  public static int getIntegerProperty(final String propName) {
    if (!wasUpdatedViaPassedProperties.get() && passedProperties != null) {
      updateProperties(passedProperties);
      wasUpdatedViaPassedProperties.set(true);
      LOGGER.info(UPDATE_MSG);
    }

    return Integer.parseInt(getStringProperty(propName));
  }

  public static String getStringProperty(final String propName) {
    if (!wasUpdatedViaPassedProperties.get() && passedProperties != null) {
      updateProperties(passedProperties);
      wasUpdatedViaPassedProperties.set(true);
      LOGGER.info(UPDATE_MSG);
    }

    return String.valueOf(PROP.get(propName));
  }

  public static boolean getBooleanProperty(final String propName) {
    if (!wasUpdatedViaPassedProperties.get() && passedProperties != null) {
      updateProperties(passedProperties);
      wasUpdatedViaPassedProperties.set(true);
      LOGGER.info(UPDATE_MSG);
    }

    return Boolean.parseBoolean(getStringProperty(propName));
  }

  /**
   * Set a boolean property inside the explorviz.properties file.
   *
   * @param propName - Property key
   * @param value - Property value. String or string-castable object.
   * @throws FileNotFoundException - Thrown if explorviz.properties was not found.
   * @throws IOException - Thrown if cast to String did not work.
   */
  public static void setBooleanProperty(final String propName, final boolean value)
      throws FileNotFoundException, IOException {
    PROP.setProperty(propName, String.valueOf(value));

    try (OutputStream out = Files.newOutputStream(Paths.get(ACTIVE_PROPERTIES_FILENAME))) {
      PROP.store(out, null);
    }
  }

  public static Properties getLoadedProperties() {
    return PROP;
  }

  public static Properties getPassedProperties() {
    return passedProperties;
  }

  public static void setPassedProperties(Properties passedProperties) {
    PropertyHelper.passedProperties = passedProperties;
  }

}
