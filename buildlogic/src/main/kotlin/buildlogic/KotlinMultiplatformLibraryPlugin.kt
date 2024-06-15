package buildlogic

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal class KotlinMultiplatformLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    val customExtension = extensions.create(
      "buildlogicKotlinMultiplatformLibrary",
      KotlinMultiplatformLibraryPluginExtension::class.java,
    )
    val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    pluginManager.apply(
      libs.findPlugin("kotlin-multiplatform")
        .get()
        .get()
        .pluginId,
    )
    pluginManager.apply(
      libs.findPlugin("vanniktech-publish")
        .get()
        .get()
        .pluginId,
    )
    group = "com.tidal.networktime"
    configure<MavenPublishBaseExtension> {
      pom {
        name.set(project.name)
        afterEvaluate {
          this@pom.description.set(customExtension.pomDescription.get())
        }
        inceptionYear.set("2023")
        url.set("https://github.com/tidal-music/networktime")
        developers {
          developer {
            id.set("tidal")
            name.set("TIDAL")
          }
        }
        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }
        scm {
          connection.set("scm:git:git://github.com/tidal-music/networktime.git")
          developerConnection.set("scm:git:ssh://github.com:tidal-music/networktime.git")
          url.set("https://github.com/tidal-music/networktime/tree/master")
        }
      }
      configure(KotlinMultiplatform(JavadocJar.Empty(), true))
      signAllPublications()
      publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)
    }
    configure<KotlinMultiplatformExtension> {
      applyDefaultHierarchyTemplate()
      jvm()
    }
  }
}
