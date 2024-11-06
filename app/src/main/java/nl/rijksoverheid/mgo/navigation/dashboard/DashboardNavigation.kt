package nl.rijksoverheid.mgo.navigation.dashboard

import android.os.Parcelable
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed class DashboardNavigation {
    @Serializable
    data object Root

    @Serializable
    data object BottomBar

    @Serializable
    data class HealthCategories(val organization: MgoOrganization? = null)

    @Serializable
    data class HealthCategory(val arguments: HealthCategoryScreenArguments)

    @Serializable
    data class UiSchemaDetail(val toolbarTitle: String, val uiSchema: UISchema)

    @Serializable
    data class RemoveOrganization(val providerId: String, val providerName: String)
}
