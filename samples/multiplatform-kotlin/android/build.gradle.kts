import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
  androidTarget {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_1_8)
      freeCompilerArgs.add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
    }
  }
  sourceSets {
    androidMain.dependencies {
      implementation(project(":samples-multiplatform-kotlin-shared"))
      implementation(libs.androidx.activity.activity.compose)
      implementation(dependencies.platform(libs.androidx.compose.bom))
      implementation(libs.androidx.compose.material3)
    }
  }
}

android {
  compileSdk = 34
  defaultConfig {
    minSdk = 26
    targetSdk = 34
  }
  namespace = "com.tidal.networktime.sample.android"
}
