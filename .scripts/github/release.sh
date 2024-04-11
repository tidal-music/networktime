BODY="{
  \"tag_name\": \"${GITHUB_REF_NAME}\",
  \"target_commitish\": \"${GITHUB_SHA}\",
  \"name\": \"${GITHUB_REF_NAME}\",
  \"body\": \"$(cat ../../changelog/"$GITHUB_REF_NAME")\"
}"

RESPONSE_BODY=$(curl -s \
  -u "${GITHUB_ACTOR}":"${GITHUB_TOKEN}" \
  --header "Accept: application/vnd.github.v3+json" \
  --header "Content-Type: application/json; charset=utf-8" \
  --request POST \
  --data "${BODY}" \
  https://api.github.com/repos/"${GITHUB_REPOSITORY}"/releases)

echo "$RESPONSE_BODY" | jq -r .upload_url
