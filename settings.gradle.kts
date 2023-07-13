import java.nio.file.Paths

rootProject.name = "networktime"

pluginManagement {
  includeBuild("buildlogic")
  repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
  }
}

include(":networktime")
include(":networktime-singletons")
listOf("android", "jvm", "shared")
  .forEach {
    include(":samples-multiplatform-kotlin-$it")
    project(":samples-multiplatform-kotlin-$it").projectDir = Paths.get("samples")
      .resolve("multiplatform-kotlin")
      .resolve(it)
      .toFile()
  }

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
}
