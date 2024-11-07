package nl.rijksoverheid.mgo.navigation.overview

import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import kotlinx.serialization.Serializable

sealed class OverviewNavigation {
    @Serializable
    object Root

    @Serializable
    object HealthCategories

    @Serializable
    data class HealthCategory(val arguments: HealthCategoryScreenArguments)

    @Serializable
    data class UiSchemaDetail(val toolbarTitle: String, val uiSchema: UISchema)

    @Serializable
    data class RemoveOrganization(val providerId: String, val providerName: String)
}
