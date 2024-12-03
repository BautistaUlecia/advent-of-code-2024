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
}
