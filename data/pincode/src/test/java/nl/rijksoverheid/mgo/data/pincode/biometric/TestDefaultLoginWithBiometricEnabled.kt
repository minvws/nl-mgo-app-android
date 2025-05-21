package nl.rijksoverheid.mgo.data.pincode.biometric

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class TestDefaultLoginWithBiometricEnabled {
  @Test
  fun `Given login with biometric enabled, When calling use case, Then return true`() {
    // Given
    val keyValueStore = TestKeyValueStore()
    val loginWithBiometricEnabled = DefaultLoginWithBiometricEnabled(keyValueStore = keyValueStore)

    // When
    runBlocking { keyValueStore.setBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED, true) }

    // Then
    assertTrue(loginWithBiometricEnabled.invoke())
  }

  @Test
  fun `Given login with biometric disabled, When calling use case, Then return true`() {
    // Given
    val keyValueStore = TestKeyValueStore()
    val loginWithBiometricEnabled = DefaultLoginWithBiometricEnabled(keyValueStore = keyValueStore)

    // When
    runBlocking { keyValueStore.setBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED, false) }

    // Then
    assertFalse(loginWithBiometricEnabled.invoke())
  }
}
