package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.test.runTest

@HiltAndroidTest
internal class EncryptedFileStoreTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("storageMoshi")
    lateinit var moshi: Moshi

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var fileStore: EncryptedFileStore

    @Before
    fun init() {
        hiltRule.inject()
        fileStore =
            EncryptedFileStore(
                context = context,
                moshi = moshi,
                masterKeyAlias = "123",
            )
    }

    @JsonClass(generateAdapter = true)
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
                val cacheFiles = context.cacheDir.listFiles()
                if (cacheFiles != null) {
                    for (file in cacheFiles) {
                        file.delete()
                    }
                }
            } catch (e: Exception) {
                // Never crash
            }
        }

    @Test
    fun given_saved_file_When_getting_file_Then_return_file_content() =
        runTest {
            // Given
            val testData = TestData(id = 5, name = "Hello World")
            fileStore.saveFile(myObject = testData, name = "testdata.json")

            // When
            val fileContent = fileStore.getFile(clazz = TestData::class.java, name = "testdata.json")

            // Then
            assertEquals(testData, fileContent)
        }

    @Test
    fun given_no_saved_file_When_getting_file_Then_return_null() =
        runTest {
            // Given no saved file

            // When
            val fileContent = fileStore.getFile(clazz = TestData::class.java, name = "testdata.json")

            // Then
            assertNull(fileContent)
        }
}
