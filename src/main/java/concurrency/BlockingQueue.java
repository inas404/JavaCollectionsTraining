package concurrency;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueue <E> {

  ArrayBlockingQueue<E> blockingQueue = new ArrayBlockingQueue<>(10);

  public void put(E s) {
    try {
      blockingQueue.put(s);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public E take() {
    try {
      return blockingQueue.take();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
  }
}
