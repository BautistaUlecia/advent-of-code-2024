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
    Set<Coordinate> antinodes = new HashSet<>() {};
    for (Map.Entry<Character, List<Coordinate>> entry : antennasGroupedByLetter.entrySet()) {
      List<Coordinate> coordinates = entry.getValue();
      for (int i = 0; i < coordinates.size() - 1; i++) {
        for (int j = i + 1; j < coordinates.size(); j++) {
          Integer deltaX = coordinates.get(j).x() - coordinates.get(i).x();
          Integer deltaY = coordinates.get(j).y() - coordinates.get(i).y();

          // A - delta
          Coordinate firstAntinode =
              new Coordinate(coordinates.get(i).x() - deltaX, coordinates.get(i).y() - deltaY);

          // B + delta
          Coordinate secondAntinode =
              new Coordinate(coordinates.get(j).x() + deltaX, coordinates.get(j).y() + deltaY);

          if (isWithinBounds(firstAntinode, matrix)) {
            antinodes.add(firstAntinode);
          }
          if (isWithinBounds(secondAntinode, matrix)) {
            antinodes.add(secondAntinode);
          }
        }
      }
    }
    return antinodes.size();
  }

  public static boolean isWithinBounds(Coordinate coordinate, char[][] matrix) {
    return coordinate.x() >= 0
        && coordinate.y() >= 0
        && coordinate.x() < matrix.length
        && coordinate.y() < matrix.length;
  }
}
