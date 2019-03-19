package my.hash.map;

class MyHashMap<K, V> {

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

  public V get(K key) {
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

  public void remove(Integer key) {
    int keyHashCode = key.hashCode();
    int index = getIndex(keyHashCode);
    if (arr[index] == null) {
      return;
    } else if (arr[index].hash == keyHashCode) {
      arr[index] = arr[index].next;
      size--;
    } else {
      Node<K, V> current = arr[index];
      Node<K, V> prev = current;
      while (current != null) {
        if (current.hash == keyHashCode) {
          prev.next = current.next;
          current.next = null;
          size--;
          return;
        }
        prev = current;
        current = current.next;
      }
    }
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