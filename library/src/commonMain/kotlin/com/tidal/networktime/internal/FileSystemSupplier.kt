package com.tidal.networktime.internal

import okio.FileSystem

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class FileSystemSupplier() {
  val system: FileSystem
}
