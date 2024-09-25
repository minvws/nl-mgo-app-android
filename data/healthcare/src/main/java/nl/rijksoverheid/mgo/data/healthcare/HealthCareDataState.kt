package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.UISchema

data class HealthCareDataState(
    val loading: Boolean,
    val organization: MgoOrganization,
    val category: HealthCareCategory,
    val uiSchemaListResults: List<Result<List<UISchema>>>,
)

val TEST_HEALTH_CARE_DATA_STATE_LOADED =
    HealthCareDataState(
        loading = false,
        organization = TEST_MGO_ORGANIZATION,
        category = HealthCareCategory.MEDICATIONS,
        uiSchemaListResults = listOf(Result.success(listOf(TEST_UI_SCHEMA_MEDICATION))),
    )
