package com.bautistaulecia.DaySix;

import com.bautistaulecia.Util.Coordinate;
import com.bautistaulecia.Util.Direction;
import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaySix {
  // Code got a bit complex because of part two, could probably be optimized.
  // Commit for part one was pretty enough
  private static final Logger LOGGER = LoggerFactory.getLogger(DaySix.class);

  public static void solve() {
    char[][] labAsMatrix = FileParser.toMatrix("src/main/resources/DaySix/input.txt");
    boolean[][] visitedMatrix = new boolean[labAsMatrix.length][labAsMatrix.length];

    Coordinate guardsStartingPosition = getGuardsStartingPosition(labAsMatrix);
    Direction guardsStartingDirection = Direction.UP;
    Guard guard = new Guard(guardsStartingPosition, guardsStartingDirection);

    while (guard != null) {
      visitedMatrix[guard.getPosition().x()][guard.getPosition().y()] = true;
      guard = moveGuard(labAsMatrix, guard, new HashMap<>());
    }
    List<Coordinate> obstaclesThatResultInLoop =
        getObstaclesThatResultInLoop(labAsMatrix, guardsStartingPosition, guardsStartingDirection);

    LOGGER.info("Part one: {}", getVisitedMatrixCount(visitedMatrix));
    LOGGER.info("Coordinates that result in loop: {}", obstaclesThatResultInLoop.size());
  }

  public static Coordinate getGuardsStartingPosition(char[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        if (matrix[i][j] == '^') {
          return new Coordinate(j, i);
        }
      }
    }
    throw new RuntimeException("Guard was not found on matrix");
  }

  public static Guard moveGuard(
      char[][] matrix, Guard guard, HashMap<Coordinate, Integer> collissionsWithObstacles) {
    Coordinate currentPosition = guard.getPosition();
    int deltaX = guard.getDirection().getDeltaX();
    int deltaY = guard.getDirection().getDeltaY();

    // Precompute next position, if out of bounds then guard has exited the lab
    Coordinate nextGuardPosition =
        new Coordinate(currentPosition.x() + deltaX, currentPosition.y() + deltaY);
    if (!positionExistsInMatrix(matrix, nextGuardPosition)) {
      return null;
    }

    // If no obstacle, return guard with same direction, updated position
    if (matrix[nextGuardPosition.y()][nextGuardPosition.x()] != '#') {
      guard.position = nextGuardPosition;
      return guard;
    }

    // If obstacle, return guard with same position, updated direction
    guard.changeDirectionClockwise();
    // Keep track of obstacle collisions (for part two)
    int colissions = collissionsWithObstacles.getOrDefault(nextGuardPosition, 0);
    collissionsWithObstacles.put(nextGuardPosition, colissions + 1);
    return guard;
  }

  public static boolean positionExistsInMatrix(char[][] matrix, Coordinate position) {
    return position.x() >= 0
        && position.y() >= 0
        && position.x() < matrix.length
        && position.y() < matrix.length;
  }

  public static int getVisitedMatrixCount(boolean[][] visitedMatrix) {
    int visitedCounter = 0;
    for (int i = 0; i < visitedMatrix.length; i++) {
      for (int j = 0; j < visitedMatrix.length; j++) {
        if (visitedMatrix[i][j]) {
          visitedCounter++;
        }
      }
    }
    return visitedCounter;
  }

  public static List<Coordinate> getObstaclesThatResultInLoop(
      char[][] matrix, Coordinate guardStartingPosition, Direction guardStartingDirection) {
    Guard guardForThisTimeline = new Guard(guardStartingPosition, guardStartingDirection);
    List<Coordinate> loops = new ArrayList<>();
    // Get guard, check movement in matrix replacing values with '#', if loop add to list
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        HashMap<Coordinate, Integer> collissionsWithObstacles = new HashMap<>();
        char originalValue = matrix[i][j];
        matrix[i][j] = '#';
        while (guardForThisTimeline != null) {
          guardForThisTimeline = moveGuard(matrix, guardForThisTimeline, collissionsWithObstacles);
          if (collissionsWithObstacles.values().stream().filter(value -> value > 2).toList().size()
              >= 4) {
            loops.add(new Coordinate(j, i));
            break;
          }
        }

        matrix[i][j] = originalValue;
        guardForThisTimeline = new Guard(guardStartingPosition, guardStartingDirection);
      }
    }
    return loops;
  }
}
