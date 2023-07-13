import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

kotlin {
  jvm {
    withJava()
  }
  sourceSets {
    jvmMain.get().dependencies {
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
