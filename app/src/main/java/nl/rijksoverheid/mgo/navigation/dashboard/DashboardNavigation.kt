package nl.rijksoverheid.mgo.navigation.dashboard

import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup

/**
 * Represents all navigation destinations in the dashboard (the screen that shows the bottom bar).
 */
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
    data class HealthCareCategory(
      val category: HealthCategoryGroup.HealthCategory,
    ) : Overview()

    @Serializable
    data class UiSchema(
      val organization: MgoOrganization,
      val referenceId: MgoResourceReferenceId,
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
    data class HealthCareCategories(
      val organization: MgoOrganization,
    ) : Organizations()

    @Serializable
    data class HealthCareCategory(
      val category: HealthCategoryGroup.HealthCategory,
      val filterOrganization: MgoOrganization,
    ) : Organizations()

    @Serializable
    data class UiSchema(
      val organization: MgoOrganization,
      val referenceId: MgoResourceReferenceId,
      val isSummary: Boolean,
    ) : Organizations()

    @Serializable
    data class RemoveOrganization(
      val organizationId: String,
      val organizationName: String,
    ) : Organizations()
  }

  @Serializable
  sealed class Settings {
    @Serializable
    data object Root : Settings()

    @Serializable
    data object Home : Settings()

    @Serializable
    data object Display : Settings()

    @Serializable
    data object Security : Settings()

    @Serializable
    data object Advanced : Settings()

    @Serializable
    sealed class About : Settings() {
      @Serializable
      data object Home : About()

      @Serializable
      data object Safety : About()

      @Serializable
      data object OpenSource : About()

      @Serializable
      data object Accessibility : About()
    }
  }
}
