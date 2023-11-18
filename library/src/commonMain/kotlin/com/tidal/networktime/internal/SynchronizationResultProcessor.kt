package com.tidal.networktime.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import kotlinx.serialization.json.okio.encodeToBufferedSink
import okio.FileSystem
import okio.Path

@OptIn(ExperimentalSerializationApi::class)
internal class SynchronizationResultProcessor(
  val mutableState: MutableState,
  val backupFilePath: Path?,
  val fileSystem: FileSystem = FileSystemSupplier().system,
) {
  var synchronizationResult: SynchronizationResult?
    get() {
      val value = mutableState.synchronizationResult
      if (value == null && backupFilePath != null) {
        try {
          fileSystem.read(backupFilePath) {
            mutableState.synchronizationResult = Json.decodeFromBufferedSource(this)
          }
        } catch (_: Throwable) {
        }
      }
      return value
    }
    set(value) {
      mutableState.synchronizationResult = value
      if (backupFilePath != null && value != null) {
        try {
          fileSystem.write(backupFilePath) {
            Json.encodeToBufferedSink(value, this)
          }
        } catch (_: Throwable) {
        }
      }
    }
}
