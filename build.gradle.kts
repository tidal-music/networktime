buildscript {
  repositories {
    gradlePluginPortal()
    google()
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
    classpath("com.android.tools.build:gradle:8.1.2")
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}
