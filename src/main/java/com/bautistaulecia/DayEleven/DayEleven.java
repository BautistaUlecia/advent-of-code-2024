package com.bautistaulecia.DayEleven;

import com.bautistaulecia.Util.FileParser;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayEleven {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayEleven.class);

  public static void solve() {
    String inputAsString = FileParser.toSingleString("src/main/resources/DayEleven/input.txt");
    List<String> input = List.of(inputAsString.split(" "));
    List<String> result = input;
    LOGGER.info("Input: {}", input);
    for (int i = 0; i < 25; i++) {
      result = result.stream().map(DayEleven::blink).flatMap(List::stream).toList();
    }
    LOGGER.info("Result size = {}", result.size());
  }

  public static List<String> blink(String input) {
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
