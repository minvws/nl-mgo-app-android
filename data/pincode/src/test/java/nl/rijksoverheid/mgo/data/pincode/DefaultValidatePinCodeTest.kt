package nl.rijksoverheid.mgo.data.pincode

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.pincode.hash.TestPinCodeHasher
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DefaultValidatePinCodeTest {
  @Test
  fun `Given no stored pin, When calling use case, Return false`() =
    runTest {
      // Given
      val keyValueStore = TestKeyValueStore()
      val validatePinCode =
        DefaultValidatePinCode(
          keyValueStore = keyValueStore,
          pinCodeHasher = TestPinCodeHasher(),
        )

      // When (no pin code stored)

      // Then
      assertFalse(validatePinCode.invoke(listOf(1, 2, 3, 4, 5)))
    }

  @Test
  fun `Given pin hashes are not the same, When calling use case, Return false`() =
    runTest {
      // Given
      val keyValueStore = TestKeyValueStore()
      val pinCodeHasher = TestPinCodeHasher()
      val validatePinCode =
        DefaultValidatePinCode(
          keyValueStore = keyValueStore,
          pinCodeHasher = pinCodeHasher,
        )

      // When
      keyValueStore.setString(KEY_PIN_CODE, "1,2,3,4,6")

      // Then
      assertFalse(validatePinCode.invoke(listOf(1, 2, 3, 4, 5)))
    }

  @Test
  fun `Given pin hashes are the same, When calling use case, Return true`() =
    runTest {
      // Given
      val keyValueStore = TestKeyValueStore()
      val pinCodeHasher = TestPinCodeHasher()
      val validatePinCode =
        DefaultValidatePinCode(
          keyValueStore = keyValueStore,
          pinCodeHasher = pinCodeHasher,
        )

      // When
      keyValueStore.setString(KEY_PIN_CODE, "1,2,3,4,5")

      // Then
      assertTrue(validatePinCode.invoke(listOf(1, 2, 3, 4, 5)))
    }
}
