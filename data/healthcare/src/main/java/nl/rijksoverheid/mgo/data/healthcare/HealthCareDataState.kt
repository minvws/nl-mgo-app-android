package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.UISchema

sealed class HealthCareDataState(open val organization: MgoOrganization, open val category: HealthCareCategory) {
    data class Loading(override val organization: MgoOrganization, override val category: HealthCareCategory) :
        HealthCareDataState(organization, category)

    data class Loaded(
        val results: List<Result<List<UISchema>>>,
        override val organization: MgoOrganization,
        override val category: HealthCareCategory,
    ) : HealthCareDataState(organization, category)

    data class Empty(override val organization: MgoOrganization, override val category: HealthCareCategory) :
        HealthCareDataState(organization, category)
}

val TEST_HEALTH_CARE_DATA_STATE_LOADING =
    HealthCareDataState.Loading(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS)

val TEST_HEALTH_CARE_DATA_STATE_LOADED =
    HealthCareDataState.Loaded(
        results = listOf(Result.success(listOf(TEST_UI_SCHEMA_MEDICATION))),
        organization = TEST_MGO_ORGANIZATION,
        category = HealthCareCategory.MEDICATIONS,
    )

val TEST_HEALTH_CARE_DATA_ERROR =
    HealthCareDataState.Loaded(
        results = listOf(Result.failure(IllegalStateException())),
        organization = TEST_MGO_ORGANIZATION,
        category = HealthCareCategory.MEDICATIONS,
    )
