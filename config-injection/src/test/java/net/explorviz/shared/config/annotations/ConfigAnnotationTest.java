package net.explorviz.shared.config.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;
import net.explorviz.shared.config.annotations.injection.ConfigInjectionResolver;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConfigAnnotationTest {

  @Inject
  TestFieldInjectionClass testFieldnjectionClass;

  @Inject
  TestConstructorParameterInjectionClass testConstrParamInjectionClass;

  @Inject
  TestConstructorTopLevelInjectionClass testConstrTopInjectionClass;

  @BeforeEach
  public void setUp() {
    // final AbstractBinder binder = new CommonDependencyInjectionBinder();

    final AbstractBinder binder = new AbstractBinder() {

      @Override
      protected void configure() {
        this.bind(TestFieldInjectionClass.class).to(TestFieldInjectionClass.class);
        this.bind(TestConstructorParameterInjectionClass.class)
            .to(TestConstructorParameterInjectionClass.class);
        this.bind(TestConstructorTopLevelInjectionClass.class)
            .to(TestConstructorTopLevelInjectionClass.class);
        this.bind(new ConfigInjectionResolver())
            .to(new TypeLiteral<InjectionResolver<Config>>() {});
      }
    };

    final ServiceLocator locator = ServiceLocatorUtilities.bind(binder);

    locator.inject(this);
  }

  @Test
  public void checkStringConfigInjectionForFieldTopLevel() {
    assertEquals("field toplevel works", testFieldnjectionClass.fieldTestString);
  }

  @Test
  public void checkBooleanConfigInjectionForFieldTopLevel() {
    assertEquals(true, testFieldnjectionClass.fieldTestBoolean);
  }

  @Test
  public void checkIntConfigInjectionForFieldTopLevel() {
    assertEquals(43, testFieldnjectionClass.fieldTestInt);
  }

  @Test
  public void checkStringConfigInjectionForConstructorTopLevel() {
    assertEquals("constructor toplevel works",
        testConstrTopInjectionClass.testStringForConstructorTopLevelInjection);
  }

  @Test
  public void checkStringConfigInjectionForConstructorParameter() {
    assertEquals("constructor parameter works",
        testConstrParamInjectionClass.testStringForConstructorParameterInjection);
  }

  @Test
  public void checkBooleanConfigInjectionForConstructorParameter() {
    assertEquals(true, testConstrParamInjectionClass.testBooleanForConstructorParameterInjection);
  }

  @Test
  public void checkIntConfigInjectionForConstructorParameter() {
    assertEquals(42, testConstrParamInjectionClass.testIntForConstructorParameterInjection);
  }

}
