package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import org.junit.Assert.assertEquals
import org.junit.Test

internal class SetHasSeenOnboardingTest {
  private val keyValueStorage = MemoryMgoKeyValueStorage()
  private val usecase = SetHasSeenOnboarding(keyValueStorage)

  @Test
  fun testInvoke() {
    // When: Calling usecase
    usecase.invoke(true)

    // Then: Value is true
    assertEquals(true, keyValueStorage.get(KEY_HAS_SEEN_ONBOARDING))
  }
}
