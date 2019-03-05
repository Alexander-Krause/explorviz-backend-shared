package net.explorviz.shared.common.idgen;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Before;
import org.junit.Test;

public class ServiceIdGeneratorTest {


  ServiceIdGenerator idGenerator;

  @Before
  public void setUp() throws Exception {
    idGenerator = new ServiceIdGenerator("localhost");
  }



  @Test
  public void getServiceId() {
    String id = idGenerator.getId();

    // No exception should be thrown
    Long.parseLong(id);

  }


  @Test
  public void uniqueIdThreaded() throws InterruptedException, ExecutionException {

    ExecutorService s = Executors.newWorkStealingPool();

    List<Callable<String>> threads = new ArrayList<>();

    int threadCount = 100;

    for (int i = 0; i < threadCount; i++) {
      threads.add(new Callable<String>() {
        @Override
        public String call() throws Exception {
          return idGenerator.getId();
        }
      });
    }

    List<Future<String>> futures = s.invokeAll(threads);

    List<String> results = new ArrayList<String>();

    for (Future f : futures) {
      results.add((String) f.get());
    }

    int distinctElements = (int) results.stream().distinct().count();

    assertEquals(threadCount, distinctElements);

  }

}
