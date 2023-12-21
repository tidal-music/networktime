import java.nio.file.Paths

rootProject.name = "network-time"

include("library")
listOf("android", "desktop", "shared")
  .forEach {
    include(":samples-$it")
    project(":samples-$it").projectDir = Paths.get("samples").resolve(it).toFile()
  }
