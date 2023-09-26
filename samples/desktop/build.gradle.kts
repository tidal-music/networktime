import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version "1.5.0"
}

kotlin {
  jvm {
    withJava()
  }
  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation(project(":shared"))
        implementation(compose.desktop.currentOs)
      }
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
