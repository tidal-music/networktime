#!/bin/bash
set -e

KTLINT_VERSION="0.50.0"
TARGET="out/ktlint-$KTLINT_VERSION/ktlint"

if [ ! -f "$TARGET" ]; then
  mkdir -p "$(dirname "$TARGET")"
  curl -L "https://github.com/pinterest/ktlint/releases/download/$KTLINT_VERSION/ktlint" > "$TARGET"
  chmod u+x "$TARGET"
fi

# shellcheck disable=SC2046
"$TARGET" --reporter plain $(git ls-tree --full-tree --name-only -r HEAD | grep -e "\.kt" -e "\.kts")
