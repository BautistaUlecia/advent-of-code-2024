package com.bautistaulecia.DaySix;

import com.bautistaulecia.Util.Coordinate;
import com.bautistaulecia.Util.Direction;

public class Guard {
  Coordinate position;
  Direction direction;

  public Guard(Coordinate position, Direction direction) {
    this.position = position;
    this.direction = direction;
  }

  public Coordinate getPosition() {
    return position;
  }

  public Direction getDirection() {
    return direction;
  }

  public void changeDirectionClockwise() {
    this.direction = this.direction.getNextDirectionClockwise();
  }
}
