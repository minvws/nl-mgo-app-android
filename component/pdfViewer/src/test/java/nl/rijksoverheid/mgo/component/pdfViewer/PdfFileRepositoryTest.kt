package nl.rijksoverheid.mgo.component.pdfViewer

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File

@RunWith(RobolectricTestRunner::class)
class PdfFileRepositoryTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val repository = PdfFileRepository(context)

  @Test
  fun testGet() {
    val file = repository.get("file.pdf")
    assertEquals("file.pdf", file.name)
  }

  @Test
  fun testClearAll() {
    // Given: File saved
    val dir = File(context.cacheDir, "pdf")
    val file = File(dir, "file.pdf")
    file.parentFile?.mkdirs()
    file.writeText("Hello World")

    // When: Calling clearAll
    repository.clearAll()

    // Then: Files no longer exists
    assertEquals(0, dir.listFiles()?.size ?: 0)
  }
}
