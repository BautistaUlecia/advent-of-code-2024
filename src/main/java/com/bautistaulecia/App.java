package com.bautistaulecia;

import com.bautistaulecia.DayEight.DayEight;

public class App {
  public static void main(String[] args) {
    // DayOne.solve();
    // DayTwo.solve();
    // DayThree.solve();
    // DayFour.solve();
    // DayFive.solve();
    // DaySix.solve();
    // DaySeven.solve();
    long startTime = System.nanoTime();
    DayEight.solve();
    long endTime = System.nanoTime();

    long duration = endTime - startTime;
    int durationInMillis = (int) (duration / 1_000_000);
    System.out.println("Execution time: " + durationInMillis + " ms");
  }
}
