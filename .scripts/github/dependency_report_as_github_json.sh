#!/bin/bash
set -e

DIR_TMP="build/report_dependencies_github_json"
rm -rf $DIR_TMP || true
mkdir -p $DIR_TMP

print_usage()
{
  echo "Usage: $0 -i <input_file> -n <manifest_name> -s <scanned_at>"
}

while getopts ":i:n:s:" OPT; do
  case $OPT in
    i) INPUT_FILE="$OPTARG"
    ;;
    n) MANIFEST_NAME="$OPTARG"
    ;;
    s) SCANNED_AT="$OPTARG"
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

JSON=$(jq --null-input \
--argjson VERSION 1 \
--arg SHA "$GITHUB_SHA" \
--arg REF "$GITHUB_REF" \
--arg CORRELATOR "$GITHUB_WORKFLOW"_"$GITHUB_JOB" \
--arg RUN_ID "$GITHUB_RUN_ID" \
--arg HTML_URL "$GITHUB_SERVER_URL/$GITHUB_REPOSITORY/actions/runs/$GITHUB_RUN_ID" \
--arg DETECTOR_NAME "$GITHUB_REPOSITORY" \
--arg DETECTOR_VERSION 1 \
--arg DETECTOR_URL "$GITHUB_SERVER_URL/$GITHUB_REPOSITORY" \
--arg SCANNED "$SCANNED_AT" \
--arg MANIFEST_NAME "$MANIFEST_NAME" \
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
      "resolved":{
      }
    }
  }
}
')

for LINE in $(cat $INPUT_FILE)
do
  JSON=$(jq '.manifests.'$MANIFEST_NAME'.resolved += {"'$LINE'": {}}' <<< $JSON)
done

jq -r tostring <<< $JSON
