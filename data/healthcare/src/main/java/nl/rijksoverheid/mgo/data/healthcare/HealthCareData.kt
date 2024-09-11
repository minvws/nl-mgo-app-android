package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.UISchema

sealed class HealthCareData {
    data object Loading : HealthCareData()

    data class Loaded(
        val organization: MgoOrganization,
        val uiSchemaList: List<UISchema>,
    ) : HealthCareData()

    data class Error(val exception: Throwable) : HealthCareData()
}

val TEST_HEALTH_CARE_DATA_LOADED_MEDICATION =
    HealthCareData.Loaded(
        organization = TEST_MGO_ORGANIZATION,
        uiSchemaList = listOf(TEST_UI_SCHEMA_MEDICATION),
    )
