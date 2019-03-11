package my.dummy.hash.map;

import java.util.ArrayList;
import java.util.List;

public class MyDummyHashMap {

  List<Integer> keys;
  List<Integer> values;

  /**
   * Initialize your data structure here.
   */
  public MyDummyHashMap() {
    keys = new ArrayList<Integer>();
    values = new ArrayList<Integer>();
  }

  /**
   * value will always be non-negative.
   */
  public void put(int key, int value) {
    if (keys.contains(key)) {
      assert values.size() > keys.indexOf(key) : "Sth wrong in put";
      values.set(keys.indexOf(key), value);
    } else {
      keys.add(key);
      values.add(value);
    }
  }

  /**
   * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
   */
  public int get(int key) {
    if (keys.contains(key)) {
      assert values.size() > keys.indexOf(key) : "Sth wrong in get";
      return values.get(keys.indexOf(key));
    }
    return -1;
  }

  /**
   * Removes the mapping of the specified value key if this map contains a mapping for the key
   */
  public void remove(int key) {
    if (keys.contains(key)) {
      values.remove(keys.indexOf(key));// remove by index
      keys.remove(new Integer(key));//remove by Object
    }
  }
}

/**
 * Your MyDummyHashMap object will be instantiated and called as such: MyDummyHashMap obj = new MyDummyHashMap(); obj.put(key,value);
 * int param_2 = obj.get(key); obj.remove(key);
 */