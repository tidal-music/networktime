plugins {
  kotlin("jvm")
  id("org.jetbrains.compose") version "1.5.0"
}

dependencies {
  implementation(compose.desktop.currentOs)
}

compose.desktop {
  application {
    mainClass = "root.Main"
  }
}
