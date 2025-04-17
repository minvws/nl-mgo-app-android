package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.test.core.app.ApplicationProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@RunWith(RobolectricTestRunner::class)
internal class DefaultEncryptedEncryptedFileStoreTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val fileStore = DefaultEncryptedEncryptedFileStore(context, "123")
  private val dir = File(context.filesDir, "encrypted")

  private val json = Json

  @Serializable
  data class TestData(
    val id: Int,
    val name: String,
  )

  private fun mockEncryptedFile(
    fileName: String,
    testData: TestData,
  ) {
    mockkStatic("nl.rijksoverheid.mgo.framework.storage.file.EncryptedFileKt")
    val file = File(dir, fileName)
    val mockEncryptedFile: EncryptedFile = mockk()
    every { createEncryptedFile(any(), any(), any()) } returns mockEncryptedFile
    every { readEncryptedFile(any()) } answers {
      val inputStream = FileInputStream(file)
      inputStream.bufferedReader().use { it.readText() }
    }
    every { writeEncryptedFile(any(), any()) } answers {
      FileOutputStream(file).use { outputStream ->
        val data = json.encodeToString(TestData.serializer(), testData)
        outputStream.write(data.toByteArray())
      }
    }
  }

  @Test
  fun given_saved_file_when_getting_file_then_return_file_content() =
    runTest {
      // Given
      val testData = TestData(id = 5, name = "Hello World")
      val fileName = "testdata.json"
      mockEncryptedFile(fileName = fileName, testData = testData)
      fileStore.saveFile(value = testData, name = fileName, clazz = TestData::class)

      // When
      val fileContent = fileStore.getFile(clazz = TestData::class, name = "testdata.json")

      // Then
      assertEquals(testData, fileContent)
    }

  @Test
  fun given_saved_file_when_saving_file_then_no_error() =
    runTest {
      // Given
      val testData = TestData(id = 5, name = "Hello World")
      val fileName = "testdata.json"
      mockEncryptedFile(fileName = fileName, testData = testData)
      fileStore.saveFile(value = testData, name = fileName, clazz = TestData::class)

      // When
      val testData2 = TestData(id = 6, name = "Hello World 2")
      fileStore.saveFile(value = testData2, name = fileName, clazz = TestData::class)

      // Then no errors are thrown
    }

  @Test
  fun given_no_saved_file_When_getting_file_Then_return_null() =
    runTest {
      // Given no saved file

      // When
      val fileContent = fileStore.getFile(clazz = TestData::class, name = "testdata.json")

      // Then
      assertNull(fileContent)
    }

  @Test
  fun given_saved_file_when_calling_delete_then_remove_file() =
    runTest {
      // Given: saved testdata.json file
      val fileName = "testdata.json"
      val testData = TestData(id = 5, name = "Hello World")
      mockEncryptedFile(fileName = fileName, testData = testData)
      fileStore.saveFile(value = testData, name = fileName, clazz = TestData::class)

      // When: calling delete testdata.json
      fileStore.deleteFile("testdata.json")

      // Then: File is deleted
      val file = fileStore.getFile(clazz = TestData::class, name = fileName)
      assertNull(file)
    }

  @Test
  fun given_saved_files_when_calling_delete_all_then_remove_files() =
    runTest {
      // Given: two saved files
      val testData = TestData(id = 5, name = "Hello World")

      val file1 = "testdata1.json"
      mockEncryptedFile(fileName = file1, testData = testData)
      fileStore.saveFile(value = testData, name = file1, clazz = TestData::class)

      val file2 = "testdata2.json"
      mockEncryptedFile(fileName = file2, testData = testData)
      fileStore.saveFile(value = testData, name = file2, clazz = TestData::class)

      // When: calling deleteAll
      fileStore.deleteAll()

      // Then: All files are deleted
      assertNull(fileStore.getFile(clazz = TestData::class, name = file1))
      assertNull(fileStore.getFile(clazz = TestData::class, name = file2))
    }
}
