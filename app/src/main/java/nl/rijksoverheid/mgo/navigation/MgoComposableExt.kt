package nl.rijksoverheid.mgo.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import nl.rijksoverheid.mgo.component.mgo.navigation.mgoComposable
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.JsonNavType
import kotlin.reflect.typeOf

/**
 * Use to show a screen in a navigation.
 */
inline fun <reified T : Any> NavGraphBuilder.mgoComposableExt(
  deepLinks: List<NavDeepLink> = emptyList(),
  animate: Boolean = true,
  noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = mgoComposable<T>(
  deepLinks = deepLinks,
  animate = animate,
  typeMap =
    mapOf(
      typeOf<MgoOrganization?>() to
        JsonNavType(
          MgoOrganization::class.java,
          MgoOrganization
            .serializer(),
        ),
      typeOf<MgoOrganization>() to
        JsonNavType(
          MgoOrganization::class.java,
          MgoOrganization
            .serializer(),
        ),
      typeOf<HealthCategoryGroup.HealthCategory>() to
        JsonNavType(HealthCategoryGroup.HealthCategory::class.java, HealthCategoryGroup.HealthCategory.serializer()),
    ),
  content = content,
)
