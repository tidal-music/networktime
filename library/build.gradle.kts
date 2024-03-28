plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization")
}

group = "com.tidal.network-time"

kotlin {
  jvm()
  listOf(
    macosX64(),
    macosArm64(),
    iosX64(),
    iosArm64(),
    iosSimulatorArm64(),
    tvosSimulatorArm64(),
    tvosX64(),
    tvosArm64(),
  ).forEach {
    it.binaries.framework {
      baseName = project.name
      binaryOption("bundleId", "com.tidal.networktime")
      isStatic = true
    }
  }

  applyDefaultHierarchyTemplate()

  sourceSets {
    commonMain.get().dependencies {
      api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
      implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
      implementation("com.squareup.okio:okio:3.6.0")
      implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-okio:1.6.1")
    }
  }
}
