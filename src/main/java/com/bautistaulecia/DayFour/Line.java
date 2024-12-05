package com.bautistaulecia.DayFour;

// I'm sure there is a Vector class that could do this but since its bidirectional and i don't want
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

  @Override
  public String toString() {
    return "Line{" + "from=" + from + ", to=" + to + '}';
  }
}
