package com.bautistaulecia.DayFour;

import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    int counter = 0;
    // int delta = 3; // Part one
    int delta = 1; // Part two

    List<Line> validLines = getValidLines(position, matrix, delta);

    // counter = searchXmas(validLines, matrix); // Part one

    if (formsXmasCross(validLines, matrix)) {
      counter++; // Part two
    }

    return counter;
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
    List<Character> XMAS = List.of('X', 'M', 'A', 'S');
    List<Character> SAMX = List.of('S', 'A', 'M', 'X');
    return (int)
        validLines.stream()
            .filter(seen::add)
            .map(line -> extractWord(line, matrix))
            .filter(word -> word.equals(XMAS) || word.equals(SAMX))
            .count();
  }

  public static boolean formsXmasCross(List<Line> validLines, char[][] matrix) {
    List<Character> MAS = List.of('M', 'A', 'S');
    List<Character> SAM = List.of('S', 'A', 'M');
    List<Line> diagonals = validLines.stream().filter(Line::isDiagonal).toList();
    if (diagonals.size() < 4) {
      // if it does not contain 4 short diagonals, it cannot be a cross, fail fast
      return false;
    }
    List<Line> longDiagonals = concatShortDiagonals(diagonals);
    return (longDiagonals.stream()
        .filter(seen::add)
        .map(line -> extractWord(line, matrix))
        .allMatch(word -> word.equals(SAM) || word.equals(MAS)));
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

  private static List<Line> concatShortDiagonals(List<Line> shortDiagonals) {
    // 0 is up left, 1 is up right, 2 is down left, 3 is down right
    Coordinate upLeftToDownRightStart =
        new Coordinate(shortDiagonals.get(0).to.x(), shortDiagonals.get(0).to.y());
    Coordinate upLeftToDownRightEnd =
        new Coordinate(shortDiagonals.get(3).to.x(), shortDiagonals.get(3).to.y());
    Line upLeftToDownRight = new Line(upLeftToDownRightStart, upLeftToDownRightEnd);

    Coordinate upRightToDownLeftStart =
        new Coordinate(shortDiagonals.get(1).to.x(), shortDiagonals.get(1).to.y());
    Coordinate upRightToDownLeftEnd =
        new Coordinate(shortDiagonals.get(2).to.x(), shortDiagonals.get(2).to.y());
    Line upRightToDownLeft = new Line(upRightToDownLeftStart, upRightToDownLeftEnd);
    return List.of(upLeftToDownRight, upRightToDownLeft);
  }
}
