package nl.rijksoverheid.mgo.data.onboarding

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DefaultHasSeenOnboardingTest {
  @Test
  fun `Given the key value store, When setting the has seen onboarding flag to false, Then return false`() {
    // Given
    val keyValueStore = TestKeyValueStore()
    val hasSeenOnboarding = DefaultHasSeenOnboarding(keyValueStore = keyValueStore)

    // When
    runBlocking { keyValueStore.setBoolean(KEY_HAS_SEEN_ONBOARDING, false) }

    // Then
    assertFalse(hasSeenOnboarding.invoke())
  }

  @Test
  fun `Given the key value store, When setting the has seen onboarding flag to true, Then return true`() {
    // Given
    val keyValueStore = TestKeyValueStore()
    val hasSeenOnboarding = DefaultHasSeenOnboarding(keyValueStore = keyValueStore)

    // When
    runBlocking { keyValueStore.setBoolean(KEY_HAS_SEEN_ONBOARDING, true) }

    // Then
    assertTrue(hasSeenOnboarding.invoke())
  }
}
