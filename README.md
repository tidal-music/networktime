# networktime

A Kotlin multiplatform implementation of an SNTP client.

## Importing

<details open>
<summary><b>Gradle</b></summary>

```kotlin
repositories {
  mavenCentral()
}

dependencies {
  implementation("com.tidal.networktime:networktime:$VERSION")
}
```

</details>

<details open>
<summary><b>Swift Package Manager</b></summary>

```swift
dependencies: [
    .package(
        url: "https://github.com/tidal-music/networktime.git",
        .upToNextMajor(from: "$VERSION")
      )
]
```

If you plan to use this tool from Objective-C, all public API symbols are prefixed with TNT (for
TidalNetworkTime) to avoid naming conflicts.

</details>

Version numbers can be found under [Releases](https://github.com/tidal-music/networktime/releases).

## Usage

Create your `SNTPClient` via its constructor. Its API allows you to toggle synchronization (starts
off) and to retrieve the time based on the latest successful synchronization.

The nullable property `epochTime` retrieves the aforementioned time will return `null` if no
synchronization has occurred successfully during the lifetime of the process and no backup file has
been specified for the `SNTPClient` instance or said file contains no valid prior synchronization
data.

As an alternative, the `blockingEpochTime` method can be used to suspend the caller until a valid
synchronization or backup restoration occurs to avoid a nullable return type.

## networktime-singletons

This is a tiny utility on top of the base artifact that extends the `SNTPClient` API by adding a new
property, `SNTPClient.singleton`, which allows you to pin `SNTPClient` instances into memory, keyed
uniquely by their constructor parameters (ordering of elements in collection-like parameters is
ignored). Invoking the property on an `SNTPClient` whose parameters result in a key not
corresponding to an already pinned instance will associate the key to said instance, pin it into
memory and return the receiver, and invoking it on an `SNTPClient` whose parameters result in a key
of an already-pinned association will return the pinned instance, allowing for the original receiver
to be freely garbage-collected when applicable. `SNTPClient.singleton` is thread-safe.

While using the core artifact instead is strongly recommended whenever possible, this one may be
useful if your codebase requires exactly the same clock reference using exactly the same
synchronization pacing across multiple places, but it does not feature integration points between
these that allow you to share a single `SNTPClient` instance.
