plugins {
  kotlin("multiplatform")
  id("com.android.library")
}

group = "com.tidal.network-time"

kotlin {
  targetHierarchy.default()

  jvm()
  androidTarget()
  ios()
  linuxX64()
  macosX64()
  macosArm64()
  iosSimulatorArm64()
  iosX64()
  linuxArm64()
  watchosSimulatorArm64()
  watchosX64()
  watchosArm32()
  watchosArm64()
  tvosSimulatorArm64()
  tvosX64()
  tvosArm64()
  iosArm64()
  androidNativeArm32()
  androidNativeArm64()
  androidNativeX86()
  androidNativeX64()
  mingwX64()
  watchosDeviceArm64()
}
