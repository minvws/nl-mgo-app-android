package nl.rijksoverheid.mgo.data.pincode

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.pincode.hash.TestPinCodeHasher
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertEquals
import org.junit.Test

internal class DefaultStorePinCodeTest {
  @Test
  fun `Given no stored pin code, When storing pin code, Then store hashed pin code`() =
    runTest {
      // Given
      val keyValueStore = TestKeyValueStore()
      val hasher = TestPinCodeHasher()
      val storePinCode = DefaultStorePinCode(secureKeyValueStore = keyValueStore, pinCodeHasher = hasher)

      // When
      storePinCode.invoke(listOf(1, 2, 3, 4, 5))

      // Then
      val storedHash = keyValueStore.getString(KEY_PIN_CODE)
      assertEquals("5,4,3,2,1", storedHash)
    }
}
