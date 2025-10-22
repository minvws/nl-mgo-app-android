package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesMgoKeyValueStorageTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val sharedPreferences = context.getSharedPreferences("test_shared_preferences", Context.MODE_PRIVATE)
  private val keyValueStorage = SharedPreferencesMgoKeyValueStorage(sharedPreferences)

  @Before
  fun setup() {
    keyValueStorage.deleteAll()
  }

  @Test
  fun testSave() {
    // Boolean
    keyValueStorage.save(key = "key", value = true)
    assertEquals(true, keyValueStorage.get("key"))

    // String
    keyValueStorage.save(key = "key", value = "Hello World")
    assertEquals("Hello World", keyValueStorage.get("key"))

    // Int
    keyValueStorage.save(key = "key", value = 1)
    assertEquals(1, keyValueStorage.get("key"))

    // Long
    keyValueStorage.save(key = "key", value = 1L)
    assertEquals(1L, keyValueStorage.get("key"))

    // Float
    keyValueStorage.save(key = "key", value = 1f)
    assertEquals(1f, keyValueStorage.get("key"))
  }

  @Test(expected = IllegalArgumentException::class)
  fun testSaveError() {
    keyValueStorage.save(key = "key", value = listOf<String>())
  }

  @Test
  fun testObserve() =
    runTest {
      keyValueStorage.save(key = "key", value = true)
      keyValueStorage.observe<Boolean>("key").test {
        keyValueStorage.save(key = "key", value = false)
        assertEquals(awaitItem(), true)
        assertEquals(awaitItem(), false)
      }
    }

  @Test
  fun testObserveEmpty() =
    runTest {
      keyValueStorage.observe<Boolean>("key2").test {
        assertNull(awaitItem())
      }
    }

  @Test
  fun testDelete() {
    keyValueStorage.save(key = "key", value = true)
    keyValueStorage.delete("key")
    assertNull(keyValueStorage.get("key"))
  }

  @Test
  fun testDeleteAll() {
    keyValueStorage.save(key = "key", value = true)
    keyValueStorage.save(key = "key2", value = true)
    keyValueStorage.save(key = "key3", value = true)
    keyValueStorage.deleteAll()
    assertNull(keyValueStorage.get("key"))
    assertNull(keyValueStorage.get("key2"))
    assertNull(keyValueStorage.get("key3"))
  }
}
