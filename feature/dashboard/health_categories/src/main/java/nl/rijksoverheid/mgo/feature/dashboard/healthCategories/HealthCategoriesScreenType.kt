package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.squareup.moshi.JsonClass
import dev.zacsweers.moshix.sealed.annotations.TypeLabel
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@JsonClass(generateAdapter = true, generator = "sealed:type")
sealed class HealthCategoriesScreenType {
    @TypeLabel("all")
    data class All(val id: String = "all") : HealthCategoriesScreenType()

    @TypeLabel("single")
    data class Single(val organization: MgoOrganization) : HealthCategoriesScreenType()
}

@Composable
fun HealthCategoriesScreenType.getTitle(): String {
    return when (this) {
        is HealthCategoriesScreenType.All -> stringResource(CopyR.string.overview_heading)
        is HealthCategoriesScreenType.Single -> organization.name
    }
}
