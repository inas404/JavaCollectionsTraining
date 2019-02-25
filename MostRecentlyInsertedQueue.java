import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class MostRecentlyInsertedQueue<E> extends AbstractQueue {

  private final Integer MAX_CAPACITY;

  private int size;
  private int tail;
  private int head;
  private Object[] elements;

  public MostRecentlyInsertedQueue(Integer capacity) {
    this.MAX_CAPACITY = capacity;
    elements = new Object[MAX_CAPACITY];
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
    System.out.print("queue.size(): "+ this.size() + ", contents (head -> tail): [");
    for (Object element : elements) {
      if(element!=null)System.out.print(element + " ");
      else break;
    }
    System.out.println("]");
  }

  @Override
  public Iterator iterator() {
    return null;
  }

  @Override
  public int size() {
    return tail;
  }

  @Override
  public boolean add(Object o) {
    if (o == null) {
      throw new NullPointerException();
    }
    if (tail == MAX_CAPACITY) {
      System.arraycopy(elements, 1, elements, 0, MAX_CAPACITY-1);
      tail--;
    }
    elements[tail++] = o;
    traverseFromHeadToTail();
    return true;
  }

  @Override
  public boolean offer(Object o) {
    try {
      add(o);
      return true;
    } catch (NullPointerException ex) {
      return false;
    }
  }

  @Override
  public Object remove() {
    if (elements[head] == null) {
      throw new NullPointerException();
    }
    Object temp = elements[head];
    System.arraycopy(elements, 1, elements, 0, MAX_CAPACITY-1);
    elements[--tail]=null;
    traverseFromHeadToTail();
    System.out.println("poll = "+temp);
    return temp;
  }

  @Override
  public Object poll() {
    try{
      return remove();
    }catch (NullPointerException ex){
      return null;
    }
  }

  @Override
  public Object peek() {
    return null;
  }
}
