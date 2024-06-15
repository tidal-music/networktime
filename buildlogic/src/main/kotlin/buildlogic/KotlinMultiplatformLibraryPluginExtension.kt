package buildlogic

import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import javax.inject.Inject

open class KotlinMultiplatformLibraryPluginExtension @Inject constructor(
  private val project: Project,
  objectFactory: ObjectFactory,
) {
  val pomDescription: Property<String> = objectFactory.property(String::class.java)

  fun xCFramework(xCFrameworkName: String) = with(project) {
    configure<KotlinMultiplatformExtension> {
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
        it.binaries
          .framework {
            baseName = "TNT"
            binaryOption("bundleId", xCFrameworkName)
            binaryOption("bundleShortVersionString", version as String)
            binaryOption("bundleVersion", version as String)
            isStatic = true
            xCFramework.add(this)
          }
      }
    }
  }
}
