package nl.rijksoverheid.mgo.data.pincode.biometric

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertTrue
import org.junit.Test

internal class TestDefaultSetLoginWithBiometricEnabled {
  @Test
  fun `Given the key value store, When setting the biometric flag to false, Then store has that value`() {
    // Given
    val keyValueStore = TestKeyValueStore()
    val setLoginWithBiometricEnabled = DefaultSetLoginWithBiometricEnabled(keyValueStore = keyValueStore)

    // When
    setLoginWithBiometricEnabled.invoke()

    // Then
    assertTrue(runBlocking { keyValueStore.getBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED) })
  }
}
