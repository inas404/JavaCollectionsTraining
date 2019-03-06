package most.recent.queue.helper;

import java.util.Queue;

public class OfferRunnable<E> implements Runnable {

  private final E item;
  private Queue<E> queue;

  public OfferRunnable(Queue queue, E item) {
    this.queue = queue;
    this.item = item;
  }

  @Override
  public void run() {
    System.out.println("[START OFFER]: " + Thread.currentThread().getName() + " size = " + queue.size());
    offerToQueue();
    System.out.println("[END OFFER]: " + Thread.currentThread().getName() + " size = " + queue.size());
  }

  private void offerToQueue() {
    queue.offer(item);
  }
}
