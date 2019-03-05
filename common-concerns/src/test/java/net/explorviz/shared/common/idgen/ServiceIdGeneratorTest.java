package net.explorviz.shared.common.idgen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link ServiceIdGenerator}.
 *
 */
public class ServiceIdGeneratorTest {

  private static final int THREADS = 100;

  private ServiceIdGenerator idGenerator;


  @Before
  public void setUp() throws Exception {
    idGenerator = new ServiceIdGenerator("localhost");
  }



  @Test
  public void testGetServiceId() {
    final String id = idGenerator.getId();

    // No exception should be thrown
    Long.parseLong(id);
    assertTrue("", true);
  }


  /**
   * Checks if the ids are truly unique even in an multithreaded environment.
   */
  @Test
  public void uniqueIdThreaded() throws InterruptedException, ExecutionException {

    final ExecutorService s = Executors.newWorkStealingPool();

    final List<Callable<String>> threads = new ArrayList<>();

    for (int i = 0; i < THREADS; i++) {
      threads.add(new Callable<String>() {
        @Override
        public String call() throws Exception {
          return idGenerator.getId();
        }
      });
    }

    final List<Future<String>> futures = s.invokeAll(threads);

    final List<String> results = new ArrayList<String>();

    for (final Future f : futures) {
      results.add((String) f.get());
    }

    final int distinctElements = (int) results.stream().distinct().count();

    assertEquals("Non-unique id generated", THREADS, distinctElements);

  }

}
