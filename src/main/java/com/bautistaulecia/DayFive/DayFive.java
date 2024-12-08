package com.bautistaulecia.DayFive;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayFive {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayFive.class);
  public static Map<String, List<String>> rulesForEachValue = new HashMap<>();
  private static final List<List<String>> validInstructions = new ArrayList<>();
  private static final Set<List<String>> invalidInstructions = new HashSet<>();

  public static void solve() {

    List<String> lines = FileParser.toLines("src/main/resources/DayFive/input.txt");
    Map<Boolean, List<String>> partitionedLines =
        lines.stream().collect(Collectors.partitioningBy(line -> line.contains("|")));

    List<String> rules = partitionedLines.get(true);
    List<String> instructions = partitionedLines.get(false);
    instructions.removeIf(String::isEmpty);
    addRulesToMap(rules);

    // Assume all instructions are valid at start, filter by those that break a rule.
    for (String instruction : instructions) {
      List<String> instructionAsList = List.of(instruction.split(","));
      validInstructions.add(instructionAsList);
      if (breaksRule(instructionAsList)) {
        validInstructions.remove(instructionAsList);
        invalidInstructions.add(instructionAsList);
      }
    }
    LOGGER.info("Part one: {}", sumValidInstructions(validInstructions));
    LOGGER.info("Part two: {}", sumCorrectedInstructions(invalidInstructions));
  }

  public static void addRulesToMap(List<String> rules) {
    // Split at pipe, add to map containing:
    // Key: element that has priority
    // Value: elements that Key has priority over
    rules.stream()
        .map(rule -> rule.split("\\|"))
        .forEach(
            pair ->
                rulesForEachValue.computeIfAbsent(pair[0], (k) -> new ArrayList<>()).add(pair[1]));
  }

  public static int sumValidInstructions(List<List<String>> validInstructions) {
    return validInstructions.stream()
        .map(DayFive::getMiddleElement)
        .mapToInt(Integer::valueOf)
        .sum();
  }

  public static int sumCorrectedInstructions(Set<List<String>> invalidInstructions) {
    return invalidInstructions.stream()
        .map(DayFive::fixInvalidInstruction)
        .map(DayFive::getMiddleElement)
        .mapToInt(Integer::valueOf)
        .sum();
  }

  public static boolean breaksRule(List<String> instruction) {
    // If any rule for an element is before the element itself in the instruction,
    // Its considered invalid.
    for (String element : instruction) {
      List<String> rulesForElement = rulesForEachValue.getOrDefault(element, List.of());
      if (rulesForElement.stream()
          .filter(instruction::contains)
          .anyMatch(rule -> instruction.indexOf(rule) < instruction.indexOf(element))) {
        return true;
      }
    }
    return false;
  }

  public static List<String> fixInvalidInstruction(List<String> instruction) {
    // Readability could be improved but this runs very fast :P
    List<String> mutableCopyOfInstruction = new ArrayList<>(instruction);
    while (breaksRule(mutableCopyOfInstruction)) {
      for (String element : instruction) {
        List<String> rulesForElement = rulesForEachValue.getOrDefault(element, List.of());
        for (String rule : rulesForElement) {
          if (mutableCopyOfInstruction.contains(rule)) {
            if (mutableCopyOfInstruction.indexOf(rule)
                < mutableCopyOfInstruction.indexOf(element)) {
              Collections.swap(
                  mutableCopyOfInstruction,
                  mutableCopyOfInstruction.indexOf(rule),
                  mutableCopyOfInstruction.indexOf(element));
            }
          }
        }
      }
    }
    return mutableCopyOfInstruction;
  }

  public static String getMiddleElement(List<String> steps) {
    return steps.get((steps.size() - 1) / 2);
  }
}
