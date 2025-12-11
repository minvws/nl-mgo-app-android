package nl.rijksoverheid.mgo.framework.storage.keyvalue

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MemoryMgoKeyValueStorageTest {
  private val keyValueStorage = MemoryMgoKeyValueStorage()

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
