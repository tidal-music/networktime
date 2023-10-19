plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version "1.5.1"
  id("com.android.application")
}

kotlin {
  androidTarget {
    compilations.all {
      kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
      }
    }
  }
  sourceSets {
    val androidMain by getting {
      dependencies {
        implementation(project(":shared"))
        implementation("androidx.activity:activity-compose:1.7.2")
        implementation(platform("androidx.compose:compose-bom:2023.03.00"))
        implementation("androidx.compose.material3:material3")
      }
    }
  }
}

android {
  compileSdk = 33
  defaultConfig {
    minSdk = 21
  }
  namespace = "com.tidal.networktime.sample"
}
