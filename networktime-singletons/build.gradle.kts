plugins {
  alias(libs.plugins.buildlogic.kotlin.multiplatform.library)
  alias(libs.plugins.kotlin.atomicfu)
}

buildlogicKotlinMultiplatformLibrary {
  androidNamespaceSuffix = ".singletons"
  pomDescription = "Singleton API extension."
  xCFramework("TidalNetworkTimeSingletons")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(project(":networktime"))
      implementation(libs.kotlinx.atomicfu)
    }
  }
}
