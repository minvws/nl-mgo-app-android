package nl.rijksoverheid.mgo.component.pdfViewer

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class PdfFileRepository
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) {
    private val dir =
      File(context.cacheDir, "pdf").apply {
        if (!exists()) mkdirs()
      }

    fun get(fileName: String): File = File(dir, fileName)

    fun clearAll() {
      dir.deleteRecursively()
    }
  }
