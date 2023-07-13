#!/bin/bash
set -e

BODY="{
  \"tag_name\": \"${GITHUB_REF_NAME}\",
  \"target_commitish\": \"${GITHUB_SHA}\",
  \"name\": \"${GITHUB_REF_NAME}\",
  \"body\": \"$(cat "$GITHUB_WORKSPACE"/changelog/"$GITHUB_REF_NAME")\"
}"

RESPONSE_BODY=$(curl -sL \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: Bearer ${GITHUB_TOKEN}" \
  -H "X-GitHub-Api-Version: 2022-11-28" \
  --request POST \
  --data "${BODY}" \
  https://api.github.com/repos/"${GITHUB_REPOSITORY}"/releases)

echo "$RESPONSE_BODY" | jq -r .upload_url
