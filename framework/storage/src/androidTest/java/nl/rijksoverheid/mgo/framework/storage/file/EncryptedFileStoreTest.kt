package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable

@HiltAndroidTest
internal class EncryptedFileStoreTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var fileStore: EncryptedFileStore

    @Before
    fun init() {
        hiltRule.inject()
        fileStore =
            EncryptedFileStore(
                context = context,
                masterKeyAlias = "123",
            )
    }

    @Serializable
    data class TestData(
        val id: Int,
        val name: String,
    )

    @After
    fun cleanUp() =
        runTest {
            // After each test, make sure there are not more files in the cache dir.
            val context = ApplicationProvider.getApplicationContext<Context>()
            try {
                val dir = File(context.filesDir, "encrypted")
                dir.deleteRecursively()
            } catch (e: Exception) {
                // Never crash
            }
        }

    @Test
    fun given_saved_file_when_getting_file_then_return_file_content() =
        runTest {
            // Given
            val testData = TestData(id = 5, name = "Hello World")
            fileStore.saveFile(value = testData, name = "testdata.json", clazz = TestData::class)

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
            fileStore.saveFile(value = testData, name = "testdata.json", clazz = TestData::class)

            // When
            val testData2 = TestData(id = 6, name = "Hello World 2")
            fileStore.saveFile(value = testData2, name = "testdata.json", clazz = TestData::class)

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
            val testData = TestData(id = 5, name = "Hello World")
            fileStore.saveFile(value = testData, name = "testdata.json", clazz = TestData::class)

            // When: calling delete testdata.json
            fileStore.deleteFile("testdata.json")

            // Then: File is deleted
            val file = fileStore.getFile(clazz = TestData::class, name = "testdata.json")
            assertNull(file)
        }
}
