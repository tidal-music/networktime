#!/bin/bash
set -e

print_usage()
{
  echo "Usage: $0 -i <input_file> -n <manifest_name> -s <scanned_at> -l <source_location>"
}

while getopts ":i:n:s:l:" OPT; do
  case $OPT in
    i) INPUT_FILE="$OPTARG"
    ;;
    n) MANIFEST_NAME="$OPTARG"
    ;;
    s) SCANNED_AT="$OPTARG"
    ;;
    l) SOURCE_LOCATION="$OPTARG"
    ;;
    ?) print_usage
       exit 1
    ;;
  esac
done
if [ -z "${INPUT_FILE+x}" ]; then
  print_usage
  exit 1
fi
if [ -z "${MANIFEST_NAME+x}" ]; then
  print_usage
  exit 1
fi
if [ -z "${SCANNED_AT+x}" ]; then
  print_usage
  exit 1
fi
if [ -z "${SOURCE_LOCATION+x}" ]; then
  print_usage
  exit 1
fi

JSON=$(jq --null-input \
--argjson VERSION "$(git rev-list --count HEAD)" \
--arg SHA "$GITHUB_SHA" \
--arg REF "$GITHUB_REF" \
--arg CORRELATOR "$GITHUB_WORKFLOW"_"$GITHUB_JOB" \
--arg RUN_ID "$GITHUB_RUN_ID" \
--arg HTML_URL "$GITHUB_SERVER_URL/$GITHUB_REPOSITORY/actions/runs/$GITHUB_RUN_ID" \
--arg DETECTOR_NAME "$GITHUB_REPOSITORY" \
--arg DETECTOR_VERSION 4 \
--arg DETECTOR_URL "$GITHUB_SERVER_URL/$GITHUB_REPOSITORY" \
--arg SCANNED "$SCANNED_AT" \
--arg MANIFEST_NAME "$MANIFEST_NAME" \
--arg SOURCE_LOCATION "$SOURCE_LOCATION" \
'
{
  "version":$VERSION,
  "sha":$SHA,
  "ref":$REF,
  "job":{
    "correlator":$CORRELATOR,
    "id":$RUN_ID,
    "html_url":$HTML_URL
  },
  "detector":{
    "name":$DETECTOR_NAME,
    "version":$DETECTOR_VERSION,
    "url":$DETECTOR_URL
  },
  "scanned":$SCANNED,
  "manifests":{
    ($MANIFEST_NAME):{
      "name":$MANIFEST_NAME,
      "file": {
        "source_location":$SOURCE_LOCATION
      },
      "resolved":{
      }
    }
  }
}
')

while IFS= read -r LINE
do JSON=$(jq '.manifests.'"$MANIFEST_NAME"'.resolved += {"'"$LINE"'": {"package_url": "pkg:maven/'"$(echo "$LINE" | tr ':' '/' | sed 's/\(.*\)\//\1@/')"'"}}' <<< "$JSON")
done < "$INPUT_FILE"

jq -r tostring <<< "$JSON"
