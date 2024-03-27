package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.TestKeyValueStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.runBlocking

internal class SetHasSeenOnboardingTest {
    @Test
    fun `Given the key value store, When setting the onboarding flag to false, Then store has that value`() {
        // Given
        val keyValueStore = TestKeyValueStore()
        val setHasSeenOnboarding = SetHasSeenOnboarding(keyValueStore = keyValueStore)

        // When
        setHasSeenOnboarding.invoke(false)

        // Then
        assertFalse(runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_ONBOARDING) })
    }

    @Test
    fun `Given the key value store, When setting the onboarding flag to true, Then store has that value`() {
        // Given
        val keyValueStore = TestKeyValueStore()
        val setHasSeenOnboarding = SetHasSeenOnboarding(keyValueStore = keyValueStore)

        // When
        setHasSeenOnboarding.invoke(true)

        // Then
        assertTrue(runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_ONBOARDING) })
    }
}
