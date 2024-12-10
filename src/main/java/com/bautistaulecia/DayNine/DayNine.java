package com.bautistaulecia.DayNine;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayNine {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayNine.class);

  public static void solve() {
    String input = FileParser.toSingleString("src/main/resources/DayNine/input.txt");
    List<String> expandedInput = expandInput(input);
    List<String> orderedAndExpandedInput = swapNumbersAndDots(expandedInput);
    Long result = calculateChecksum(orderedAndExpandedInput);
    LOGGER.info("{}", result);
  }

  public static List<String> expandInput(String input) {
    int numberId = 0; // Starts at zero increments every time a number is added
    List<String> result = new ArrayList<>();

    // Add [i] amount of numberId or [i] amount of '.' for every character
    for (int i = 0; i < input.length(); i++) {
      int amountToAdd = Character.getNumericValue(input.charAt(i));
      if (i % 2 == 0) {
        for (int j = 0; j < amountToAdd; j++) {
          result.add(String.valueOf(numberId));
        }
        numberId += 1;
      } else {
        for (int k = 0; k < amountToAdd; k++) {
          result.add(String.valueOf('.'));
        }
      }
    }
    return result;
  }

  public static List<String> swapNumbersAndDots(List<String> expandedInput) {
    // Swap numbers and dots in place using two pointer approach
    String[] expandedInputAsCharArray = expandedInput.toArray(new String[0]);
    int left = 0;
    int right = expandedInputAsCharArray.length - 1;

    while (left < right) {
      while (!expandedInputAsCharArray[left].equals(".")) {
        left++;
      }
      while (expandedInputAsCharArray[right].equals(".")) {
        right--;
      }
      String temp = expandedInputAsCharArray[left];
      expandedInputAsCharArray[left] = expandedInputAsCharArray[right];
      expandedInputAsCharArray[right] = temp;
      left++;
      right--;
    }
    return Arrays.asList(expandedInputAsCharArray);
  }

  public static Long calculateChecksum(List<String> orderedExpandedInput) {
    Long counter = 0L;
    for (int i = 0; i < orderedExpandedInput.size(); i++) {
      if (!orderedExpandedInput.get(i).equals(".")) {
        counter += (i * Long.parseLong((orderedExpandedInput.get(i))));
      }
    }
    return counter;
  }
}
