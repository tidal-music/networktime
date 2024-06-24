#!/bin/bash
set -e

XCODEBUILD_VERSIONS=("15.1" "15.4")

for XCODEBUILD_VERSION in "${XCODEBUILD_VERSIONS[@]}"
do
  sudo xcode-select -s /Applications/Xcode_"$XCODEBUILD_VERSION".app
  ./gradlew networktime:clean networktime:assembleTidalNetworkTimeReleaseXCFramework
  mv networktime/build/XCFrameworks/release/TidalNetworkTime.xcframework TidalNetworkTime-xcodebuild-"$XCODEBUILD_VERSION".xcframework
  ./gradlew networktime-singletons:clean networktime-singletons:assembleTidalNetworkTimeSingletonsReleaseXCFramework
  mv networktime-singletons/build/XCFrameworks/release/TidalNetworkTimeSingletons.xcframework TidalNetworkTimeSingletons-xcodebuild-"$XCODEBUILD_VERSION".xcframework
  git add -A -f
  git commit -m "XCFramework generation for version $TAG (xcodebuild $XCODEBUILD_VERSION)"
done
