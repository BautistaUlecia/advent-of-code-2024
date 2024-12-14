package com.bautistaulecia.DayNine;

import com.bautistaulecia.Util.FileParser;
import com.bautistaulecia.Util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    List<String> inputOrderedBySwappingBlocks = swapNumberBlocksAndDotBlocks(expandedInput);
    Long partTwoResult = calculateChecksum(inputOrderedBySwappingBlocks);
    LOGGER.info("Part two = {}", partTwoResult);
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

  public static List<String> swapNumberBlocksAndDotBlocks(List<String> expandedInput) {
    // First, add all index of empty spaces to a list. Then get number block, find first space that
    // can fit it (it should be big enough, and be located before the number block), and
    // if number has not been moved yet, swap.

    // If swapped, update spaces list to reflect new occupied space and add number's ID to "moved"
    // set.
    // This runs very fast because I only have to iterate the list twice, once for spaces and once
    // for moving.

    Set<String> moved = new HashSet<>();
    String[] expandedInputAsArray = expandedInput.toArray(new String[0]);
    List<Pair<Integer, Integer>> freeSpaces = getFreeSpaces(expandedInputAsArray);
    int right = expandedInputAsArray.length - 1;

    while (right > 0) {
      while (right > 0 && expandedInputAsArray[right].equals(".")) {
        right--;
      }

      if (right <= 0) {
        break;
      }

      int slidingRight = right;

      while (slidingRight > 0
          && expandedInputAsArray[right].equals(expandedInputAsArray[slidingRight])) {
        slidingRight--;
      }

      int fileSize = right - slidingRight;
      Pair<Integer, Integer> numberBlock = new Pair<>(slidingRight + 1, right);
      Optional<Pair<Integer, Integer>> possibleFreeSpace =
          freeSpaces.stream()
              .filter(pair -> ((pair.second() - pair.first() + 1) >= (fileSize)))
              .findFirst();

      // There is a free space of size n, it's before the number and the number has not moved yet
      if (possibleFreeSpace.isPresent()
          && (!moved.contains(expandedInputAsArray[numberBlock.first()])
              && numberBlock.first() > possibleFreeSpace.get().first())) {

        // Swap and add number to moved
        Pair<Integer, Integer> freeSpace = possibleFreeSpace.get();
        swapBlocks(expandedInputAsArray, freeSpace, numberBlock);
        moved.add(expandedInputAsArray[numberBlock.first()]);

        // Update list
        int newStart = freeSpace.first() + fileSize;
        updateFreeSpaces(freeSpaces, freeSpace, newStart);
      }
      right = slidingRight;
    }
    return Arrays.asList(expandedInputAsArray);
  }

  private static void updateFreeSpaces(
      List<Pair<Integer, Integer>> freeSpaces, Pair<Integer, Integer> freeSpace, int newStart) {
    if (newStart <= freeSpace.second()) {
      freeSpaces.set(freeSpaces.indexOf(freeSpace), new Pair<>(newStart, freeSpace.second()));
    } else {
      freeSpaces.remove(freeSpace);
    }
  }

  public static void swapBlocks(
      String[] array, Pair<Integer, Integer> freeSpace, Pair<Integer, Integer> numberBlock) {
    int operations = numberBlock.second() - numberBlock.first() + 1;
    for (int i = 0; i < operations; i++) {
      int freeIndex = freeSpace.first() + i;
      int numberIndex = numberBlock.second() - i;
      String temp = array[freeIndex];
      array[freeIndex] = array[numberIndex];
      array[numberIndex] = temp;
    }
  }

  public static List<Pair<Integer, Integer>> getFreeSpaces(String[] expandedInputAsArray) {
    List<Pair<Integer, Integer>> freeSpaces = new ArrayList<>();
    int left = 0;
    int end = expandedInputAsArray.length - 1;

    while (left <= end) {
      while (left <= end && !expandedInputAsArray[left].equals(".")) {
        left++;
      }
      if (left > end) {
        break;
      }
      int slidingLeft = left;
      while (slidingLeft <= end && expandedInputAsArray[slidingLeft].equals(".")) {
        slidingLeft++;
      }
      freeSpaces.add(new Pair<>(left, slidingLeft - 1));
      left = slidingLeft;
    }
    return freeSpaces;
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
