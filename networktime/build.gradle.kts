import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  alias(libs.plugins.buildlogic.kotlin.multiplatform.library)
  alias(libs.plugins.kotlin.serialization)
}

buildlogicKotlinMultiplatformLibrary {
  pomDescription = "SNTP client for JVM, Android, native Apple and Kotlin Multiplatform hosts."
  xCFramework("TidalNetworkTime")
}

kotlin {
  targets.filterIsInstance<KotlinNativeTarget>()
    .forEach {
      it.compilations.configureEach { cinterops.create("NetworkFrameworkWorkaround") }
    }
  sourceSets {
    all {
      languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }
    commonMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.datetime)
      implementation(libs.kotlinx.serialization.json.okio)
      implementation(libs.okio)
    }
  }
}
