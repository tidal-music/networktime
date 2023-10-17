plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version "1.5.0"
}

kotlin {
  targetHierarchy.default()

  jvm()
  listOf(
    iosX64(),
    iosArm64(),
  ).forEach {
    it.binaries.framework {
      baseName = project.name
    }
  }

  sourceSets {
    commonMain.get().dependencies {
      dependencies {
        implementation(compose.runtime)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
        implementation(project(":library"))
      }
    }
  }
}
