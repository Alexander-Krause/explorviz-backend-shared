package net.explorviz.shared.common.idgen;

import static org.junit.Assert.assertFalse;
import javax.inject.Inject;
import net.explorviz.shared.common.injection.CommonDependencyInjectionBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Tests the {@link IdGenerator}.
 */
public class IdGeneratorTest {


  @Inject
  private IdGenerator idGen1;

  @Inject
  private IdGenerator idGen2;

  /**
   * Inject dependencies.
   */
  @BeforeEach
  public void setUp() {
    final AbstractBinder binder = new CommonDependencyInjectionBinder();
    final ServiceLocator locator = ServiceLocatorUtilities.bind(binder);
    locator.inject(this);
  }


  /**
   * Check if each injected instance is freshly created and not a singleton.
   */
  @Test
  public void testPerLookup() {
    assertFalse("Per lookup injection failed", idGen1.equals(idGen2));
  }


}
