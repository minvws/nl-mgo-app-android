package nl.rijksoverheid.mgo.navigation.dashboard

import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory as HealthCareCategoryModel

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
    data class HealthCareCategory(val category: HealthCareCategoryModel) : Overview()

    @Serializable
    data class UiSchema(
      val organization: MgoOrganization,
      val mgoResource: MgoResource,
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
    data class UiSchema(
      val organization: MgoOrganization,
      val mgoResource: MgoResource,
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
