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

public class $DAY_NAME {
}
EOF

echo "Created $DAY_NAME with test.txt and $DAY_NAME.java"
