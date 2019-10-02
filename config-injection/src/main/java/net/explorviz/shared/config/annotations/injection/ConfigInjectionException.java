package net.explorviz.shared.config.annotations.injection;

/**
 * Thrown if the injection of configuration through the
 * {@link net.explorviz.shared.config.annotations.Config} annotation was not successful.
 */
public class ConfigInjectionException extends RuntimeException {

  public ConfigInjectionException() {
  }

  public ConfigInjectionException(String s) {
    super(s);
  }

  public ConfigInjectionException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public ConfigInjectionException(Throwable throwable) {
    super(throwable);
  }

  public ConfigInjectionException(String s, Throwable throwable, boolean b, boolean b1) {
    super(s, throwable, b, b1);
  }
}
