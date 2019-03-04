import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConcurrentMostRecentlyInsertedQueue<E> extends AbstractQueue<E> {

  private int capacity;
  private int size;
  private Node<E> head;
  private Node<E> tail;
  private Object mutex = new Object();

  public ConcurrentMostRecentlyInsertedQueue(int capacity) {
    this.capacity = capacity;
    this.size = 0;
  }

  @Override
  public Iterator<E> iterator() {
    return new ConcurrentMostRecentlyInsertedQueueIterator();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean offer(E e) {
    if (e == null) {
      throw new NullPointerException("Null element passed to offer method");
    }
    synchronized (mutex) {
      if (size == capacity) {
        poll();
      }
      Node<E> node = new Node<E>(e, null);
      if (head == null) {
        this.head = node;
        this.tail = node;
      } else if (head.next == null) {
        head.next = node;
        this.tail = node;
      } else {
        Node oldTail = this.tail;
        this.tail = node;
        oldTail.next = this.tail;
      }

      size++;
      return true;
    }
  }

  @Override
  public E poll() {
    if (head != null) {
      synchronized (mutex) {
        E val = head.val;
        head = head.next;
        size--;
        return val;
      }
    } else {
      return null;
    }
  }

  @Override
  public E peek() {
    if (head == null) {
      return null;
    }
    return head.val;
  }

  private static class Node<E> {

    private E val;
    private Node<E> next;

    public Node(E val, Node<E> next) {
      this.val = val;
      this.next = next;
    }
  }

  private class ConcurrentMostRecentlyInsertedQueueIterator implements Iterator<E> {

    private Node<E> current;

    public ConcurrentMostRecentlyInsertedQueueIterator() {
      this.current = new Node<E>(null, ConcurrentMostRecentlyInsertedQueue.this.head);
    }

    @Override
    public synchronized boolean hasNext() {
      return (current != null && current.next != null);
    }

    @Override
    public synchronized E next() {
      if (current != null && current.next != null) {
        E value = current.next.val;
        current = current.next;
        return value;
      } else {
        throw new NoSuchElementException("Queue is empty");
      }
    }
  }
}