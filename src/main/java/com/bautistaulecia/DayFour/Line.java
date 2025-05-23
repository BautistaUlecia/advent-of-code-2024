package com.bautistaulecia.DayFour;

import com.bautistaulecia.Util.Coordinate;

// I'm sure there is a Vector class that could do this but since its bidirectional and I don't want
// To make the gods of math angry, ill just implement it myself
public class Line {
  // Not really a 'from' and 'to' since no direction is involved
  Coordinate from;
  Coordinate to;

  public Line(Coordinate from, Coordinate to) {
    this.from = from;
    this.to = to;
  }

  public Coordinate getFrom() {
    return from;
  }

  public Coordinate getTo() {
    return to;
  }

  public Boolean isDiagonal() {
    // A line is diagonal if both x and y are different in end
    return (!from.x().equals(to.x())) && (!from.y().equals(to.y()));
  }

  @Override
  public String toString() {
    return "Line{" + "from=" + from + ", to=" + to + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Line other)) return false;
    // A Line is equal if the points match in either order
    return (from.equals(other.from) && to.equals(other.to))
        || (from.equals(other.to) && to.equals(other.from));
  }

  @Override
  public int hashCode() {
    return from.hashCode() + to.hashCode();
  }
}
