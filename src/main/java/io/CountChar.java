package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CountChar {

  private final String FILE_NAME;

  public CountChar(String file_name) {
    FILE_NAME = file_name;
  }

  int count(char c) {
    int result = 0;
    //read the contents of a file as a stream of characters
    try (Reader fileReader = new FileReader(FILE_NAME); Reader buff = new BufferedReader(fileReader)) {
      int data = buff.read();
      while (data != -1) {
        char charData = (char) data;
        if (charData == c || (Character.isAlphabetic(c) && Character.toLowerCase(charData) == c)) {
          result++;
        }
        data = buff.read();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  int countCaseSensitive(char c) {
    int result = 0;
    //read the contents of a file as a stream of characters
    try (Reader fileReader = new FileReader(FILE_NAME); Reader buff = new BufferedReader(fileReader)) {
      int data = buff.read();
      while (data != -1) {
        if ((char) data == c) {
          result++;
        }
        data = buff.read();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static void main(String args[]) {
    CountChar cc = new CountChar("/Users/inas/Careem/Backend/src/main/resources/io/count_char_file.txt");
    System.out.print(cc.count(' '));
  }
}

