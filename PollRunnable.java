import java.util.Queue;

public class PollRunnable<E> implements Runnable {

  private Queue<E> queue;

  public PollRunnable(Queue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    System.out.println("[START PEEK]: " + Thread.currentThread().getName());
    E polledValue = offerToQueue();
    System.out.println("Polled value: " + polledValue);
    System.out.println("[END PEEK]: " + Thread.currentThread().getName());
  }

  private E offerToQueue() {
    return queue.poll();
  }
}