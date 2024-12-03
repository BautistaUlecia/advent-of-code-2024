package com.bautistaulecia.DayThree;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Honestly not super proud of this one, but it's my first time using regex in Java
// And it gets the job done (in a performant fashion, too)

public class DayThree {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayThree.class);

  public static void solve() {
    String fileAsString = FileParser.toSingleString("src/main/resources/DayThree/input.txt");
    List<String> matches = new ArrayList<>();
    Pattern multPattern = Pattern.compile("mul[(].?.?.?,*.?.?.?[)]");
    Pattern doPattern = Pattern.compile("do[()]");
    Pattern dontPattern = Pattern.compile("don't[()]");
    Matcher multPatternMatcher = multPattern.matcher(fileAsString);
    Matcher doPatternMatcher;
    Matcher dontPatternMatcher;

    boolean matching = true;
    int lastKnownEnd = 0;
    int i = 0;
    List<Integer> knownEnds = new ArrayList<>();

    while (multPatternMatcher.find()) {
      // If match for mult is found, search for last occurrence of "do" or "don't" in substring
      // Which will be either 0 to start of mult (first iteration) or lastKnownEnd of mult to
      // currentStart

      knownEnds.add(multPatternMatcher.end());
      if (i > 0) {
        lastKnownEnd = knownEnds.get(i - 1);
      }
      String sublist = getValidSublist(lastKnownEnd, multPatternMatcher.start(), fileAsString);
      doPatternMatcher = doPattern.matcher(sublist);
      dontPatternMatcher = dontPattern.matcher(sublist);
      if (doPatternMatcher.find()) {
        matching = true;
      }
      if (dontPatternMatcher.find()) {
        matching = false; // use this line for part two
        // matching = true; //  <= use this line for part one, since do and don't are ignored
      }
      if (matching) {
        matches.add(multPatternMatcher.group());
      }
      i++;
    }
    LOGGER.info("Part two: {}", calculateValueFromMatches(matches));
    calculateValueFromMatches(matches);
  }

  private static int calculateValueFromMatches(List<String> matches) {
    return matches.stream()
        .mapToInt(
            match -> {
              String[] numbers = match.replaceAll("[^0-9,]", "").split(",");
              int first = Integer.parseInt(numbers[0]);
              int second = Integer.parseInt(numbers[1]);
              return first * second;
            })
        .sum();
  }

  public static String getValidSublist(int lastKnownEnd, int currentStart, String input) {
    return input.substring(lastKnownEnd, currentStart);
  }
}
