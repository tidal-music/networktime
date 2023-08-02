buildscript {
  repositories {
    gradlePluginPortal()
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
  }
}

allprojects {
  repositories {
    mavenCentral()
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
