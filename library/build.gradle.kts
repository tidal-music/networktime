plugins {
  kotlin("multiplatform")
  id("com.android.library")
}

group = "com.tidal.network-time"

kotlin {
  targetHierarchy.default()

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

  sourceSets {
    commonMain.get().dependencies {
      api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
      implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
    }
    val jvmMain by getting {}
    val androidMain by getting {
      dependsOn(jvmMain)
    }
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
