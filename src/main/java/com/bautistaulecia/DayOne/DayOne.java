package com.bautistaulecia.DayOne;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayOne {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayOne.class);

  public static void solve() {
    List<String> lines = FileParser.toLines("src/main/resources/DayOne/input.txt");
    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();

    lines.forEach(
        line -> {
          String[] parts =
              line.split(" {3}"); // Split at 3 spaces ("   ") (It's what the input shows)
          left.add(Integer.parseInt(parts[0]));
          right.add(Integer.parseInt(parts[1]));
        });
    left.sort(Integer::compareTo);
    right.sort(Integer::compareTo);

    List<Integer> distances = new ArrayList<>();
    for (int i = 0; i < left.size(); i++) {
      distances.add(Math.abs(left.get(i) - right.get(i)));
    }
    int partOne = distances.stream().mapToInt(Integer::intValue).sum();
    LOGGER.info("Part one: {}", partOne);

    // Part two
    List<Integer> similarityScores =
        left.stream().map(l -> l * Collections.frequency(right, l)).toList();
    int partTwo = similarityScores.stream().mapToInt(Integer::intValue).sum();
    LOGGER.info("Part two: {}", partTwo);
  }
}
