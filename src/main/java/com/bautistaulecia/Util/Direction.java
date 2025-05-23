package com.bautistaulecia.Util;

public enum Direction {
  LEFT(-1, 0),
  RIGHT(1, 0),
  UP(0, -1),
  DOWN(0, 1);

  private final int deltaX;
  private final int deltaY;

  Direction(int deltaX, int deltaY) {
    this.deltaX = deltaX;
    this.deltaY = deltaY;
  }

  public int getDeltaX() {
    return deltaX;
  }

  public int getDeltaY() {
    return deltaY;
  }

  public Direction getNextDirectionClockwise() {
    return switch (this) {
      case UP -> RIGHT;
      case RIGHT -> DOWN;
      case DOWN -> LEFT;
      case LEFT -> UP;
    };
  }
}
