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
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}
