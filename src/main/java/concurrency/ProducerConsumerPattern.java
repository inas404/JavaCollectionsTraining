package concurrency;

import java.util.Random;
import lombok.Getter;

@Getter
public class ProducerConsumerPattern {

  //  uncomment any of 3 ways of using blocking queue
  //  private BlockingQueue<String> queue = new BlockingQueue();
  //  private MyBlockingQueue<String> queue = new MyBlockingQueue();
  private MyOtherBlockingQueue<String> queue = new MyOtherBlockingQueue();

  @Getter
  private Producer producer = new Producer();
  @Getter
  private Consumer consumer = new Consumer();

  public class Producer implements Runnable {

    @Override
    public void run() {
      String importantInfo[] = {
          "Mares eat oats",
          "Does eat oats",
          "Little lambs eat ivy",
          "A kid will eat ivy too"
      };
      Random random = new Random();

      for (int i = 0; i < importantInfo.length; i++) {
        queue.put(importantInfo[i]);
        try {
          Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
        }
      }
      queue.put("DONE");
    }
  }

  public class Consumer implements Runnable {

    @Override
    public void run() {
      Random random = new Random();
      for (String message = queue.take(); !message.equals("DONE"); message = queue.take()) {
        System.out.format("MESSAGE RECEIVED: %s%n", message);
        try {
          Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
        }
      }
    }
  }

  public static void main(String[] args) {
    ProducerConsumerPattern producerConsumer = new ProducerConsumerPattern();

    (new Thread(producerConsumer.getProducer(), "producer 1")).start();

    (new Thread(producerConsumer.getConsumer(), "consumer 1")).start();
  }
}


