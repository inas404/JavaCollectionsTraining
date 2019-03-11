package my.hash.map;

class MyHashMap<E> {

  int MY_MAP_SZ = 10000;
  Node<E>[] arr;

  /**
   * Initialize your data structure here.
   */
  public MyHashMap() {
    arr = new Node[MY_MAP_SZ];
  }

  /**
   * value will always be non-negative.
   */
  public void put(Integer key, E value) {
    int keyHashCode = key.hashCode();
    int index = getIndex(keyHashCode);
    Node<E> newNode = new Node(key, value, keyHashCode, null);
    if (arr[index] == null) {
      arr[index] = newNode;
    } else {
      Node<E> current = arr[index];
      Node<E> prev = current;
      while (current != null) {
        if (current.hash == keyHashCode) {
          current.value = value; //update value
          return;
        }
        prev = current;
        current = current.next;
      }
      prev.next = newNode; // add to end of linkedlist of arr[index]
    }
  }

  private int getIndex(int keyHashCode) {
    return keyHashCode % MY_MAP_SZ;
  }

  /**
   * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
   */
  public E get(Integer key) {
    int keyHashCode = key.hashCode();
    int index = getIndex(keyHashCode);
    if (arr[index] == null) {
      return null;
    } else {
      Node<E> current = arr[index];
      while (current != null) {
        if (current.hash == keyHashCode) {
          return current.value;
        }
        current = current.next;
      }
      return null;
    }
  }

  /**
   * Removes the mapping of the specified value key if this map contains a mapping for the key
   */
  public void remove(Integer key) {
    int keyHashCode = key.hashCode();
    int index = getIndex(keyHashCode);
    if (arr[index] == null) {
      return;
    } else if (arr[index].hash == keyHashCode) {
      arr[index] = arr[index].next;
    } else {
      Node<E> current = arr[index];
      Node<E> prev = current;
      while (current != null) {
        if (current.hash == keyHashCode) {
          prev.next = current.next;
          current.next = null;
          return;
        }
        prev = current;
        current = current.next;
      }
    }
  }

  private class Node<E> {

    Integer key;
    E value;
    int hash;
    Node<E> next;

    public Node(int key, E value, int hash, Node next) {
      this.key = key;
      this.value = value;
      this.hash = hash;
      this.next = next;
    }
  }
}

/**
 * Your MyHashMap object will be instantiated and called as such: MyHashMap obj = new MyHashMap(); obj.put(key,value);
 * int param_2 = obj.get(key); obj.remove(key);
 */