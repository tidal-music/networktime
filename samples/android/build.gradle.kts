plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version "1.5.10"
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
    androidMain.get().dependencies {
      implementation(project(":shared"))
      implementation("androidx.activity:activity-compose:1.8.0")
      implementation(project.dependencies.platform("androidx.compose:compose-bom:2023.03.00"))
      implementation("androidx.compose.material3:material3")
    }
  }
}

android {
  compileSdk = 34
  defaultConfig {
    minSdk = 21
    targetSdk = 34
  }
  namespace = "com.tidal.networktime.sample"
}
