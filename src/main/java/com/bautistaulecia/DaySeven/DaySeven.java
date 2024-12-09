package com.bautistaulecia.DaySeven;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaySeven {
  private static final Logger LOGGER = LoggerFactory.getLogger(DaySeven.class);

  public static void solve() {
    List<String> lines = FileParser.toLines("src/main/resources/DaySeven/input.txt");
    Map<Long, List<Long>> targetResultToNumbersMap = new HashMap<>();
    for (String line : lines) {
      String[] parts = line.split(":");
      Long resultAsLong = Long.valueOf(parts[0]);
      List<Long> numbersAsLong =
          Arrays.stream(parts[1].trim().split(" ")).map(Long::valueOf).toList();
      targetResultToNumbersMap.put(resultAsLong, numbersAsLong);
    }
    Long result = sumTargetsThatCanBeReached(targetResultToNumbersMap);
    LOGGER.info("Result = {}", result);
  }

  public static Long sumTargetsThatCanBeReached(Map<Long, List<Long>> targetResultToNumbersMap) {
    Long totalSum = 0L;
    List<Character> operators = new ArrayList<>();
    operators.add('*');
    operators.add('+');
    operators.add('|'); // Comment out for part one
    for (Map.Entry<Long, List<Long>> entry : targetResultToNumbersMap.entrySet()) {
      List<List<Character>> operatorChoicesPerStep = new ArrayList<>();
      Long targetResult = entry.getKey();
      List<Long> numbers = entry.getValue();

      int possibleOperations = numbers.size() - 1;
      for (int i = 0; i < possibleOperations; i++) {
        operatorChoicesPerStep.add(operators);
      }
      List<List<Character>> cartesianProduct =
          cartesianProductBetweenAnyAmount(operatorChoicesPerStep);
      for (List<Character> cartesian : cartesianProduct) {
        Long possibleResult = performOperations(numbers, cartesian);
        if (possibleResult.equals(targetResult)) {
          totalSum += possibleResult;
          break;
        }
      }
    }
    return totalSum;
  }

  public static List<List<Character>> cartesianProductBetweenAnyAmount(
      List<List<Character>> allLists) {
    List<List<Character>> result = new ArrayList<>();
    for (Character element : allLists.getFirst()) {
      List<Character> temp = new ArrayList<>();
      temp.add(element);
      result.add(temp);
    }
    for (int i = 1; i < allLists.size(); i++) {
      result = cartesianProductBetweenTwo(result, allLists.get(i));
    }
    return result;
  }

  public static List<List<Character>> cartesianProductBetweenTwo(
      List<List<Character>> listA, List<Character> listB) {
    List<List<Character>> result = new ArrayList<>();
    for (List<Character> a : listA) {
      for (Character b : listB) {
        List<Character> temp = new ArrayList<>(a);
        temp.add(b);
        result.add(temp);
      }
    }
    return result;
  }

  public static Long performOperations(List<Long> numbers, List<Character> operations) {
    Long currentResult = numbers.getFirst();
    for (int i = 1; i < numbers.size(); i++) {
      currentResult = performOperation(currentResult, numbers.get(i), operations.get(i - 1));
    }
    return currentResult;
  }

  public static Long performOperation(Long a, Long b, Character operator) {
    return switch (operator) {
      case '*' -> a * b;
      case '+' -> a + b;
      case '|' -> Long.valueOf(String.valueOf(a) + String.valueOf(b));
      default -> throw new UnsupportedOperationException("Unknown operator found");
    };
  }
}
