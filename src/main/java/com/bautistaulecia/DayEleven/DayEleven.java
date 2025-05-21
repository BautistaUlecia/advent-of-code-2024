package com.bautistaulecia.DayEleven;

import com.bautistaulecia.Util.FileParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayEleven {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayEleven.class);

  private static final Map<String, List<String>> knownResults = new HashMap<>();
  private static Map<String, Long> frequencies = new HashMap<>();

  public static void solve() {
    String inputAsString = FileParser.toSingleString("src/main/resources/DayEleven/input.txt");
    int totalSteps = 75;
    int currentStep = 0;
    List<String> input = List.of(inputAsString.split(" "));

    for (String stone : input) {
      frequencies.put(stone, 1L);
    }

    Map<String, Long> newFrequencies;

    while (currentStep < totalSteps) {
      newFrequencies = new HashMap<>();
      for (String stone : frequencies.keySet()) {

        List<String> newStones = findOrCalculate(stone);

        Map<String, Long> stonesByFrequency =
            newStones.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (String newStone : stonesByFrequency.keySet()) {
          long addedValue = stonesByFrequency.get(newStone) * frequencies.get(stone);
          newFrequencies.put(newStone, newFrequencies.getOrDefault(newStone, 0L) + addedValue);
        }
      }

      frequencies = newFrequencies;
      currentStep++;
    }
    long result = frequencies.values().stream().mapToLong(Long::longValue).sum();
    LOGGER.info("Freq = {}", result);
  }

  public static List<String> findOrCalculate(String stone) {
    return knownResults.computeIfAbsent(stone, DayEleven::calculate);
  }

  public static List<String> calculate(String input) {
    if (input.equals("0")) return List.of("1");
    if (input.length() % 2 == 0) {
      int half = input.length() / 2;
      return List.of(
          removeLeadingZeroes(input.substring(0, half)),
          removeLeadingZeroes(input.substring(half)));
    } else {
      long newStone = Long.parseLong(input) * 2024;
      return List.of(String.valueOf(newStone));
    }
  }

  public static String removeLeadingZeroes(String input) {
    for (int i = 0; i < input.length(); i++) {
      if (input.charAt(i) != '0') {
        return input.substring(i);
      }
    }
    return "0";
  }
}
