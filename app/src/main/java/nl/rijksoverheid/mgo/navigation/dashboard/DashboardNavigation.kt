package nl.rijksoverheid.mgo.navigation.dashboard

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import kotlinx.serialization.Serializable

sealed class DashboardNavigation {
    @Serializable
    data object Root

    @Serializable
    data object BottomBar

    @Serializable
    sealed class Overview {
        @Serializable
        data object Root : Overview()

        @Serializable
        data class HealthCareCategory(val arguments: HealthCategoryScreenArguments) : Overview()

        @Serializable
        data class UISchemaDetail(val toolbarTitle: String, val uiSchema: UISchema) : Overview()
    }

    @Serializable
    sealed class Organizations {
        @Serializable
        data object Root : Organizations()

        @Serializable
        data class HealthCareCategories(val organization: MgoOrganization) : Organizations()

        @Serializable
        data class HealthCareCategory(val arguments: HealthCategoryScreenArguments) : Organizations()

        @Serializable
        data class UISchemaDetail(val toolbarTitle: String, val uiSchema: UISchema) : Organizations()
    }

    @Serializable
    sealed class AboutThisApp {
        @Serializable
        data object Root : AboutThisApp()
    }
}
