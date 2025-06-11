package nl.rijksoverheid.mgo.component.pdfViewer

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import timber.log.Timber
import java.io.File

/**
 * Converts all pages of a PDF file into a list of Bitmaps.
 *
 * @param pdf The PDF file to render.
 * @return A list of Bitmap objects, each representing a page in the PDF.
 */
fun createBitmaps(pdf: File): List<Bitmap> {
  val bitmaps = mutableListOf<Bitmap>()

  // Open the PDF file in read-only mode
  ParcelFileDescriptor.open(pdf, ParcelFileDescriptor.MODE_READ_ONLY)?.use { fileDescriptor ->
    // Create a PdfRenderer from the file descriptor
    PdfRenderer(fileDescriptor).use { pdfRenderer ->
      // Iterate through all pages in the PDF
      for (pageIndex in 0 until pdfRenderer.pageCount) {
        try {
          // Open the current page
          pdfRenderer.openPage(pageIndex).use { page ->
            // Render the page to a Bitmap and add it to the list
            bitmaps.add(createBitmap(page))
          }
        } catch (e: Exception) {
          Timber.e(e, "Failed to render page $pageIndex")
        }
      }
    }
  }

  return bitmaps
}

/**
 * Renders a single PDF page to a Bitmap.
 *
 * @param page The PDF page to render.
 * @return A Bitmap representing the rendered page.
 */
private fun createBitmap(page: PdfRenderer.Page): Bitmap =
  androidx.core.graphics.createBitmap(page.width, page.height).apply {
    eraseColor(android.graphics.Color.WHITE) // Fill the background with white
    page.render(this, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY) // Render the page onto the Bitmap
  }
