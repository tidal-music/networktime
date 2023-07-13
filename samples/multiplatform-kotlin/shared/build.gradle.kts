plugins {
  alias(libs.plugins.compose)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
  applyDefaultHierarchyTemplate()
  jvm()
  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64(),
  ).forEach {
    it.binaries
      .framework {
        baseName = project.name
        binaryOption("bundleId", "TidalNetworkTimeSample")
        binaryOption("bundleShortVersionString", version as String)
        binaryOption("bundleVersion", version as String)
      }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.datetime)
      implementation(project(":networktime"))
    }
  }
}
