plugins {
  `kotlin-dsl`
}

dependencies {
  compileOnly(gradleApi())
  implementation(libs.plugin.android.build)
  implementation(libs.plugin.kotlin)
  implementation(libs.plugin.vanniktech.publish)
}

gradlePlugin {
  plugins {
    register("buildlogic-kotlin-multiplatform-library") {
      id = libs.plugins.buildlogic.kotlin.multiplatform.library.get().pluginId
      version = libs.plugins.buildlogic.kotlin.multiplatform.library.get().version
      implementationClass = "buildlogic.KotlinMultiplatformLibraryPlugin"
    }
  }
}
