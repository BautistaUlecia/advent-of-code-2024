package com.bautistaulecia.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileParser {
  public static List<String> toLines(String path) {
    try {
      return Files.readAllLines(Paths.get(path));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String toSingleString(String path) {
    try {
      return Files.readString(Paths.get(path));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static char[][] toMatrix(String path) {
    try {
      List<String> lines = Files.readAllLines(Paths.get(path));
      int size = lines.size(); // Matrix is square
      char[][] matrix = new char[size][size];
      for (int i = 0; i < size; i++) {
        matrix[i] = lines.get(i).toCharArray();
      }
      return matrix;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
