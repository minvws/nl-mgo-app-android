package nl.rijksoverheid.mgo.framework.storage.keyvalue

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
  fun testDelete() =
    runTest {
      keyValueStorage.save(key = "key", value = true)
      keyValueStorage.delete("key")
      assertNull(keyValueStorage.get("key"))
    }

  @Test
  fun testDeleteAll() =
    runTest {
      keyValueStorage.save(key = "key", value = true)
      keyValueStorage.save(key = "key2", value = true)
      keyValueStorage.save(key = "key3", value = true)
      keyValueStorage.deleteAll()
      assertNull(keyValueStorage.get("key"))
      assertNull(keyValueStorage.get("key2"))
      assertNull(keyValueStorage.get("key3"))
    }
}
