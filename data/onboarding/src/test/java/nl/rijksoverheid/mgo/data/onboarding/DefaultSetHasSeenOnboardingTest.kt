package nl.rijksoverheid.mgo.data.onboarding

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DefaultSetHasSeenOnboardingTest {
  @Test
  fun `Given the key value store, When setting the onboarding flag to false, Then store has that value`() {
    // Given
    val keyValueStore = TestKeyValueStore()
    val setHasSeenOnboarding = DefaultSetHasSeenOnboarding(keyValueStore = keyValueStore)

    // When
    setHasSeenOnboarding.invoke(false)

    // Then
    assertFalse(runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_ONBOARDING) })
  }

  @Test
  fun `Given the key value store, When setting the onboarding flag to true, Then store has that value`() {
    // Given
    val keyValueStore = TestKeyValueStore()
    val setHasSeenOnboarding = DefaultSetHasSeenOnboarding(keyValueStore = keyValueStore)

    // When
    setHasSeenOnboarding.invoke(true)

    // Then
    assertTrue(runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_ONBOARDING) })
  }
}
