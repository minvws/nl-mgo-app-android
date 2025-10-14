package nl.rijksoverheid.mgo.framework.storage.bytearray

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MemoryMgoByteArrayStorageTest {
  private val byteArrayStorage = MemoryMgoByteArrayStorage()

  @Test
  fun testSave() =
    runTest {
      byteArrayStorage.save(name = "key", content = "Hello World".toByteArray())
      assertEquals("Hello World", byteArrayStorage.get("key")?.toString(Charsets.UTF_8))
    }

  @Test
  fun testDelete() =
    runTest {
      byteArrayStorage.save(name = "key", content = "Hello World".toByteArray())
      byteArrayStorage.delete("key")
      assertNull(byteArrayStorage.get("key"))
    }
}
