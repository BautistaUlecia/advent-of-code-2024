package com.bautistaulecia.DayTwo;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayTwo {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayTwo.class);

  public static void solve() {
    List<String> lines = FileParser.toLines("src/main/resources/DayTwo/input.txt");
    List<List<Integer>> parsedLines = lines.stream().map(DayTwo::parseToIntList).toList();
    long safeCounter = parsedLines.stream().filter(DayTwo::isSafe).count();
    long fixedCounter =
        parsedLines.stream()
            .filter(l -> !isSafe(l))
            .filter(DayTwo::isFixableByRemovingOneElement)
            .count();
    LOGGER.info("Part one: {}", safeCounter);
    LOGGER.info("Part two: {}", safeCounter + fixedCounter);
  }

  public static Boolean isSafe(List<Integer> numbers) {
    return isSequence(numbers) && valuesDifferByOneToThree(numbers);
  }

  private static Boolean isSequence(List<Integer> line) {
    // Should be sorted (ascending or descending) if sequence
    return line.equals(line.stream().sorted().toList())
        || line.equals(line.stream().sorted(Comparator.reverseOrder()).toList());
  }

  private static Boolean valuesDifferByOneToThree(List<Integer> line) {
    // Every value should differ by no more than 3 and no less than 1 from its pair
    for (int i = 1; i < line.size(); i++) {
      if ((Math.abs(line.get(i) - line.get(i - 1)) < 1)
          || Math.abs(line.get(i) - line.get(i - 1)) > 3) {
        return false;
      }
    }
    return true;
  }

  private static Boolean isFixableByRemovingOneElement(List<Integer> line) {
    // Since lists are size 6 at most, brute-force is a reasonable approach.
    // Mutating the same list is problematic and what makes it safe for one
    // Parameter might make it unsafe for the other.
    for (int i = 0; i < line.size(); i++) {
      List<Integer> sublist = new ArrayList<>(line);
      sublist.remove(i);
      if (isSafe(sublist)) {
        return true;
      }
    }
    return false;
  }

  private static List<Integer> parseToIntList(String line) {
    return Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
  }
}
