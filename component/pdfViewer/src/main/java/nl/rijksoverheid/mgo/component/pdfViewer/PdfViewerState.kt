package nl.rijksoverheid.mgo.component.pdfViewer

import java.io.File

sealed class PdfViewerState {
  data object Loading : PdfViewerState()

  data class Loaded(
    val file: File,
  ) : PdfViewerState()
}
