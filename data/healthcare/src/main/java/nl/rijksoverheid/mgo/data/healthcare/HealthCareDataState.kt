package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema

data class HealthCareDataState(
    val loading: Boolean,
    val organization: MgoOrganization,
    val category: HealthCareCategory,
    val uiSchemaListResults: List<Result<List<UISchema>>>,
)
