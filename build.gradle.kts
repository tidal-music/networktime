buildscript {
  repositories {
    gradlePluginPortal()
    google()
  }
  dependencies {
    val kotlinVersion = "1.9.20"
    classpath(kotlin("gradle-plugin", version = kotlinVersion))
    classpath(kotlin("serialization", version = kotlinVersion))
    classpath("com.android.tools.build:gradle:8.1.4")
    classpath("org.jetbrains.compose:compose-gradle-plugin:1.5.10")
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}
