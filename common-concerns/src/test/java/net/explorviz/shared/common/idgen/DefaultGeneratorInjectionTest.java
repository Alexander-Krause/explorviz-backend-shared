package net.explorviz.shared.common.idgen;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import net.explorviz.shared.common.injection.CommonDependencyInjectionBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Before;
import org.junit.Test;

public class DefaultGeneratorInjectionTest {

  @Inject
  private ServiceIdGenerator serviceIdGen;

  @Inject
  private EntityIdGenerator entityIdGen;

  /**
   * Inject dependencies.
   */
  @Before
  public void setUp() {
    final AbstractBinder binder = new CommonDependencyInjectionBinder();
    final ServiceLocator locator = ServiceLocatorUtilities.bind(binder);
    locator.inject(this);
  }

  /**
   * Check if injected service generator is (per default) UuidServiceIdGenerator
   * 
   * @see UuidServiceIdGenerator
   */
  @Test
  public void testDefaultServiceGeneratorInjection() {
    assertTrue("Default service generator injection failed",
        serviceIdGen instanceof UuidServiceIdGenerator);
  }

  /**
   * Check if injected service generator is (per default) AtomicEntityIdGenerator
   * 
   * @see AtomicEntityIdGenerator
   */
  @Test
  public void testDefaultEntityGeneratorInjection() {
    assertTrue("Default service generator injection failed",
        entityIdGen instanceof AtomicEntityIdGenerator);
  }

}
