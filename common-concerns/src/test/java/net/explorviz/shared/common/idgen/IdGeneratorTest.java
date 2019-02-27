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

public class IdGeneratorTest {


  IdGenerator idGenerator;

  @Before
  public void setUp() throws Exception {
    idGenerator = new IdGenerator("localhost");
  }

  @Test
  public void getUniqueIdAsLong() {
    long id = idGenerator.getUniqueIdAsLong();
    long nextId = idGenerator.getUniqueIdAsLong();

    assertEquals(nextId, id + 1);

  }

  @Test
  public void getUniqueIdAsString() {
    String id = idGenerator.getUniqueIdAsString();

    // No exception should be thrown
    Long.parseLong(id);

  }


  @Test
  public void uniqueIdThreaded() throws InterruptedException, ExecutionException {

    ExecutorService s = Executors.newWorkStealingPool();

    List<Callable<Long>> threads = new ArrayList<>();

    int threadCount = 100;

    for (int i = 0; i < threadCount; i++) {
      threads.add(new Callable<Long>() {
        @Override
        public Long call() throws Exception {
          return idGenerator.getUniqueIdAsLong();
        }
      });
    }

    List<Future<Long>> futures = s.invokeAll(threads);

    List<Long> results = new ArrayList<Long>();

    for (Future f : futures) {
      results.add((Long) f.get());
    }

    System.out.println(results.get(0));
    int distinctElements = (int) results.stream().distinct().count();

    assertEquals(threadCount, distinctElements);

  }

}
