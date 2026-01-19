package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.FhirRepositoryRule
import nl.rijksoverheid.mgo.data.fhir.FhirResponseJson
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_ALCOHOL_USE
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_DRUG_USE
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_LIVING_SITUATION
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_NUTRITION_ADVICE
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_TOBACCO_USE
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_LIFESTYLE
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
class HealthCategoriesListItemViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @get:Rule
  val fhirRepositoryRule = FhirRepositoryRule(MemoryMgoByteArrayStorage())

  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())
  private val getRequests = GetRequests(getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk = JvmGetDataSetsFromDisk()))
  private lateinit var observeFhirResponses: ObserveFhirResponses

  @Before
  fun setup() {
    observeFhirResponses = ObserveFhirResponses(getRequests = getRequests, fhirRepository = fhirRepositoryRule.getRepository())
  }

  @Test
  fun testLoading() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: Only first lifestyle response is finished
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.DRUG_USE, request = TEST_FHIR_REQUEST_DRUG_USE)

      // When: Creating viewmodel
      val viewModel = createViewModel(filterOrganization = null, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Then: View state is updated
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.LOADING)
      }
    }

  @Test
  fun testLoaded() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: All lifestyle responses are success
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.DRUG_USE, request = TEST_FHIR_REQUEST_DRUG_USE)
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.ALCOHOL_USE, request = TEST_FHIR_REQUEST_ALCOHOL_USE)
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.LIVING_SITUATION,
        request = TEST_FHIR_REQUEST_LIVING_SITUATION,
      )
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.NUTRITION_ADVICE,
        request = TEST_FHIR_REQUEST_NUTRITION_ADVICE,
      )
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.TOBACCO_USE, request = TEST_FHIR_REQUEST_TOBACCO_USE)

      // When: Creating viewmodel
      val viewModel = createViewModel(filterOrganization = TEST_MGO_ORGANIZATION, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Then: View state is updated
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.LOADED)
      }
    }

  @Test
  fun testNoData() =
    runTest {
      // Given: Stored organization
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: All lifestyle responses are success but with empty json
      fhirRepositoryRule.enqueueEmptyJson(TEST_FHIR_REQUEST_DRUG_USE)
      fhirRepositoryRule.enqueueEmptyJson(TEST_FHIR_REQUEST_ALCOHOL_USE)
      fhirRepositoryRule.enqueueEmptyJson(TEST_FHIR_REQUEST_LIVING_SITUATION)
      fhirRepositoryRule.enqueueEmptyJson(TEST_FHIR_REQUEST_NUTRITION_ADVICE)
      fhirRepositoryRule.enqueueEmptyJson(TEST_FHIR_REQUEST_TOBACCO_USE)

      // When: Creating viewmodel
      val viewModel = createViewModel(filterOrganization = null, category = TEST_HEALTH_CATEGORY_LIFESTYLE)

      // Then: View state is updated
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.NO_DATA)
      }
    }

  private fun createViewModel(
    filterOrganization: MgoOrganization?,
    category: HealthCategoryGroup.HealthCategory,
  ) = HealthCategoriesListItemViewModel(
    filterOrganization = filterOrganization,
    category = category,
    organizationRepository = organizationRepository,
    fhirRepository = fhirRepositoryRule.getRepository(),
    ioDispatcher = mainDispatcherRule.testDispatcher,
    getRequests = getRequests,
    observeFhirResponses = observeFhirResponses,
  )
}
