package concurrency;

import java.util.LinkedList;
import java.util.Queue;

public class MyOtherBlockingQueue<E> {

  Queue<E> myBlockingQueue = new LinkedList<>();

  Object sharedObj = new Object();

  public void put(E item) {
    synchronized (sharedObj) {
      while (myBlockingQueue.size() == 10) {
        System.out.println(Thread.currentThread().getName() + " : Queue is full, waiting");
        try {
          sharedObj.wait(); // Producer thread blocks if full, waits for notFull signal
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      myBlockingQueue.add(item);
      System.out.println(Thread.currentThread().getName() + " : Signalling that Queue is no more empty now");
      sharedObj.notifyAll();
    }
  }

  public E take() {
    synchronized (sharedObj) {
      while (myBlockingQueue.size() == 0) {
        System.out.println(Thread.currentThread().getName() + " : Queue is empty, waiting");
        try {
          sharedObj.wait(); // Consumer thread blocks if empty, waits for not empty signal
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println(Thread.currentThread().getName() + " : Signalling that queue may be empty now");
      sharedObj.notifyAll(); // Consumer signals notFull to Producer
      return myBlockingQueue.remove();
    }
  }
}
