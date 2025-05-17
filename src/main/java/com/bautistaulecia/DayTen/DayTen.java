package com.bautistaulecia.DayTen;

import com.bautistaulecia.Util.FileParser;
import com.bautistaulecia.Util.Pair;
import java.util.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayTen {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayTen.class);

  public static void solve() {
    char[][] matrix = FileParser.toSquareMatrix("src/main/resources/DayTen/input.txt");
    int size = matrix.length;
    int totalScore = 0;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (matrix[i][j] == '0') {
          Pair<Integer, Integer> foundZero = new Pair<>(i, j);
          totalScore += dfs(matrix, foundZero);
        }
      }
    }
    LOGGER.info("Total score = {}", totalScore);
  }

  public static int dfs(char[][] matrix, Pair<Integer, Integer> foundZero) {
    Stack<Pair<Integer, Integer>> stack = new Stack<>();
    boolean[][] visited = new boolean[matrix.length][matrix.length];
    stack.push(foundZero);
    int counter = 0;
    while (!stack.isEmpty()) {
      Pair<Integer, Integer> p = stack.pop();
      if (matrix[p.first()][p.second()] == '9') {
        counter += 1;
      }
      // visited[p.first()][p.second()] = true; Uncomment for part 1!
      Pair<Integer, Integer> left = new Pair<>(p.first() - 1, p.second());
      Pair<Integer, Integer> right = new Pair<>(p.first() + 1, p.second());
      Pair<Integer, Integer> down = new Pair<>(p.first(), p.second() + 1);
      Pair<Integer, Integer> up = new Pair<>(p.first(), p.second() - 1);

      if (isValidNextStep(matrix, p, left, visited)) {
        stack.add(left);
      }
      if (isValidNextStep(matrix, p, right, visited)) {
        stack.add(right);
      }
      if (isValidNextStep(matrix, p, down, visited)) {
        stack.add(down);
      }
      if (isValidNextStep(matrix, p, up, visited)) {
        stack.add(up);
      }
    }
    return counter;
  }

  public static Boolean isValidNextStep(
      char[][] matrix,
      Pair<Integer, Integer> current,
      Pair<Integer, Integer> next,
      boolean[][] visited) {
    return isWithinBounds(matrix.length, next)
        && (int) matrix[next.first()][next.second()]
                - (int) matrix[current.first()][current.second()]
            == 1
        && !visited[next.first()][next.second()];
  }

  public static Boolean isWithinBounds(int size, Pair<Integer, Integer> position) {
    return position.first() >= 0
        && position.second() >= 0
        && position.first() < size
        && position.second() < size;
  }
}
