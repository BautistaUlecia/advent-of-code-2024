package com.bautistaulecia.DayThree;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Honestly not super proud of this one, but it's my first time using regex in Java,
and it gets the job done. Also, I realized (not while solving) that lastIndexOf() exists,
which could remove like 30 lines of code, but to stay true to the spirit of the challenge,
I will leave my implementation as is. */

public class DayThree {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayThree.class);

  public static void solve() {
    String fileAsString = FileParser.toSingleString("src/main/resources/DayThree/input.txt");
    List<String> matches = new ArrayList<>();
    Pattern multPattern = Pattern.compile("mul[(].?.?.?,*.?.?.?[)]");
    Pattern doPattern = Pattern.compile("do[()]");
    Pattern dontPattern = Pattern.compile("don't[()]");
    Matcher multPatternMatcher = multPattern.matcher(fileAsString);

    boolean matching = true;
    int lastKnownEnd = 0;
    int i = 0;
    List<Integer> knownEnds = new ArrayList<>();

    while (multPatternMatcher.find()) {
      // If match for mult is found, search for last occurrence of "do" or "don't" in substring
      // Which will be either 0 to start of mult (first iteration) or lastKnownEnd of mult to
      // currentStart (non-first iteration)

      knownEnds.add(multPatternMatcher.end());
      if (i > 0) {
        lastKnownEnd = knownEnds.get(i - 1);
      }
      String sublist = getValidSublist(lastKnownEnd, multPatternMatcher.start(), fileAsString);

      Matcher doMatcher = doPattern.matcher(sublist);
      Matcher dontMatcher = dontPattern.matcher(sublist);

      int lastDoIndex = -1;
      int lastDontIndex = -1;

      while (doMatcher.find()) {
        lastDoIndex = doMatcher.start();
      }

      while (dontMatcher.find()) {
        lastDontIndex = dontMatcher.start();
      }

      if (lastDontIndex > lastDoIndex) {
        matching = false;
      } else if (lastDoIndex > lastDontIndex) {
        matching = true;
      }

      // Comment this if for part one (since all mult should be considered)
      if (matching) {
        matches.add(multPatternMatcher.group());
      }
      i++;
    }
    LOGGER.info("Solution: {}", calculateValueFromMatches(matches));
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
