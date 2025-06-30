package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import android.webkit.MimeTypeMap
import androidx.test.core.app.ApplicationProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File

@RunWith(RobolectricTestRunner::class)
internal class DefaultCacheFileStoreTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val fileStore = DefaultCacheFileStore(context)

  @Before
  fun setup() {
    mockkStatic(MimeTypeMap::class)
    val mockMimeTypeMap = mockk<MimeTypeMap>()
    every { MimeTypeMap.getSingleton() } returns mockMimeTypeMap
    every { mockMimeTypeMap.getExtensionFromMimeType("application/pdf") } returns "pdf"
  }

  @Test
  fun testGetFile() {
    fileStore.saveFile("file.pdf", contentType = "application/pdf", base64Content = "SGVsbG8gV29ybGQ=")
    val expectedFile = File(context.cacheDir, "mgo/file.pdf")
    assertEquals(expectedFile, fileStore.getFile("file.pdf"))
  }

  @Test
  fun testSaveFile() {
    val file = fileStore.saveFile(name = "file.pdf", contentType = "application/pdf", base64Content = "SGVsbG8gV29ybGQ=")
    assertTrue(file.exists())
  }

  @Test
  fun testDeleteAll() {
    // Given: file exists in cache dir
    fileStore.saveFile(name = "file.pdf", contentType = "application/pdf", base64Content = "SGVsbG8gV29ybGQ=")

    // When: Calling deleteAll
    fileStore.deleteAll()

    // Then: No more files exists
    val cacheDir = File(context.cacheDir, "mgo")
    assertEquals(0, cacheDir.listFiles()?.size ?: 0)
  }
}
