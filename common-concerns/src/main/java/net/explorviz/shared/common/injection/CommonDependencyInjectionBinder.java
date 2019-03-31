package net.explorviz.shared.common.injection;

import com.github.jasminb.jsonapi.ResourceConverter;
import javax.inject.Singleton;
import net.explorviz.shared.common.idgen.AtomicEntityIdGenerator;
import net.explorviz.shared.common.idgen.EntityIdGenerator;
import net.explorviz.shared.common.idgen.IdGenerator;
import net.explorviz.shared.common.idgen.ServiceIdGenerator;
import net.explorviz.shared.common.idgen.UuidServiceIdGenerator;
import net.explorviz.shared.common.jsonapi.ResourceConverterFactory;
import net.explorviz.shared.config.annotations.Config;
import net.explorviz.shared.config.annotations.ConfigValues;
import net.explorviz.shared.config.annotations.injection.ConfigInjectionResolver;
import net.explorviz.shared.config.annotations.injection.ConfigValuesInjectionResolver;
import net.explorviz.shared.exceptions.ErrorObjectHelper;
import net.explorviz.shared.exceptions.JsonApiErrorObjectHelper;
import net.explorviz.shared.security.TokenParserService;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Configures the dependency binding setup for inject during runtime.
 */
public class CommonDependencyInjectionBinder extends AbstractBinder {

  @Override
  public void configure() {

    // Injectable config properties
    this.bind(new ConfigInjectionResolver())
        .to(new TypeLiteral<InjectionResolver<ConfigValues>>() {});
    this.bind(new ConfigValuesInjectionResolver())
        .to(new TypeLiteral<InjectionResolver<ConfigValues>>() {});

    this.bindFactory(ResourceConverterFactory.class).to(ResourceConverter.class)
        .in(Singleton.class);

    this.bind(TokenParserService.class).to(TokenParserService.class).in(Singleton.class);

    // injectable config properties
    this.bind(new ConfigInjectionResolver()).to(new TypeLiteral<InjectionResolver<Config>>() {});

    // ErrorObject Handler
    this.bind(JsonApiErrorObjectHelper.class).to(ErrorObjectHelper.class).in(Singleton.class);

    // Id Generator
    this.bind(UuidServiceIdGenerator.class).to(ServiceIdGenerator.class).in(Singleton.class);
    this.bind(AtomicEntityIdGenerator.class).to(EntityIdGenerator.class).in(PerLookup.class);
    this.bind(IdGenerator.class).to(IdGenerator.class).in(PerLookup.class);
  }
}
