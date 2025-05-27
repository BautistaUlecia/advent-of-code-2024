#!/bin/bash

if [ -z "$1" ]; then
  echo "Usage: $0 DayName"
  exit 1
fi

DAY_NAME="$1"

mkdir -p "$DAY_NAME"
touch "$DAY_NAME/test.txt"

JAVA_SRC_PATH="./../java/com/bautistaulecia/$DAY_NAME"
mkdir -p "$JAVA_SRC_PATH"

JAVA_FILE="$JAVA_SRC_PATH/$DAY_NAME.java"
cat <<EOF > "$JAVA_FILE"
package com.bautistaulecia.$DAY_NAME;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class $DAY_NAME {
    private static final Logger LOGGER = LoggerFactory.getLogger($DAY_NAME.class);
    public static void solve() {}
}
EOF


echo "Created $DAY_NAME with test.txt and $DAY_NAME.java"
