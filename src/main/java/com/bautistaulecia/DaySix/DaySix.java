package com.bautistaulecia.DaySix;

import com.bautistaulecia.Util.Coordinate;
import com.bautistaulecia.Util.Direction;
import com.bautistaulecia.Util.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaySix {
  private static final Logger LOGGER = LoggerFactory.getLogger(DaySix.class);

  public static void solve() {

    char[][] labAsMatrix = FileParser.toMatrix("src/main/resources/DaySix/input.txt");
    boolean[][] visitedMatrix = new boolean[labAsMatrix.length][labAsMatrix.length];

    Coordinate guardsStartingPosition = getGuardsStartingPosition(labAsMatrix);
    Direction guardsStartingDirection = Direction.UP;
    Guard guard = new Guard(guardsStartingPosition, guardsStartingDirection);

    while (guard != null) {
      visitedMatrix[guard.getPosition().x()][guard.getPosition().y()] = true;
      guard = moveGuard(labAsMatrix, guard);
    }

    LOGGER.info("Part one: {}", getVisitedMatrixCount(visitedMatrix));
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

  public static Guard moveGuard(char[][] matrix, Guard guard) {
    Coordinate currentPosition = guard.getPosition();
    int deltaX = guard.getDirection().getDeltaX();
    int deltaY = guard.getDirection().getDeltaY();

    // Precompute next position, if out of bounds then guard has exited the lab
    Coordinate nextGuardPosition =
        new Coordinate(currentPosition.x() + deltaX, currentPosition.y() + deltaY);
    if (!positionExistsInMatrix(matrix, nextGuardPosition)) {
      LOGGER.info("Guard exited matrix at position {}", nextGuardPosition);
      return null;
    }

    // If no obstacle, return guard with same direction, updated position
    if (matrix[nextGuardPosition.y()][nextGuardPosition.x()] != '#') {
      guard.position = nextGuardPosition;
      return guard;
    }

    // If obstacle, return guard with same position, updated direction
    guard.changeDirectionClockwise();
    return guard;
  }

  public static boolean positionExistsInMatrix(char[][] matrix, Coordinate position) {
    return position.x() >= 0
        && position.y() >= 0
        && position.x() < matrix[0].length
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
}
