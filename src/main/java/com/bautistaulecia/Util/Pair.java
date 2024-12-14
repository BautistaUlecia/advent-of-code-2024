package com.bautistaulecia.Util;

public record Pair<F, S>(F first, S second) {
  public F first() {
    return first;
  }

  public S second() {
    return second;
  }
}
