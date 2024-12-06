package com.bautistaulecia.DayFour;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Could probably handle 'seen' logic inside the valid lines logic
// to add a bit of speed, but it's optimized enough I think

public class DayFour {
  private static final Set<Line> seen = new HashSet<Line>();
  private static final Logger LOGGER = LoggerFactory.getLogger(DayFour.class);

  public static void solve() {
    int counter = 0;
    char[][] inputAsMatrix = FileParser.toMatrix("src/main/resources/DayFour/input.txt");
    for (int i = 0; i < inputAsMatrix.length; i++) {
      for (int j = 0; j < inputAsMatrix.length; j++) {
        counter += searchInAllValidLines(new Coordinate(i, j), inputAsMatrix);
      }
    }
    LOGGER.info("{}", counter);
  }

  public static int searchInAllValidLines(Coordinate position, char[][] matrix) {
    int lineSize = 3;
    List<Line> validLines = getValidLines(position, matrix, lineSize);
    return searchXmas(validLines, matrix);
  }

  public static List<Line> getValidLines(Coordinate position, char[][] matrix, int lineSize) {
    boolean canSearchLeft = false;
    boolean canSearchUp = false;
    boolean canSearchDown = false;
    boolean canSearchRight = false;

    List<Line> directions = new ArrayList<>();
    // Should search left?
    if (position.x() >= lineSize) {
      Coordinate positionThreeSquaresToTheLeft =
          new Coordinate(position.x() - lineSize, position.y());
      directions.add(new Line(position, positionThreeSquaresToTheLeft));
      canSearchLeft = true;
    }
    // Should search right?
    if (position.x() < matrix.length - lineSize) {
      Coordinate positionThreeSquaresToTheRight =
          new Coordinate(position.x() + lineSize, position.y());
      directions.add(new Line(position, positionThreeSquaresToTheRight));
      canSearchRight = true;
    }
    // Should search up?
    if (position.y() >= lineSize) {
      Coordinate positionThreeSquaresUp = new Coordinate(position.x(), position.y() - lineSize);
      directions.add(new Line(position, positionThreeSquaresUp));
      canSearchUp = true;
    }
    // Should search down?
    if (position.y() < matrix.length - lineSize) {
      Coordinate positionThreeSquaresDown = new Coordinate(position.x(), position.y() + lineSize);
      directions.add(new Line(position, positionThreeSquaresDown));
      canSearchDown = true;
    }
    // Should search diagonals?
    if (canSearchUp && canSearchLeft) {
      Coordinate diagonalThreeUpThreeLeft =
          new Coordinate(position.x() - lineSize, position.y() - lineSize);
      directions.add(new Line(position, diagonalThreeUpThreeLeft));
    }
    if (canSearchUp && canSearchRight) {
      Coordinate diagonalThreeUpThreeRight =
          new Coordinate(position.x() + lineSize, position.y() - lineSize);
      directions.add(new Line(position, diagonalThreeUpThreeRight));
    }
    if (canSearchDown && canSearchLeft) {
      Coordinate diagonalThreeDownThreeLeft =
          new Coordinate(position.x() - lineSize, position.y() + lineSize);
      directions.add(new Line(position, diagonalThreeDownThreeLeft));
    }
    if (canSearchDown && canSearchRight) {
      Coordinate diagonalThreeDownThreeRight =
          new Coordinate(position.x() + lineSize, position.y() + lineSize);
      directions.add(new Line(position, diagonalThreeDownThreeRight));
    }
    return directions;
  }

  public static int searchXmas(List<Line> validLines, char[][] matrix) {
    int counter = 0;
    for (Line line : validLines) {
      if (seen.add(line)) {
        List<Character> word = extractWord(line, matrix);
        // search 0-1-2-3 and 3-2-1-0 at the same time
        if (word.equals(List.of('X', 'M', 'A', 'S')) || word.equals(List.of('S', 'A', 'M', 'X'))) {
          counter++;
        }
      }
    }
    return counter;
  }

  private static List<Character> extractWord(Line line, char[][] matrix) {
    List<Character> word = new ArrayList<>();
    int currX = line.getFrom().x();
    int currY = line.getFrom().y();
    int maxX = line.getTo().x();
    int maxY = line.getTo().y();

    while (currX != maxX || currY != maxY) {
      word.add(matrix[currX][currY]);
      if (currX < maxX) currX++;
      if (currX > maxX) currX--;
      if (currY < maxY) currY++;
      if (currY > maxY) currY--;
    }
    word.add(matrix[currX][currY]);
    return word;
  }
}
