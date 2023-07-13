package com.tidal.networktime.internal

import okio.FileSystem

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class FileSystemSupplier {
  actual val system = FileSystem.SYSTEM
}
