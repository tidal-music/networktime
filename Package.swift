// swift-tools-version:5.9
// The swift-tools-version declares the minimum version of Swift required to build this package.

import Foundation
import PackageDescription

#if swift(>=5.10)
  let xcodeBuildVersion = "15.4"
#elseif swift(>=5.9)
  let xcodeBuildVersion = "15.1"
#else
  fatalError("This version of Swift is unsupported (too old).")
#endif

let package = Package(
  name: "TidalNetworkTime",
  products: [
    .library(
      name: "TidalNetworkTime",
      targets: ["TidalNetworkTime"]
    ),
    .library(
      name: "TidalNetworkTimeSingletons",
      targets: ["TidalNetworkTime", "TidalNetworkTimeSingletons"]
    )
  ],
  targets: [
    .binaryTarget(
      name: "TidalNetworkTime",
      path: "TidalNetworkTime-xcodebuild-" + xcodeBuildVersion + ".xcframework"
    ),
    .binaryTarget(
      name: "TidalNetworkTimeSingletons",
      path: "TidalNetworkTimeSingletons-xcodebuild-" + xcodeBuildVersion + ".xcframework"
    ),
  ]
)

extension Target.Dependency {
  static let tidalNetworkTime = byName(name: "TidalNetworkTime")
}
