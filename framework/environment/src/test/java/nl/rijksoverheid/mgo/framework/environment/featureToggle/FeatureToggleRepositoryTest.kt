package nl.rijksoverheid.mgo.framework.environment.featureToggle

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FeatureToggleRepositoryTest {
  private val keyValueStorage = MemoryMgoKeyValueStorage()
  private val repository = FeatureToggleRepository(keyValueStorage)

  @Test
  fun testGetAll() {
    // When: Calling get
    val toggles = repository.get()

    // Then: Return toggles
    val expected =
      listOf(
        FeatureToggleEntry(
          toggle = FEATURE_TOGGLE_FLAG_SECURE,
          value = true,
        ),
        FeatureToggleEntry(
          toggle = FEATURE_TOGGLE_SKIP_DIGID_LOGIN,
          value = false,
        ),
      )
    assertEquals(expected, toggles)
  }

  @Test
  fun testGetSingle() {
    // When: Calling get
    val toggle = repository.get(FEATURE_TOGGLE_SKIP_DIGID_LOGIN)

    // Then: Return toggle
    val expected = FeatureToggleEntry(toggle = FEATURE_TOGGLE_SKIP_DIGID_LOGIN, value = false)
    assertEquals(expected, toggle)
  }

  @Test
  fun testObserveAll() =
    runTest {
      // When: Calling observe
      repository.observe().test {
        val toggles = awaitItem()

        // Then: Return toggles
        val expected =
          listOf(
            FeatureToggleEntry(
              toggle = FEATURE_TOGGLE_FLAG_SECURE,
              value = true,
            ),
            FeatureToggleEntry(
              toggle = FEATURE_TOGGLE_SKIP_DIGID_LOGIN,
              value = false,
            ),
          )
        assertEquals(expected, toggles)
      }
    }

  @Test
  fun testObserveSingle() =
    runTest {
      // When: Calling observe
      repository.observe(FEATURE_TOGGLE_SKIP_DIGID_LOGIN).test {
        val toggles = awaitItem()

        // Then: Return toggle
        val expected =
          FeatureToggleEntry(
            toggle = FEATURE_TOGGLE_SKIP_DIGID_LOGIN,
            value = false,
          )
        assertEquals(expected, toggles)
      }
    }

  @Test
  fun testSet() {
    // When: Calling set
    repository.set(FEATURE_TOGGLE_SKIP_DIGID_LOGIN, true)

    // Then: Toggle is true
    val toggle = repository.get(FEATURE_TOGGLE_SKIP_DIGID_LOGIN)
    assertTrue(toggle.value)
  }
}
