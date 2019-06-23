package word.counter;

import static java.util.stream.Collectors.*;

import com.sun.tools.javac.util.Pair;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * write tool - word counter Input: filePath to single text file, or a directory tree containing text files. Output:
 * statistics - word counts to find the top N most used words, the bottom N least used words and the total word count.
 */
@NoArgsConstructor
public class WordCounter {

  private Map<String, Integer> wordCountMap = new HashMap<>();
  private int totalWordsCount = 0;

  private void countWordsInAllFiles(Path path) throws IOException, InterruptedException, ExecutionException {
    List<Callable<Pair<Integer, Map<String, Integer>>>> todo = new ArrayList<>();

    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    Files.walk(path).filter(f -> !Files.isDirectory(f)).forEach(f ->
        todo.add(new CountWordsCallable(f.toAbsolutePath().toString())));

    for (Future<Pair<Integer, Map<String, Integer>>> callable : executorService.invokeAll(todo)) {
      Pair<Integer, Map<String, Integer>> threadResult = callable.get();
      totalWordsCount += threadResult.fst;
      Map<String, Integer> partialWordCountMap = threadResult.snd;
      partialWordCountMap.forEach((k, v) -> wordCountMap.merge(k, v, (v1, v2) -> v1 + v2));
    }
    executorService.shutdown();
  }

  @AllArgsConstructor
  private class CountWordsCallable implements Callable<Pair<Integer, Map<String, Integer>>> {

    String filePath;

    @Override
    public Pair<Integer, Map<String, Integer>> call() throws Exception {
      Integer totalWordsCount = 0;
      Map<String, Integer> wordCountMap = new HashMap<>();
      try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
        while (scanner.hasNext()) {
          String word = scanner.next();
          wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
          totalWordsCount++;
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      return new Pair<>(totalWordsCount, wordCountMap);
    }
  }

  private void countWordsInFile(String path) {
    try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(path)))) {
      while (scanner.hasNext()) {
        String word = scanner.next();
        wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
        totalWordsCount++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void doStats(int n) {
    System.out.println("Total number of words is " + totalWordsCount);

    // sort by word occurrence
    List<Entry<String, Integer>> sortedWordsByOccurrence = wordCountMap.entrySet().stream()
        .sorted(Comparator.comparing(Entry::getValue))
        .collect(Collectors.toList());

    List<Entry<String, Integer>> mostUsedNWords, leastUsedNWords;
    leastUsedNWords = sortedWordsByOccurrence.stream().limit(n).collect(toList());
    mostUsedNWords = sortedWordsByOccurrence.stream().skip(sortedWordsByOccurrence.size() - n).collect(toList());

    System.out.println("Most used words are " + mostUsedNWords);
    System.out.println("Least used words are " + leastUsedNWords);
  }

  public void getStats(String strPath, int n) throws IOException, InterruptedException, ExecutionException {
    Path path = Paths.get(strPath);
    if (!Files.exists(path)) {
      throw new FileNotFoundException();
    }
    if (Files.isDirectory(path)) {
      countWordsInAllFiles(path);
    } else {
      countWordsInFile(path.toAbsolutePath().toString());
    }
    doStats(n);
    wordCountMap.clear();
    totalWordsCount = 0;
  }

  // use tree map TreeMap won't help here as it sorts by key not by value

  public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
    WordCounter wc = new WordCounter();
    wc.getStats("resources/word.counter", 2);
  }
}