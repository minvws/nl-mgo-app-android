package nl.rijksoverheid.mgo.navigation.dashboard

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareCategory as HealthCareCategoryModel

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
        data object HealthCareCategories : Overview()

        @Serializable
        data class HealthCareCategory(val category: HealthCareCategoryModel) : Overview()

        @Serializable
        data class UISchemaDetail(
            val organization: MgoOrganization,
            val mgoResource: MgoResourceJson,
            val isSummary: Boolean,
        ) : Overview()
    }

    @Serializable
    sealed class Organizations {
        @Serializable
        data object Root : Organizations()

        @Serializable
        data object List : Organizations()

        @Serializable
        data class HealthCareCategories(val organization: MgoOrganization) : Organizations()

        @Serializable
        data class HealthCareCategory(val category: HealthCareCategoryModel, val filterOrganization: MgoOrganization) : Organizations()

        @Serializable
        data class UISchemaDetail(
            val organization: MgoOrganization,
            val mgoResource: MgoResourceJson,
            val isSummary: Boolean,
        ) : Organizations()

        @Serializable
        data class RemoveOrganization(val organizationId: String, val organizationName: String) : Organizations()
    }

    @Serializable
    sealed class Settings {
        @Serializable
        data object Root : Settings()

        @Serializable
        data object Debug : Settings()
    }
}
