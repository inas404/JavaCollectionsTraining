package my.hash.set;

import java.util.AbstractSet;
import java.util.Iterator;
import my.hash.map.MyHashMap;

public class MyHashSet<E> extends AbstractSet {

  private MyHashMap<E, Boolean> map;

  public MyHashSet() {
    map = new MyHashMap<>();
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return map.get((E) o) == null ? false : true;
  }

  @Override
  public Iterator iterator() {
    return map.keySet().iterator();
  }

  @Override
  public boolean add(Object o) {
    map.put((E) o, true);
    return true;
  }

  @Override
  public boolean remove(Object o) {
    map.remove((E) o);
    return true;
  }
}
