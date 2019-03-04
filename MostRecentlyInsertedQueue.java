import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class MostRecentlyInsertedQueue<E> extends AbstractQueue<E> {


  /* TODO
   * implement LL
   * cover with tests
   * implement missing methods
   * use generics*/
  private final Integer MAX_CAPACITY;

  private Node<E> tail;
  private Node<E> head;

  public MostRecentlyInsertedQueue(Integer capacity) {
    this.MAX_CAPACITY = capacity;
  }

  public static void main(String[] args) throws InterruptedException {
    Queue<Integer> queue = new MostRecentlyInsertedQueue<Integer>(4);
// queue.size(): 0, contents (head -> tail): [ ]
    queue.offer(1); // queue.size(): 1, contents (head -> tail): [ 1 ]
    queue.offer(2); // queue.size(): 2, contents (head -> tail): [ 1, 2 ]
    queue.offer(3); // queue.size(): 3, contents (head -> tail): [ 1, 2, 3 ]
    queue.offer(4); // queue.size(): 3, contents (head -> tail): [ 2, 3, 4 ]
    queue.offer(5); // queue.size(): 3, contents (head -> tail): [ 3, 4, 5 ]
    int poll1 = queue.poll(); // queue.size(): 2, contents (head -> tail): [ 4, 5 ], poll1 = 3
    int poll2 = queue.poll(); // queue.size(): 1, contents (head -> tail): [ 5 ], poll2 = 4
    queue.clear(); // queue.size(): 0, contents (head -> tail): [ ]

  }

  public void traverseFromHeadToTail() {
    Node<E> traversalNode = new Node<E>(head);
    System.out.print("queue.size(): " + this.tail + ", contents (head -> tail): [");
    while (traversalNode != null) {
      System.out.print(traversalNode.value + " ");
      traversalNode = traversalNode.next;
    }
    System.out.println("]");
  }

  @Override
  public Iterator iterator() {
    return null;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean add(E o) {
    if (o == null) {
      throw new NullPointerException();
    }
    if (tail == null) {
      tail = new Node<E>(o, null, null);
      head = tail;
    } else {
      Node<E> temp = tail;
      tail.next = new Node<E>(o, null, temp.next);
    }
    traverseFromHeadToTail();
    return true;
  }

  @Override
  public boolean offer(E o) {
    try {
      add(o);
      return true;
    } catch (NullPointerException ex) {
      return false;
    }
  }

  @Override
  public E remove() {
    if (head == null) {
      throw new NullPointerException();
    }
    head = head.next;

    traverseFromHeadToTail();
    System.out.println("poll = " + head.value);
    return head.value;
  }

  @Override
  public E poll() {
    try {
      return remove();
    } catch (NullPointerException ex) {
      return null;
    }
  }

  @Override
  public E peek() {
    return null;
  }

  private class Node<E> {

    private E value;
    private Node<E> next;
    private Node<E> prev;
//    private int pos;
//    private int sz = 0;

    Node(E value, Node next, Node prev) {
      this.value = value;
      this.next = next;
      this.prev = prev;
//      this.pos = sz++;
    }

    Node(Node<E> node) {
      this.value = node.value;
      this.next = node.next;
      this.prev = node.prev;
//      this.pos = sz++;
    }
  }

}
