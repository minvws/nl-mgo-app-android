package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.os.Parcelable
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class HealthCategoryScreenArguments(
    val category: HealthCareCategory,
    val filterOrganization: MgoOrganization? = null,
) : Parcelable
