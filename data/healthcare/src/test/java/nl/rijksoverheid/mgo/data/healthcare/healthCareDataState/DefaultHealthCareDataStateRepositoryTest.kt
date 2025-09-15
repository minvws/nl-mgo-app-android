package nl.rijksoverheid.mgo.data.healthcare.healthCareDataState

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.TestMgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import org.junit.Assert.assertTrue
import org.junit.Test

class DefaultHealthCareDataStateRepositoryTest {
  @Test
  fun testEmptyState() =
    runTest {
      // Given: There are no mgo resources returned
      val mgoResourceRepository = TestMgoResourceRepository()
      mgoResourceRepository.setMgoResources(Result.success(listOf()))
      val repository = DefaultHealthCareDataStateRepository(mgoResourceRepository)

      // When: Calling get
      repository.get(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategoryId.MEDICATIONS).test {
        // Then: First emit loading state, and then empty state
        assertTrue(awaitItem() is HealthCareDataState.Loading)
        assertTrue(awaitItem() is HealthCareDataState.Empty)
        awaitComplete()
      }
    }

  @Test
  fun testLoadedState() =
    runTest {
      // Given: There are mgo resources returned
      val mgoResourceRepository = TestMgoResourceRepository()
      mgoResourceRepository.setMgoResources(Result.success(listOf(TEST_MGO_RESOURCE)))
      val repository = DefaultHealthCareDataStateRepository(mgoResourceRepository)

      // When: Calling get
      repository.get(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategoryId.MEDICATIONS).test {
        // Then: First emit loading state, and then loaded state
        assertTrue(awaitItem() is HealthCareDataState.Loading)
        assertTrue(awaitItem() is HealthCareDataState.Loaded)
        awaitComplete()
      }
    }
}
