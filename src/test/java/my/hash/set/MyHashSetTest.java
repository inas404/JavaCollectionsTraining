package my.hash.set;

import org.junit.Assert;
import org.junit.Test;

public class MyHashSetTest {

  private MyHashSet myset = new MyHashSet();

  @Test
  public void shouldInsert() {
    myset.add(1);
    Assert.assertEquals(1, myset.size());

    myset.add(5);
    Assert.assertEquals(2, myset.size());

    myset.add("str");
    Assert.assertEquals(3, myset.size());
  }

  @Test
  public void shouldInsertAndContains() {
    myset.add(1);
    Assert.assertTrue(myset.contains(1));
    Assert.assertEquals(1, myset.size());

    myset.add(5);
    Assert.assertTrue(myset.contains(5));
    Assert.assertEquals(2, myset.size());

    myset.add("str");
    Assert.assertTrue(myset.contains("str"));
    Assert.assertEquals(3, myset.size());
  }

  @Test
  public void shouldNotInsertDuplicates() {
    myset.add(1);
    Assert.assertTrue(myset.contains(1));
    Assert.assertEquals(1, myset.size());

    myset.add(1);
    Assert.assertTrue(myset.contains(1));
    Assert.assertEquals(1, myset.size());

    myset.add("str");
    Assert.assertTrue(myset.contains("str"));
    Assert.assertEquals(2, myset.size());

    myset.add("str");
    Assert.assertTrue(myset.contains("str"));
    Assert.assertEquals(2, myset.size());
  }

  @Test
  public void shouldRemove() {
    myset.add(1);
    Assert.assertTrue(myset.contains(1));
    Assert.assertEquals(1, myset.size());

    myset.remove(1);
    Assert.assertFalse(myset.contains(1));
    Assert.assertEquals(0, myset.size());
    Assert.assertTrue(myset.isEmpty());
  }
}
