package word.counter;

import static java.util.Map.Entry.*;
import static java.util.stream.Collectors.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;

/**
 * write tool - word counter Input: filePath to single text file, or a directory tree containing text files. Output:
 * statistics - word counts to find the top N most used words, the bottom N least used words and the total word count.
 */
public class WordCounter {

  private Path path;
  private boolean isDirectory;
  private Map<String, Integer> mostUsedNWords, leastUsedNWords;
  private Map<String, Integer> wordCountMap = Collections.synchronizedMap(new HashMap<>());
  private int totalWordsCount = 0, n;

  WordCounter(String path, int n) throws FileNotFoundException {
    this.n = n;
    this.path = Paths.get(path);
    if (!Files.exists(this.path)) {
      throw new FileNotFoundException();
    }
    this.isDirectory = Files.isDirectory(this.path);
  }

  private void countWordsInAllFiles() throws IOException, InterruptedException {
    // TODO: create new thread for each file
    List<Callable<Object>> todo = new ArrayList<>();

    ExecutorService executorService = Executors.newCachedThreadPool();
    Files.walk(path).filter(f -> !Files.isDirectory(f)).forEach(f ->
        todo.add(Executors.callable(new cwRunnable(f.toAbsolutePath().toString()))));

    executorService.invokeAll(todo);
    executorService.shutdown();
  }

  @AllArgsConstructor
  private class cwRunnable implements Runnable {

    String filePath;

    @Override
    public void run() {
      try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
        while (scanner.hasNext()) {
          String word = scanner.next();
          wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
          totalWordsCount++;
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
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

  private void doStats() {
    System.out.println("Total number of words is " + totalWordsCount);

    // sort by word occurrence
    mostUsedNWords = wordCountMap.entrySet().stream().sorted(comparingByValue()).limit(n)
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
            LinkedHashMap::new));

    System.out.println("Most used words are " + mostUsedNWords.toString());

    leastUsedNWords = wordCountMap.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(n)
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
            LinkedHashMap::new));

    System.out.println("Least used words are " + leastUsedNWords.toString());
  }

  public void read() throws IOException, InterruptedException {
    if (isDirectory) {
      countWordsInAllFiles();
    } else {
      countWordsInFile(path.toAbsolutePath().toString());
    }
    doStats();
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    WordCounter wc = new WordCounter("/Users/inas/Careem/Backend/src/main/resources/word.counter", 2);
    wc.read();
  }
}