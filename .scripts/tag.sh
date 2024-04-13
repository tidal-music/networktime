#!/bin/bash
set -e

usage() {
  echo "Usage: $0 -v <version>" >&2
  exit 1
}

VERSION=""

if [ -n "$(git status --porcelain)" ]; then
  echo "Cannot tag from a dirty working tree"
  exit 1
fi

while getopts ":v:" opt; do
  case $opt in
  v)
    VERSION=$OPTARG
    ;;
  \?)
    echo "Invalid option: -$OPTARG" >&2
    exit 1
    ;;
  *)
    usage
    ;;
  esac
done

if [ -z "$VERSION" ]; then
  usage
fi

FILE_CHANGELOG="changelog/$VERSION"
if [ ! -f "$FILE_CHANGELOG" ]; then
  echo "Changelog file $FILE_CHANGELOG missing"
  exit 1
fi

git fetch &&
  git tag "${VERSION}" "HEAD" -m "${VERSION}" &&
  git push --tags &&
  echo "New release ${VERSION} for revision $(git rev-parse --short "HEAD") requested successfully"
