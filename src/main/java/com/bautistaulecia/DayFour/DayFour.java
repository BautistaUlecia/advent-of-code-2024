package com.bautistaulecia.DayFour;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Me paro en 00
// Busco izq der, der izq
// Arriba abajo, abajo arriba
// diagonales ida vuelta
// Lo agrego a "seen", si mi actual esta en seen saltear

public class DayFour {
  private static final Set<Line> seen = new HashSet<Line>();
  private static final Logger LOGGER = LoggerFactory.getLogger(DayFour.class);

  public static void solve() {
    int counter = 0;
    char[][] inputAsMatrix = FileParser.toMatrix("src/main/resources/DayFour/input.txt");
    for (int i = 0; i < inputAsMatrix.length; i++) {
      for (int j = 0; j < inputAsMatrix.length; j++) {
        counter += searchInAllDirections(new Coordinate(i, j), inputAsMatrix);
      }
    }
    LOGGER.info("{}", counter);
  }

  public static int searchInAllDirections(Coordinate position, char[][] matrix) {
    List<Line> validLines = getValidLines(position, matrix);
    LOGGER.info("Valid lines = {}", validLines);
    int ocurrences = searchXmas(validLines, matrix);
    Set<Line> seen = new HashSet<>();
    return ocurrences;
  }

  public static List<Line> getValidLines(Coordinate position, char[][] matrix) {
    boolean canSearchLeft = false;
    boolean canSearchUp = false;
    boolean canSearchDown = false;
    boolean canSearchRight = false;

    List<Line> directions = new ArrayList<>();
    // Should search left?
    if (position.x() >= 3) {
      Coordinate positionThreeSquaresToTheLeft = new Coordinate(position.x() - 3, position.y());
      directions.add(new Line(position, positionThreeSquaresToTheLeft));
      canSearchLeft = true;
    }
    // Should search right?
    if (position.x() < matrix.length - 3) {
      Coordinate positionThreeSquaresToTheRight = new Coordinate(position.x() + 3, position.y());
      directions.add(new Line(position, positionThreeSquaresToTheRight));
      canSearchRight = true;
    }
    // Should search up?
    if (position.y() >= 3) {
      Coordinate positionThreeSquaresUp = new Coordinate(position.x(), position.y() - 3);
      directions.add(new Line(position, positionThreeSquaresUp));
      canSearchUp = true;
    }
    // Should search down?
    if (position.y() < matrix.length - 3) {
      Coordinate positionThreeSquaresDown = new Coordinate(position.x(), position.y() + 3);
      directions.add(new Line(position, positionThreeSquaresDown));
      canSearchDown = true;
    }
    // Should search diagonals?
    if (canSearchUp && canSearchLeft) {
      Coordinate diagonalThreeUpThreeLeft = new Coordinate(position.x() - 3, position.y() - 3);
      directions.add(new Line(position, diagonalThreeUpThreeLeft));
    }
    if (canSearchUp && canSearchRight) {
      Coordinate diagonalThreeUpThreeRight = new Coordinate(position.x() + 3, position.y() - 3);
      directions.add(new Line(position, diagonalThreeUpThreeRight));
    }
    if (canSearchDown && canSearchLeft) {
      Coordinate diagonalThreeDownThreeLeft = new Coordinate(position.x() - 3, position.y() + 3);
      directions.add(new Line(position, diagonalThreeDownThreeLeft));
    }
    if (canSearchDown && canSearchRight) {
      Coordinate diagonalThreeDownThreeRight = new Coordinate(position.x() + 3, position.y() + 3);
      directions.add(new Line(position, diagonalThreeDownThreeRight));
    }
    seen.addAll(directions);
    return directions;
  }

  public static int searchXmas(List<Line> validLines, char[][] matrix) {
    int counter = 0;
    for (Line line : validLines) {
      List<Character> word = new ArrayList<Character>();
      int currX = line.getFrom().x();
      int currY = line.getFrom().y();
      int maxX = line.getTo().x();
      int maxY = line.getTo().y();
      LOGGER.info("currx {}, curry {}, maxX {}, maxy{}", currX, currY, maxX, maxY);
      while (currX != maxX || currY != maxY) {
        word.add(matrix[currX][currY]);
        if (currX < maxX) {
          currX++;
          LOGGER.info("Incremented curr x");
        }
        if (currX > maxX) {
          currX--;
          LOGGER.info("Decremented curr x");
        }

        if (currY < maxY) {
          currY++;
          LOGGER.info("Incremented curr y");
        }
        if (currY > maxY) {
          currY--;
          LOGGER.info("Decremented curry");
        }
      }
      word.add(matrix[currX][currY]);

      LOGGER.info("WORD = {}", word);
      if (word.equals(List.of('X', 'M', 'A', 'S')) || word.equals(List.of('S', 'A', 'M', 'X'))) {
        counter++;
      }
    }
    return counter;
  }
}
