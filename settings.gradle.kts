import java.nio.file.Paths

rootProject.name = "network-time"

include("library")
listOf("android", "jvm", "shared")
  .forEach {
    include(":samples-multiplatform-kotlin-$it")
    project(":samples-multiplatform-kotlin-$it").projectDir = Paths.get("samples")
      .resolve("multiplatform-kotlin")
      .resolve(it)
      .toFile()
  }
