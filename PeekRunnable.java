import java.util.Queue;

public class PeekRunnable<E> implements Runnable {

  private Queue<E> queue;

  public PeekRunnable(Queue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    System.out.println("[START PEEK]: " + Thread.currentThread().getName());
    E peekedValue = offerToQueue();
    System.out.println("Peaked value: " + peekedValue);
    System.out.println("[END PEEK]: " + Thread.currentThread().getName());
  }

  private E offerToQueue() {
    return queue.peek();
  }
}