package my.hash.map;

import java.util.AbstractMap;
import java.util.Set;

public class MyHashMap<K, V> extends AbstractMap<K, V> {

  private int capacity = 16;
  private int size;
  private float loadFactor;
  private float threshold;
  private Node<K, V>[] arr;

  public MyHashMap() {
    this(0.75f);
  }

  public MyHashMap(float loadFactor) {
    arr = new Node[capacity];
    this.loadFactor = loadFactor;
    this.threshold = capacity * loadFactor;
  }

  @Override
  public V put(K key, V value) {
    int keyHashCode = key.hashCode();
    int index = getIndex(keyHashCode);
    Node<K, V> newNode = new Node(key, value, keyHashCode, null);
    if (arr[index] == null) {
      autoResize();
      arr[index] = newNode;
      size++;
    } else {
      Node<K, V> current = arr[index];
      Node<K, V> prev = current;
      while (current != null) {
        if (current.hash == keyHashCode) {
          current.value = value; //update value
          return value;
        }
        prev = current;
        current = current.next;
      }
      autoResize();
      prev.next = newNode; // add to end of linkedlist of arr[index]
      size++;
    }
    return value;
  }

  private int getIndex(int keyHashCode) {
    return keyHashCode % capacity;
  }

  @Override
  public V get(Object key) {
    int keyHashCode = key.hashCode();
    int index = getIndex(keyHashCode);
    if (arr[index] == null) {
      return null;
    } else {
      Node<K, V> current = arr[index];
      while (current != null) {
        if (current.hash == keyHashCode) {
          return current.value;
        }
        current = current.next;
      }
      return null;
    }
  }

  @Override
  public V remove(Object key) {
    int keyHashCode = key.hashCode();
    int index = getIndex(keyHashCode);
    if (arr[index] == null) {
      return null;
    } else if (arr[index].hash == keyHashCode) {
      arr[index] = arr[index].next;
      size--;
      return (V) key;
    } else {
      Node<K, V> current = arr[index];
      Node<K, V> prev = current;
      while (current != null) {
        if (current.hash == keyHashCode) {
          prev.next = current.next;
          current.next = null;
          size--;
          return (V) key;
        }
        prev = current;
        current = current.next;
      }
    }
    return null;
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return null;
  }

  public int size() {
    return size;
  }

  private void autoResize() {
    if (size > threshold) {
      resize(capacity * 2);
    }
  }

  private void resize(int newCapacity) {
    capacity = newCapacity;
    this.threshold = capacity * loadFactor;
    Node<K, V>[] resizedArr = new Node[newCapacity];
    for (Node<K, V> entry : arr) {
      if (entry == null) {
        continue;
      }
      resizedArr[getIndex(entry.key.hashCode())] = entry;
    }
    arr = resizedArr;
  }

  @Override
  public boolean isEmpty() {
    return size == 0 ? true : false;
  }

  @Override
  public boolean containsKey(Object key) {
    return get(key) == null? false: true;
  }

  private class Node<K, V> {

    K key;
    V value;
    int hash;
    Node<K, V> next;

    public Node(K key, V value, int hash, Node next) {
      this.key = key;
      this.value = value;
      this.hash = hash;
      this.next = next;
    }
  }
}