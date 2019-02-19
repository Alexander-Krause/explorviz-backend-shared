package net.explorviz.shared.common.injection;

import com.github.jasminb.jsonapi.ResourceConverter;
import javax.inject.Singleton;
import net.explorviz.shared.common.jsonapi.ResourceConverterFactory;
import net.explorviz.shared.config.annotations.Config;
import net.explorviz.shared.config.annotations.injection.ConfigInjectionResolver;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import net.explorviz.shared.security.TokenParserService;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Configures the dependency binding setup for inject during runtime.
 */
public class DependencyInjectionBinder extends AbstractBinder {

  @Override
  public void configure() {

    this.bindFactory(ResourceConverterFactory.class).to(ResourceConverter.class)
        .in(Singleton.class);

    this.bind(TokenParserService.class).to(TokenParserService.class).in(Singleton.class);

    // injectable config properties
    this.bind(new ConfigInjectionResolver()).to(new TypeLiteral<InjectionResolver<Config>>() {});

    // ErrorObject Handler
    this.bind(ErrorObjectHelper.class).to(ErrorObjectHelper.class).in(Singleton.class);
  }
}
