package com.tidal.networktime.internal

import okio.FileSystem

internal actual class FileSystemSupplier {
  actual val system = FileSystem.SYSTEM
}
