package net.explorviz.shared.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use the {@code @Config} annotation to inject properties from the explorviz.properties file.
 * Supported types are String, int and boolean.
 * <p/>
 * For example:
 *
 * <pre>
 * {@code @Config("jwt.secret")}
 * private String secret;
 * </pre>
 * 
 *
 * @see net.explorviz.shared.config.annotations.injection.ConfigInjectionResolver
 */
@Target({ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ConfigValues.class)
public @interface Config {
  String value(); // NOCS
}
