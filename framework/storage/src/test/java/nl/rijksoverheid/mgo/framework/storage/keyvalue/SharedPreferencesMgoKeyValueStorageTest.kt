package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesMgoKeyValueStorageTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val sharedPreferences = context.getSharedPreferences("test_shared_preferences", Context.MODE_PRIVATE)
  private val keyValueStorage = SharedPreferencesMgoKeyValueStorage(sharedPreferences)

  @Test
  fun testSave() {
    keyValueStorage.save(key = "key", value = true)
    assertEquals(true, keyValueStorage.get("key"))
  }

  @Test
  fun testObserve() =
    runTest {
      keyValueStorage.save(key = "key", value = true)
      keyValueStorage.observe<Boolean>("key").test {
        assertEquals(awaitItem(), true)
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
