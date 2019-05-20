package concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<E> {

  Queue<E> myBlockingQueue = new LinkedList<>();

  ReentrantLock lock = new ReentrantLock(true);
  Condition isNotEmpty = lock.newCondition();
  Condition isNotFull = lock.newCondition();

  public void put(E item) {
    lock.lock();
    try {
      while (myBlockingQueue.size() == 10) {
        System.out.println(Thread.currentThread().getName() + " : Queue is full, waiting");
        isNotFull.await(); // Producer thread blocks if full
      }
      myBlockingQueue.add(item);
      System.out.println(Thread.currentThread().getName() + " : Signalling that Queue is no more empty now");
      isNotEmpty.signalAll();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public E take() {
    lock.lock();
    try {
      while (myBlockingQueue.size() == 0) {
        System.out.println(Thread.currentThread().getName() + " : Queue is empty, waiting");
        isNotEmpty.await(); // Consumer thread blocks if empty
      }
      System.out.println(Thread.currentThread().getName() + " : Signalling that queue may be empty now");
      isNotFull.signalAll(); // Consumer signals notFull to Producer
      return myBlockingQueue.remove();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    return null;
  }
}
