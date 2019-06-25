package word.counter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.junit.Assert;
import org.junit.Test;
import word.counter.WordCounter.StatsHolder;

public class WordCounterTest {

  @Test
  public void testOneFileStats() throws InterruptedException, ExecutionException, IOException {
    WordCounter wc = new WordCounter();
    StatsHolder stats = wc.getStats("resources/word.counter/input.txt", 2);
    Assert.assertEquals(88, stats.getTotalWordsCount());
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("the=4"));
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("to=4"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("but=1"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("JVM=1"));
  }

  @Test(expected = FileNotFoundException.class)
  public void testWithNonExistingFile() throws InterruptedException, ExecutionException, IOException {
    WordCounter wc = new WordCounter();
    wc.getStats("resources/word.counter1", 2);
  }

  @Test(expected = FileNotFoundException.class)
  public void testWithNonExistingDir() throws InterruptedException, ExecutionException, IOException {
    WordCounter wc = new WordCounter();
    wc.getStats("resources/word.counter2", 2);
  }

  @Test
  public void testStatsOfDirectoryWith2Files() throws InterruptedException, ExecutionException, IOException {
    WordCounter wc = new WordCounter();
    StatsHolder stats = wc.getStats("resources/word.counter", 2);
    Assert.assertEquals(177, stats.getTotalWordsCount());
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("the=8"));
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("to=8"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("very=2"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("JVM=2"));
  }

  @Test
  public void testGetStatsMultipleTimesWithSameDir() throws InterruptedException, ExecutionException, IOException {
    WordCounter wc = new WordCounter();
    StatsHolder stats = wc.getStats("resources/word.counter", 2);
    Assert.assertEquals(177, stats.getTotalWordsCount());
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("the=8"));
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("to=8"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("very=2"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("JVM=2"));

    stats = wc.getStats("resources/word.counter", 2);
    Assert.assertEquals(177, stats.getTotalWordsCount());
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("the=8"));
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("to=8"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("very=2"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("JVM=2"));
  }

  @Test
  public void testGetStatsMultipleTimesWithDifferentDir() throws InterruptedException, ExecutionException, IOException {
    WordCounter wc = new WordCounter();
    StatsHolder stats = wc.getStats("resources/word.counter", 2);
    Assert.assertEquals(177, stats.getTotalWordsCount());
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("the=8"));
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("to=8"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("very=2"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("JVM=2"));

    stats = wc.getStats("resources/word.counter/input.txt", 2);
    Assert.assertEquals(88, stats.getTotalWordsCount());
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("the=4"));
    Assert.assertTrue(stats.getMostUsedNWords().toString().contains("to=4"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("but=1"));
    Assert.assertTrue(stats.getLeastUsedNWords().toString().contains("JVM=1"));
  }
}
