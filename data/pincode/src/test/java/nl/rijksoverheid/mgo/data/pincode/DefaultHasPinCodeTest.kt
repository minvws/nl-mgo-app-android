package nl.rijksoverheid.mgo.data.pincode

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DefaultHasPinCodeTest {
  @Test
  fun `Given stored pin code, When calling use case, Then return true`() {
    // Given
    val keyValueStore = TestKeyValueStore()
    val hasPinCode = DefaultHasPinCode(keyValueStore = keyValueStore)

    // When
    runBlocking { keyValueStore.setString(KEY_PIN_CODE, "pin") }

    // Then
    assertTrue(hasPinCode.invoke())
  }

  @Test
  fun `Given no stored pin code, When calling use case, Then return true`() {
    // Given
    val keyValueStore = TestKeyValueStore()
    val hasPinCode = DefaultHasPinCode(keyValueStore = keyValueStore)

    // When (no pin code stored)

    // Then
    assertFalse(hasPinCode.invoke())
  }
}
