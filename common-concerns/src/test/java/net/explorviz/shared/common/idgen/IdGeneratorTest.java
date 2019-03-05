package net.explorviz.shared.common.idgen;

import static org.junit.Assert.assertFalse;
import javax.inject.Inject;
import net.explorviz.shared.common.injection.CommonDependencyInjectionBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Before;
import org.junit.Test;

public class IdGeneratorTest {


  @Inject
  private IdGenerator idGen1;

  @Inject
  private IdGenerator idGen2;

  @Before
  public void setUp() {
    AbstractBinder binder = new CommonDependencyInjectionBinder();
    ServiceLocator locator = ServiceLocatorUtilities.bind(binder);
    locator.inject(this);
  }


  @Test
  public void testPerLookup() {
    assertFalse(idGen1.equals(idGen2));
  }


}
