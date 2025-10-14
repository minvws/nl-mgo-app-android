package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import org.junit.Assert.assertEquals
import org.junit.Test

internal class HasSeenOnboardingTest {
  private val keyValueStorage = MemoryMgoKeyValueStorage()
  private val usecase = HasSeenOnboarding(keyValueStorage)

  @Test
  fun testInvoke() {
    // Given: Onboarding has seen
    keyValueStorage.save(key = KEY_HAS_SEEN_ONBOARDING, value = true)

    // When: Calling usecase
    val hasSeen = usecase.invoke()

    // Then: Return true
    assertEquals(true, hasSeen)
  }
}
