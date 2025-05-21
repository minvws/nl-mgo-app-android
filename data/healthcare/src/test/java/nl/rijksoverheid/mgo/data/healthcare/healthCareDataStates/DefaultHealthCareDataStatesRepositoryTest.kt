package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TestHealthCareDataStateRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TestHealthCareDataStatesStore
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import org.junit.Test

class DefaultHealthCareDataStatesRepositoryTest {
  private val healthCareDataStateRepository = TestHealthCareDataStateRepository()
  private val healthCareDataStatesStore = TestHealthCareDataStatesStore()
  private val repository =
    DefaultHealthCareDataStatesRepository(
      healthCareDataStateRepository = healthCareDataStateRepository,
      healthCareDataStatesStore = healthCareDataStatesStore,
    )

  @Test
  fun testCategoryAndOrganization() =
    runTest {
      val organization = TEST_MGO_ORGANIZATION
      val category = HealthCareCategory.MEDICATIONS

      // Given: Set loaded state
      healthCareDataStateRepository.setLoadedState(organization = organization, category = category)

      // When: Refresh
      repository.refresh(organization = organization, category = category)

      // Then: Collect state
      repository.observe(category = HealthCareCategory.MEDICATIONS, filterOrganization = TEST_MGO_ORGANIZATION).test {
        val emit = awaitItem()
        assertEquals(1, emit.size)
        assertEquals(category, emit.first().category)
      }
    }

  @Test
  fun testOrganizations() =
    runTest {
      val organization1 = TEST_MGO_ORGANIZATION.copy(id = "1")
      val organization2 = TEST_MGO_ORGANIZATION.copy(id = "2")
      val category = HealthCareCategory.MEDICATIONS

      // Given: Set loaded states
      healthCareDataStateRepository.setLoadedState(organization = organization1, category = category)
      healthCareDataStateRepository.setLoadedState(organization = organization2, category = category)

      // When: Refresh
      repository.refresh(organization = organization1, category = category)
      repository.refresh(organization = organization2, category = category)

      // Then: Collect state
      repository.observe(category = HealthCareCategory.MEDICATIONS, filterOrganization = null).test {
        val emit = awaitItem()
        assertEquals(2, emit.size)
        assertEquals("1", emit[0].organization.id)
        assertEquals("2", emit[1].organization.id)
      }
    }

  @Test
  fun testDelete() =
    runTest {
      val organization = TEST_MGO_ORGANIZATION.copy(id = "1")
      val category = HealthCareCategory.MEDICATIONS

      // Given: Organization is stored
      healthCareDataStateRepository.setLoadedState(organization = organization, category = category)
      repository.refresh(organization = organization, category = category)

      // When: Deleting the first organization
      repository.delete(organization)

      // Then: Nothing is observed
      repository.observe(category = category, filterOrganization = organization).test {
        assertEquals(0, awaitItem().size)
      }
    }
}
