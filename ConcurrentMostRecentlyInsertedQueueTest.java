import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.junit.Before;
import org.junit.Test;

public class ConcurrentMostRecentlyInsertedQueueTest {

  private Queue<Integer> queue;

  @Before
  public void init() {
    this.queue = new ConcurrentMostRecentlyInsertedQueue<>(3);
  }

  @Test(expected = NullPointerException.class)
  public void offerMethodShouldThrowNullPointerOnNullElement() {
    queue.offer(null);
  }

  @Test
  public void firstElementShouldBeAddedToTheQueue() {
    queue.offer(1);
    assertEquals(1, queue.size());
  }

  @Test
  public void multipleElementsShouldBeAddedToTheQueue() {
    queue.offer(1);
    queue.offer(2);
    assertEquals(2, queue.size());
    assertEquals(new Integer(1), queue.poll());
    assertEquals(new Integer(2), queue.poll());
  }

  @Test
  public void peekShouldReturnNullOnEmptyQueue() {
    Integer value = queue.peek();
    assertNull(value);
  }

  @Test
  public void peekShouldReturnHeadValue() {
    queue.offer(1);
    queue.offer(2);
    assertEquals(2, queue.size());
    Integer value = queue.peek();
    assertNotNull(value);
    assertEquals(new Integer(1), queue.peek());
    assertEquals(2, queue.size());
  }

  @Test
  public void multipleElementsShouldBeAddedToTheQueueAndBoundedToCapacitySize() {
    queue.offer(1);
    queue.offer(2);
    queue.offer(3);
    queue.offer(4);
    assertEquals(3, queue.size());
    assertEquals(new Integer(2), queue.poll());
    assertEquals(new Integer(3), queue.poll());
    assertEquals(new Integer(4), queue.poll());
  }


  @Test(expected = NoSuchElementException.class)
  public void queueShouldBeIterated() {
    queue.offer(1);
    queue.offer(2);
    queue.offer(3);
    Iterator<Integer> iterator = queue.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(1), iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(2), iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(3), iterator.next());
    assertFalse(iterator.hasNext());
    iterator.next();
  }

  @Test
  public void allFlowsShouldPass() {
    Queue<Integer> queue = new ConcurrentMostRecentlyInsertedQueue<>(3);
    assertEquals(0, queue.size());
    queue.offer(1);
    Iterator<Integer> iterator = queue.iterator();
    assertEquals(1, queue.size());
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(1), iterator.next());
    queue.offer(2);
    assertEquals(2, queue.size());
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(2), iterator.next());
    queue.offer(3);
    assertEquals(3, queue.size());
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(3), iterator.next());
    queue.offer(4);
    assertEquals(3, queue.size());
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(4), iterator.next());
    queue.offer(5);
    assertEquals(3, queue.size());
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(5), iterator.next());
    int poll1 = queue.poll();
    assertEquals(3, poll1);
    assertEquals(2, queue.size());
    iterator = queue.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(4), iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(5), iterator.next());
    int poll2 = queue.poll();
    assertEquals(4, poll2);
    iterator = queue.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(new Integer(5), iterator.next());
    queue.clear();
    assertEquals(0, queue.size());
  }

  @Test
  public void multipleThreadsAddingToTheQueue() throws InterruptedException {
    Thread th1 = new Thread(new OfferRunnable<Integer>(queue, 1));
    Thread th2 = new Thread(new OfferRunnable<Integer>(queue, 2));

    th1.start();
    th2.start();

    th1.join();
    th2.join();

    assertEquals(2, queue.size());
  }

  @Test
  public void multipleThreadsPeekingFromTheQueue() throws InterruptedException {
    queue.offer(1);
    queue.offer(2);
    queue.offer(3);

    Thread th1 = new Thread(new PeekRunnable<>(queue));
    Thread th2 = new Thread(new PeekRunnable<>(queue));

    th1.start();
    th2.start();

    th1.join();
    th2.join();

    // TODO: how to test return value from peeking?

    assertEquals(3, queue.size());
  }

  @Test
  public void multipleThreadsPollingFromTheQueue() throws InterruptedException {
    queue.offer(1);
    queue.offer(2);
    queue.offer(3);

    Thread th1 = new Thread(new PollRunnable<>(queue));
    Thread th2 = new Thread(new PollRunnable<>(queue));

    th1.start();
    th2.start();

    th1.join();
    th2.join();

    assertEquals(1, queue.size());
  }
}