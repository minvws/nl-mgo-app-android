package nl.rijksoverheid.mgo.data.healthcare.healthCareDataState

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION

/**
 * State of retrieved health care data.
 *
 * @param organization The organization of the health care data.
 * @param category The category of the health care data.
 */
sealed class HealthCareDataState(
  open val organization: MgoOrganization,
  open val category: HealthCareCategoryId,
) {
  /**
   * Represents that the health care data is loading.
   *
   * @param organization The organization of the health care data.
   * @param category The category of the health care data.
   */
  data class Loading(
    override val organization: MgoOrganization,
    override val category: HealthCareCategoryId,
  ) : HealthCareDataState(organization, category)

  /**
   * Represents that the health care data is successfully retrieved.
   *
   * @param results The fetched results in our own json format. This can be used in [UiSchemaMapper] to transform into
   * presentable data.
   * @param organization The organization of the health care data.
   * @param category The category of the health care data.
   */
  data class Loaded(
    val results: List<Result<List<MgoResource>>>,
    override val organization: MgoOrganization,
    override val category: HealthCareCategoryId,
  ) : HealthCareDataState(organization, category)

  /**
   * Represents fetched health care data that does not contain anything to present.
   *
   * @param organization The organization of the health care data.
   * @param category The category of the health care data.
   */
  data class Empty(
    override val organization: MgoOrganization,
    override val category: HealthCareCategoryId,
  ) : HealthCareDataState(organization, category)
}

val TEST_HEALTH_CARE_DATA_STATE_LOADING =
  HealthCareDataState.Loading(
    organization = TEST_MGO_ORGANIZATION,
    category = HealthCareCategoryId.MEDICATIONS,
  )

val TEST_HEALTH_CARE_DATA_STATE_EMPTY =
  HealthCareDataState.Empty(
    organization = TEST_MGO_ORGANIZATION,
    category = HealthCareCategoryId.MEDICATIONS,
  )

val TEST_HEALTH_CARE_DATA_STATE_LOADED =
  HealthCareDataState.Loaded(
    results = listOf(Result.success(listOf(TEST_MGO_RESOURCE))),
    organization = TEST_MGO_ORGANIZATION,
    category = HealthCareCategoryId.MEDICATIONS,
  )

val TEST_HEALTH_CARE_DATA_ERROR =
  HealthCareDataState.Loaded(
    results = listOf(Result.failure(IllegalStateException())),
    organization = TEST_MGO_ORGANIZATION,
    category = HealthCareCategoryId.MEDICATIONS,
  )
