package io;

import org.junit.Assert;
import org.junit.Test;

public class CountCharTest {

  private final CountChar cc = new CountChar("/resources/io/count_char_file.txt");

  @Test
  public void countLetterShouldWork(){
    Assert.assertEquals(6, cc.count('a'));
  }

  @Test
  public void countLetterCaseSensitiveShouldWork(){
    Assert.assertEquals(4, cc.countCaseSensitive('c'));
    Assert.assertEquals(1, cc.countCaseSensitive('C'));
  }


  @Test
  public void countNumberShouldWork(){
    Assert.assertEquals(1, cc.count('2'));
  }

  @Test
  public void countSpecialCharShouldWork(){
    Assert.assertEquals(1, cc.count('@'));
  }

  @Test
  public void countSpaceShouldWork(){
    Assert.assertEquals(12, cc.count(' '));
  }

  @Test
  public void countCharThatDoesNotExistShouldEqualsZero(){
    Assert.assertEquals(0, cc.count('#'));
    Assert.assertEquals(0, cc.count('9'));
    Assert.assertEquals(0, cc.count('A'));
    Assert.assertEquals(0, cc.count('y'));
  }
}
