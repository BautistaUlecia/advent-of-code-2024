package com.bautistaulecia.DayFive;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayFive {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayFive.class);
  public static Map<String, List<String>> rulesForEachValue = new HashMap<>();
  private static final List<List<String>> validInstructions = new ArrayList<>();
  private static final List<List<String>> invalidInstructions = new ArrayList<>();

  public static void solve() {

    List<String> lines = FileParser.toLines("src/main/resources/DayFive/test.txt");
    Map<Boolean, List<String>> partitionedLines =
        lines.stream().collect(Collectors.partitioningBy(l -> l.contains("|")));

    List<String> rules = partitionedLines.get(true);
    List<String> instructions = partitionedLines.get(false);
    instructions.removeIf(String::isEmpty);
    addRulesToMap(rules);

    // Assume all instructions are valid at start, filter by those that break a rule.
    for (String instruction : instructions) {
      List<String> instructionAsList = List.of(instruction.split(","));
      validInstructions.add(instructionAsList);
      for (String element : instructionAsList) {
        List<String> rulesForElement = rulesForEachValue.getOrDefault(element, List.of());
        if (rulesForElement.stream()
            .filter(instructionAsList::contains)
            .anyMatch(
                rule -> instructionAsList.indexOf(rule) < instructionAsList.indexOf(element))) {
          validInstructions.remove(instructionAsList);
          invalidInstructions.add(instructionAsList);
        }
      }
    }
    int sum =
        validInstructions.stream().map(DayFive::getMiddleElement).mapToInt(Integer::valueOf).sum();
    LOGGER.info("{}", sum);
  }

  public static void addRulesToMap(List<String> rules) {
    rules.stream()
        .map(r -> r.split("\\|"))
        .forEach(
            pair ->
                rulesForEachValue.computeIfAbsent(pair[0], (k) -> new ArrayList<>()).add(pair[1]));
  }

  public static String getMiddleElement(List<String> steps) {
    return steps.get((steps.size() - 1) / 2);
  }

  public static String correctedInstruction(List<List<String>> invalidInstructions) {
    // De alguna forma, ordenar segun las reglas que ya tengo
    return "TO DO XD!";
  }
}
