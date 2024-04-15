buildscript {
  repositories {
    gradlePluginPortal()
    google()
  }
  dependencies {
    val kotlinVersion = "1.9.23"
    // Should be $kotlinVersion when possible
    classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
    classpath(kotlin("gradle-plugin", version = kotlinVersion))
    classpath(kotlin("serialization", version = kotlinVersion))
    classpath("com.android.tools.build:gradle:8.1.4")
    classpath("org.jetbrains.compose:compose-gradle-plugin:1.6.1")
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}
