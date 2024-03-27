package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.TestKeyValueStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class HasSeenOnboardingTest {
    @Test
    fun `Given the key value store, When it does not have the onboarding flag, Then return false`() {
        // Given
        val keyValueStore = TestKeyValueStore()
        val hasSeenOnboarding = HasSeenOnboarding(keyValueStore = keyValueStore)

        // When
        keyValueStore.setBoolean(KEY_HAS_SEEN_ONBOARDING, false)

        // Then
        assertFalse(hasSeenOnboarding.invoke())
    }

    @Test
    fun `Given the key value store, When it does have the onboarding flag, Then return true`() {
        // Given
        val keyValueStore = TestKeyValueStore()
        val hasSeenOnboarding = HasSeenOnboarding(keyValueStore = keyValueStore)

        // When
        keyValueStore.setBoolean(KEY_HAS_SEEN_ONBOARDING, true)

        // Then
        assertTrue(hasSeenOnboarding.invoke())
    }
}
