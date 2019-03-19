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
  public void shouldPutAndGetEntry() {
    mymap.put(1, 123);
    Assert.assertEquals(123, mymap.get(1));

    mymap.put(3, 303);
    Assert.assertEquals(303, mymap.get(3));

    mymap.put(4, 567);
    Assert.assertEquals(567, mymap.get(4));
  }

  @Test
  public void shouldPutWithCollisionAndGetEntry() {
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
    Assert.assertEquals(null, mymap.get(1));
  }

  @Test
  public void shouldUpdate(){
    mymap.put(1, 123);
    Assert.assertEquals(123, mymap.get(1));

    mymap.put(1, 404);
    Assert.assertEquals(404, mymap.get(1));
  }

  @Test
  public void shouldPutAndDeleteInTheMiddleOfTheBucket(){
    mymap.put(1, 123);
    Assert.assertEquals(123, mymap.get(1));

    mymap.put(10001, 404);
    Assert.assertEquals(404, mymap.get(10001));

    mymap.remove(10001);
    Assert.assertEquals(null, mymap.get(10001));
  }

  @Test
  public void shouldPutAndGetAndRemoveEntriesOnBoundaries(){
    // key = 0
    mymap.put(0, 0);
    Assert.assertEquals(0, mymap.get(0));

    mymap.remove(0);
    Assert.assertEquals(null, mymap.get(0));

    mymap.put(0, 1000000);
    Assert.assertEquals(1000000, mymap.get(0));

    mymap.remove(0);
    Assert.assertEquals(null, mymap.get(0));

    // key = 1000000
    mymap.put(1000000, 0);
    Assert.assertEquals(0, mymap.get(1000000));

    mymap.remove(1000000);
    Assert.assertEquals(null, mymap.get(1000000));

    mymap.put(1000000, 1000000);
    Assert.assertEquals(1000000, mymap.get(1000000));

    mymap.remove(1000000);
    Assert.assertEquals(null, mymap.get(1000000));
   }

  @Test
  public void integrationTest(){
    mymap.put(1, 123);
    Assert.assertEquals(123, mymap.get(1));

    mymap.put(10001, 456);
    Assert.assertEquals(456, mymap.get(10001));

    mymap.put(100001, 404);
    Assert.assertEquals(404, mymap.get(100001));

    mymap.remove(10001);
    Assert.assertEquals(null, mymap.get(10001));
    Assert.assertEquals(123, mymap.get(1));
    Assert.assertEquals(404, mymap.get(100001));

    mymap.remove(1);
    Assert.assertEquals(null, mymap.get(1));
    Assert.assertEquals(null, mymap.get(10001));
    Assert.assertEquals(404, mymap.get(100001));

    mymap.put(10001, 678);
    Assert.assertEquals(678, mymap.get(10001));

    mymap.remove(10001);
    Assert.assertEquals(null, mymap.get(10001));
  }

  @Test
  public void mapShouldPutAndGetAnyType(){
    mymap.put(1, "str1");
    Assert.assertEquals("str1", mymap.get(1));

    mymap.put(1, "str2");
    Assert.assertEquals("str2", mymap.get(1));

    mymap.put(1, "str3");
    Assert.assertEquals("str3", mymap.get(1));

    mymap.put("k", "v");
    Assert.assertEquals("v", mymap.get("k"));

    mymap.put('k', "v2");
    Assert.assertEquals("v2", mymap.get("k"));
  }

  @Test
  public void shouldPutAndDeleteAnyType(){
    mymap.put(1, "str1");
    Assert.assertEquals("str1", mymap.get(1));

    mymap.remove(1);
    Assert.assertEquals(null, mymap.get(1));
  }

  @Test
  public void shouldUpdateAnyType(){
    mymap.put(1, "str1");
    Assert.assertEquals("str1", mymap.get(1));

    mymap.put(1, "str2");
    Assert.assertEquals("str2", mymap.get(1));
  }

  @Test
  public void myHashMapShouldSupportResizing(){
    mymap.put(101, 1);
    Assert.assertEquals(1, mymap.get(101));

    mymap.put(102, 2);
    Assert.assertEquals(2, mymap.get(102));

    mymap.resize(1000);
    Assert.assertEquals(1, mymap.get(101));
    Assert.assertEquals(2, mymap.get(102));
  }


}
