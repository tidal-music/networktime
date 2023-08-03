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
  androidNativeArm32()
  androidNativeArm64()
  androidNativeX86()
  androidNativeX64()
  listOf(
    macosX64(),
    macosArm64(),
    iosX64(),
    watchosSimulatorArm64(),
    watchosX64(),
    watchosArm32(),
    watchosArm64(),
    tvosSimulatorArm64(),
    tvosX64(),
    tvosArm64(),
    iosArm64(),
    watchosDeviceArm64(),
  ).forEach {
    it.binaries.framework {
      baseName = project.name
    }
  }
  js(IR) {
    browser()
    nodejs()
    binaries.executable()
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
      }
    }
  }
}

android {
  compileSdk = 33
  defaultConfig {
    minSdk = 1
  }
  namespace = "com.tidal.networktime"
}
