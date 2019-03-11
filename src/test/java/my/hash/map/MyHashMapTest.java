package my.hash.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyHashMapTest {

  private MyHashMap mymap = new MyHashMap();

  @Before
  public void setUp() {
  }

  @Test
  public void shouldPutAndGetEntryToHash() {
    mymap.put(1, 123);
    Assert.assertEquals(123, mymap.get(1));

    mymap.put(3, 303);
    Assert.assertEquals(303, mymap.get(3));

    mymap.put(4, 567);
    Assert.assertEquals(567, mymap.get(4));
  }

  @Test
  public void shouldPutWithCollisionAndGetEntryToHash() {
    mymap.put(1, 123);
    Assert.assertEquals(123, mymap.get(1));

    mymap.put(3, 303);
    Assert.assertEquals(303, mymap.get(3));

    // collision with key 1
    mymap.put(10001, 404);
    Assert.assertEquals(404, mymap.get(10001));

    // collision with key 3
    mymap.put(10003, 282);
    Assert.assertEquals(282, mymap.get(10003));
  }

  @Test
  public void shouldPutAndDelete(){
    mymap.put(1, 123);
    Assert.assertEquals(123, mymap.get(1));

    mymap.remove(1);
    Assert.assertEquals(-1, mymap.get(1));
  }

  @Test
  public void shouldPutAndDeleteInTheMiddleOfTheBucket(){
    mymap.put(1, 123);
    Assert.assertEquals(123, mymap.get(1));

    mymap.put(10001, 404);
    Assert.assertEquals(404, mymap.get(10001));

    mymap.remove(10001);
    Assert.assertEquals(-1, mymap.get(10001));
  }

  @Test
  public void integrationTest(){
    mymap.put(1, 123);
    Assert.assertEquals(123, mymap.get(1));

    mymap.put(10001, 404);
    Assert.assertEquals(404, mymap.get(10001));

    mymap.put(100001, 404);
    Assert.assertEquals(404, mymap.get(100001));

    mymap.remove(10001);
    Assert.assertEquals(-1, mymap.get(10001));

    mymap.put(10001, 404);
    Assert.assertEquals(404, mymap.get(10001));

    mymap.remove(10001);
    Assert.assertEquals(-1, mymap.get(10001));
  }

}
