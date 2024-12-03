package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

internal class DefaultCacheFileStoreTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val fileStore = DefaultCacheFileStore(context)

    @After
    fun cleanUp() {
        fileStore.deleteAll()
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
