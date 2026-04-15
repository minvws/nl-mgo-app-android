package nl.rijksoverheid.mgo.component.pdf

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

typealias MgoPdfFileName = String

class MgoPdfStore
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) {
    private val dir =
      File(context.cacheDir, "pdf").apply {
        if (!exists()) mkdirs()
      }

    fun get(fileName: MgoPdfFileName): File = File(dir, fileName)

    fun clear() {
      dir.deleteRecursively()
    }
  }
