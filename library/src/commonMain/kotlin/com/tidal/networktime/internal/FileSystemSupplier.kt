package com.tidal.networktime.internal

import okio.FileSystem

internal expect class FileSystemSupplier() {
  val system: FileSystem
}
