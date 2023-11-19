plugins {
  kotlin("multiplatform")
  id("com.android.library")
  kotlin("plugin.serialization")
}

group = "com.tidal.network-time"

kotlin {
  jvm()
  androidTarget {
    compilations.all {
      kotlinOptions {
        jvmTarget = "1.8"
      }
    }
  }
  listOf(
    macosX64(),
    macosArm64(),
    iosX64(),
    tvosSimulatorArm64(),
    tvosX64(),
    tvosArm64(),
    iosArm64(),
  ).forEach {
    it.binaries.framework {
      baseName = project.name
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
    androidMain.get().dependsOn(jvmMain.get())
  }
}

android {
  compileSdk = 33
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  defaultConfig {
    minSdk = 1
  }
  namespace = "com.tidal.networktime"
}
