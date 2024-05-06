package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.squareup.moshi.JsonClass
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlinx.coroutines.test.runTest

@HiltAndroidTest
internal class DefaultFileStoreTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Inject
    lateinit var fileStore: DefaultFileStore

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
    fun validateStorage() =
        runTest {
            // Given
            val testData = TestData(id = 5, name = "Hello World")

            // When
            fileStore.saveFile(file = testData, name = "testdata.json")

            // Then
            val file = fileStore.getFile(clazz = TestData::class.java, name = "testdata.json")
            assertEquals(testData, file)
        }
}
