import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  alias(libs.plugins.compose)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
  jvm {
    withJava()
  }
  sourceSets {
    jvmMain.dependencies {
      implementation(project(":samples-multiplatform-kotlin-shared"))
      implementation(compose.desktop.currentOs)
    }
  }
}

compose.desktop {
  application {
    mainClass = "root.MainKt"
    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Deb, TargetFormat.Msi)
      packageName = "${rootProject.name}-${project.rootDir.name}"
    }
  }
}
