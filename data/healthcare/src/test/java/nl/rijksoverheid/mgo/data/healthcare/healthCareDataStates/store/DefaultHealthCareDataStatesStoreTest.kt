package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_STATE_LOADED
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultHealthCareDataStatesStoreTest {
  private val store = DefaultHealthCareDataStatesStore()

  @Test
  fun testGet() {
    // When: Calling get
    val states = store.get()

    // Then: Return no states
    assertEquals(0, states.size)
  }

  @Test
  fun testObserveCategory() =
    runTest {
      // Given: State is stored from multiple providers and medication category
      store.add(
        organization = TEST_MGO_ORGANIZATION.copy(id = "1"),
        category = HealthCareCategoryId.MEDICATIONS,
        state = TEST_HEALTH_CARE_DATA_STATE_LOADED,
      )
      store.add(
        organization = TEST_MGO_ORGANIZATION.copy(id = "2"),
        category = HealthCareCategoryId.MEDICATIONS,
        state = TEST_HEALTH_CARE_DATA_STATE_LOADED,
      )
      store.add(
        organization = TEST_MGO_ORGANIZATION.copy(id = "3"),
        category = HealthCareCategoryId.MEDICATIONS,
        state =
        TEST_HEALTH_CARE_DATA_STATE_LOADED,
      )

      // When: Observing the medication category
      store.observe(category = HealthCareCategoryId.MEDICATIONS, filterOrganization = null).test {
        // Then: Three states are returned
        assertEquals(3, awaitItem().size)
      }
    }

  @Test
  fun testObserveCategoryAndOrganization() =
    runTest {
      // Given: State is stored from multiple providers and medication category
      store.add(
        organization = TEST_MGO_ORGANIZATION.copy(id = "1"),
        category = HealthCareCategoryId.MEDICATIONS,
        state = TEST_HEALTH_CARE_DATA_STATE_LOADED,
      )
      store.add(
        organization = TEST_MGO_ORGANIZATION.copy(id = "2"),
        category = HealthCareCategoryId.MEDICATIONS,
        state = TEST_HEALTH_CARE_DATA_STATE_LOADED,
      )
      store.add(
        organization = TEST_MGO_ORGANIZATION.copy(id = "3"),
        category = HealthCareCategoryId.MEDICATIONS,
        state =
        TEST_HEALTH_CARE_DATA_STATE_LOADED,
      )

      // When: Observing the medication category and organization with id 1
      store.observe(category = HealthCareCategoryId.MEDICATIONS, filterOrganization = TEST_MGO_ORGANIZATION.copy(id = "1")).test {
        // Then: One state is returned
        assertEquals(1, awaitItem().size)
      }
    }

  @Test
  fun testDelete() =
    runTest {
      // Given: State is stored
      store.add(
        organization = TEST_MGO_ORGANIZATION.copy(id = "1"),
        category = HealthCareCategoryId.MEDICATIONS,
        state = TEST_HEALTH_CARE_DATA_STATE_LOADED,
      )

      // When: Calling delete for organization with id 1
      store.delete(organization = TEST_MGO_ORGANIZATION.copy(id = "1"))

      // Then: State is removed
      val states = store.get()
      assertEquals(0, states.size)
    }
}
