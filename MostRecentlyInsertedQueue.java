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

/*
//*******************************************************************
// Welcome to CompileJava!
// If you experience any issues, please contact us ('More Info')  -->
// Also, sorry that the "Paste" feature no longer works! GitHub broke
// this (so we'll switch to a new provider): https://blog.github.com\
// /2018-02-18-deprecation-notice-removing-anonymous-gist-creation/
//*******************************************************************

import java.lang.Math; // headers MUST be above the first class
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

// one class needs to have a main() method
public class HelloWorld
{
  // arguments are passed using the text field below this editor
  public static void main(String[] args)
  {
    OtherClass myObject = new OtherClass("Hello World!");
    System.out.print(myObject);
  }
}

// you can add other public classes to this editor in any order
public class OtherClass
{
  private String message;
  private boolean answer = false;
  public OtherClass(String input)
  {
    message = "Why, " + input + " Isn't this something?";
  }
  public String toString()
  {
    return message;
  }
}

public class MostRecentlyInsertedQueue<E> extends AbstractQueue<E> {

  private final Integer MAX_CAPACITY;

  private Node<E> tail;
  private Node<E> head;
  private E[] elements;

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
    System.out.print("queue.size(): "+ this.size() + ", contents (head -> tail): [");
    while(traversalNode != null){
		System.out.print(traversalNode + " ");
      traversalNode = traversalNode.next;
    }
    System.out.print("queue.size(): "+ this.size() + ", contents (head -> tail): [");
    for (E element : elements) {
      if(element!=null)System.out.print(element + " ");
      else break;
    }
    System.out.println("]");
  }

  @Override
  public Iterator<E> iterator() {
    return null;
  }

  @Override
  public int size() {
    return tail;
  }

  @Override
  public boolean add(E o) {
    if (o == null) {
      throw new NullPointerException();
    }
    
    Node newNode = new Node(o,null,tail);
    tail = newNode;

    
    if (tail == MAX_CAPACITY) {
      System.arraycopy(elements, 1, elements, 0, MAX_CAPACITY-1);
      tail--;
    }
    elements[tail++] = o;
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
    if (elements[head] == null) {
      throw new NullPointerException();
    }
    E temp = elements[head];
    System.arraycopy(elements, 1, elements, 0, MAX_CAPACITY-1);
    elements[--tail]=null;
    traverseFromHeadToTail();
    System.out.println("poll = "+temp);
    return temp;
  }

  @Override
  public E poll() {
    try{
      return remove();
    }catch (NullPointerException ex){
      return null;
    }
  }

  @Override
  public E peek() {
    return null;
  }
  
  private class Node<E>{
  	E value;
    Node next;
    Node prev;
    
    Node(E value, Node next, Node prev){
      this.value = value;
      this.next = next;
      this.prev = prev;
    }
     Node(Node<E> node){
      this.value = node.value;
      this.next = node.next;
      this.prev = node.prev;
    }
  }
}

*/
