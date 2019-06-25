package word.counter;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.junit.Assert;
import org.junit.Test;
import word.counter.WordCounter.StatsHolder;

public class WordCounterTest {

  @Test
  public void testOneFile() throws InterruptedException, ExecutionException, IOException {
    WordCounter wc = new WordCounter();
    StatsHolder stats = wc.getStats("resources/word.counter/input.txt", 2);
    Assert.assertEquals(177, stats.getTotalWordsCount());
    System.out.println("Most used words are " + stats.getMostUsedNWords());
    System.out.println("Least used words are " + stats.getLeastUsedNWords());
  }

  @Test
  public void testWithNonExistingFile() {
    WordCounter wc = new WordCounter();
    StatsHolder stats = wc.getStats("resources/word.counter", 2);
  }

  @Test
  public void testWithNonExistingDir() {
    WordCounter wc = new WordCounter();
    StatsHolder stats = wc.getStats("resources/word.counter", 2);
  }

  @Test
  public void testDirectoryWith2Files() {

  }

  @Test
  public void testTotalCount() {

  }

  @Test
  public void testLeastUsedWordsCount() {

  }

  @Test
  public void testMostUsedWordsCount() {

  }

  @Test
  public void testGetStatsMultipleTimesWithSameDir() {

  }

  @Test
  public void testGetStatsMultipleTimesWithDifferentDir() {

  }
}
