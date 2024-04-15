# network-time

A Kotlin multiplatform implementation of an SNTP client.

## Importing

<details>
<summary><b>Maven</b></summary>

```kotlin
repositories {
  maven(url = "https://maven.pkg.github.com/tidal-music/network-time")
}

dependencies {
  implementation("com.tidal.networktime:library:$VERSION")
}
```

</details>

<details>
<summary><b>SwiftPM</b></summary>

```swift
.binaryTarget(
    url: "https://github.com/tidal-music/network-time/downloads/$VERSION/Library-$VERSION-xcodebuild-${XCODEVERSION}.xcframework.zip",
    checksum: "Contents of https://github.com/tidal-music/network-time/downloads/$VERSION/Library-$VERSION-xcodebuild-$XCODEVERSION.xcframework.zip.checksum"
)
```

</details>

## Usage

Create your `SNTPClient` via its constructor. Its API allows you to toggle synchronization (starts
off) and to retrieve the time based on the last successful synchronization, if any.

The property that retrieves the aforementioned time is nullable as it will return null if no
synchronization has occurred successfully during the lifetime of the process and no backup file has
been specified for the `SNTPClient` instance or said file contains no valid prior synchronization
data.
