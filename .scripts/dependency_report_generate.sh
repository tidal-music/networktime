#!/bin/bash
set -e

DIR_TMP="build/report_dependencies"
rm -rf $DIR_TMP || true
mkdir -p $DIR_TMP

print_usage()
{
  echo "Usage: $0 -m <module> -c <configuration>"
}

while getopts ":m:c:" OPT; do
  case $OPT in
    m) MODULE="$OPTARG"
    ;;
    c) CONFIGURATION="$OPTARG"
    ;;
    ?) print_usage
       exit 1
    ;;
  esac
done

read -r -d '' -a WITH_ADJUSTED < <(./gradlew --console=plain "$MODULE":dependencies --configuration "$CONFIGURATION" |  grep --color=never -o "\S*:.*:.*" |  grep --color=never -v "/" |  awk 'NR > 1' |  tr -d " (*)" && printf '\0' )

RESOLVED=()
REGEX_PATTERN_DEPENDENCY_WITH_VERSION_UPGRADED='.*:.*:.*->.*'
for DEPENDENCY in "${WITH_ADJUSTED[@]}"
do
  if [[ "$DEPENDENCY" =~ $REGEX_PATTERN_DEPENDENCY_WITH_VERSION_UPGRADED ]]; then
    RESOLVED+=("$(echo "$DEPENDENCY" | grep -o ".*:.*:")$(echo "$DEPENDENCY" | cut -d ">" -f2)")
  else
    RESOLVED+=("$DEPENDENCY")
  fi
done
FILE_TMP=$DIR_TMP/"tmp"
for DEPENDENCY in "${RESOLVED[@]}"
do
  echo "$DEPENDENCY" >> $FILE_TMP
done
sort -u $FILE_TMP
