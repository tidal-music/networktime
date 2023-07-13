import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.net.URI

plugins {
  id("org.jetbrains.dokka")
  id("maven-publish")
  kotlin("multiplatform")
  kotlin("plugin.serialization")
}

group = "com.tidal.networktime"

kotlin {
  jvm()
  val xCFrameworkName = "Library"
  val xCFramework = XCFramework(xCFrameworkName)
  listOf(
    macosX64(),
    macosArm64(),
    iosX64(),
    iosArm64(),
    iosSimulatorArm64(),
    tvosSimulatorArm64(),
    tvosX64(),
    tvosArm64(),
  ).forEach {
    it.binaries.framework {
      baseName = xCFrameworkName
      binaryOption("bundleId", "com.tidal.networktime")
      isStatic = true
      xCFramework.add(this)
    }
    it.compilations.configureEach { cinterops.create("NetworkFrameworkWorkaround") }
  }

  applyDefaultHierarchyTemplate()

  sourceSets {
    commonMain.get().dependencies {
      api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
      implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
      implementation("com.squareup.okio:okio:3.6.0")
      implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-okio:1.6.1")
    }
  }
}

val htmlDoc by tasks.registering(Jar::class) {
  dependsOn(tasks.dokkaHtml)
  archiveClassifier = "documentation"
  from(layout.buildDirectory.dir("dokka").get().dir("html"))
}

publishing {
  repositories {
    maven {
      name = "GithubPackages"
      url = URI.create("https://maven.pkg.github.com/${System.getenv("GITHUB_REPOSITORY")}")
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }
  publications {
    getByName<MavenPublication>("kotlinMultiplatform") {
      pom {
        artifact(htmlDoc)
        inceptionYear.set("2023")
        url.set("https://github.com/tidal-music/network-time")
        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }
        scm {
          url.set("https://github.com/tidal-music/network-time")
          connection.set("scm:git:https://github.com/tidal-music/network-time.git")
          developerConnection.set("scm:git:ssh://git@github.com/tidal-music/network-time.git")
        }
      }
    }
  }
}
