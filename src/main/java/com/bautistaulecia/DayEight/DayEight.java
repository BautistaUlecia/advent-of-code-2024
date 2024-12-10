package com.bautistaulecia.DayEight;

import com.bautistaulecia.Util.Coordinate;
import com.bautistaulecia.Util.FileParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayEight {
  private static final Logger LOGGER = LoggerFactory.getLogger(DayEight.class);

  public static void solve() {
    char[][] matrix = FileParser.toSquareMatrix("src/main/resources/DayEight/input.txt");
    Map<Character, List<Coordinate>> antennasGroupedByLetter = findAndGroupAntennas(matrix);
    int result = countAntinodesInBounds(antennasGroupedByLetter, matrix);
    LOGGER.info("Result = {}", result);
  }

  public static Map<Character, List<Coordinate>> findAndGroupAntennas(char[][] matrix) {
    Map<Character, List<Coordinate>> antennas = new HashMap<Character, List<Coordinate>>();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        if (matrix[i][j] != '.') {
          antennas.computeIfAbsent(matrix[i][j], k -> new ArrayList<>()).add(new Coordinate(j, i));
        }
      }
    }
    return antennas;
  }

  public static int countAntinodesInBounds(
      Map<Character, List<Coordinate>> antennasGroupedByLetter, char[][] matrix) {
    Set<Coordinate> antinodesForPartOne = new HashSet<>();
    Set<Coordinate> antinodesForPartTwo = new HashSet<>();
    for (Map.Entry<Character, List<Coordinate>> entry : antennasGroupedByLetter.entrySet()) {
      List<Coordinate> coordinates = entry.getValue();
      for (int i = 0; i < coordinates.size() - 1; i++) {
        for (int j = i + 1; j < coordinates.size(); j++) {
          Coordinate firstAntenna = coordinates.get(i);
          Coordinate secondAntenna = coordinates.get(j);
          Integer deltaX = secondAntenna.x() - firstAntenna.x();
          Integer deltaY = secondAntenna.y() - firstAntenna.y();

          // first - delta
          addIfValidAntinode(
              antinodesForPartOne,
              matrix,
              new Coordinate(firstAntenna.x() - deltaX, firstAntenna.y() - deltaY));

          // second + delta
          addIfValidAntinode(
              antinodesForPartOne,
              matrix,
              new Coordinate(secondAntenna.x() + deltaX, secondAntenna.y() + deltaY));

          // Part two (add all valid coordinates in added antinode + delta)
          antinodesForPartTwo.addAll(
              findAllAntinodesForPartTwo(matrix, firstAntenna, secondAntenna, deltaX, deltaY));
        }
      }
    }
    // return antinodesForPartOne.size(); // Part one
    return antinodesForPartTwo.size();
  }

  private static boolean addIfValidAntinode(
      Set<Coordinate> antinodes, char[][] matrix, Coordinate antinode) {
    if (isWithinBounds(antinode, matrix)) {
      antinodes.add(antinode);
      return true;
    }
    return false;
  }

  public static boolean isWithinBounds(Coordinate coordinate, char[][] matrix) {
    return coordinate.x() >= 0
        && coordinate.y() >= 0
        && coordinate.x() < matrix.length
        && coordinate.y() < matrix.length;
  }

  public static Set<Coordinate> findAllAntinodesForPartTwo(
      char[][] matrix,
      Coordinate first,
      Coordinate second,
      Integer originalDeltaX,
      Integer originalDeltaY) {
    Set<Coordinate> result = new HashSet<>();
    Integer deltaX = originalDeltaX;
    Integer deltaY = originalDeltaY;

    while (addIfValidAntinode(
        result, matrix, new Coordinate(first.x() + deltaX, first.y() + deltaY))) {
      deltaX += originalDeltaX;
      deltaY += originalDeltaY;
    }

    deltaX = originalDeltaX;
    deltaY = originalDeltaY;

    while (addIfValidAntinode(
        result, matrix, new Coordinate(second.x() - deltaX, second.y() - deltaY))) {
      deltaX += originalDeltaX;
      deltaY += originalDeltaY;
    }
    return result;
  }
}
