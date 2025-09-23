package nl.rijksoverheid.mgo.data.healthData.configuration.models

import androidx.annotation.VisibleForTesting
import kotlinx.serialization.Serializable

typealias HealthCategoryProfile = String
typealias HealthCategoryId = String

/**
 * Represents a top-level group of medical categories as defined in a shared JSON file.
 *
 * This data model is a direct 1:1 mapping of the JSON structure exchanged between platforms
 * (e.g., Android, iOS, Web). It is not intended for UI usage.
 *
 * @property id Unique identifier for the category group.
 * @property heading Display title of the category group.
 * @property categories List of [HealthCategory] items belonging to this group.
 */
@Serializable
data class HealthCategoryGroupConfig(
  val id: String,
  val heading: String,
  val categories: List<HealthCategory>,
) {
  /**
   * Represents a single health category within a group of medical categories.
   *
   * @property id Unique identifier for the category.
   * @property heading Heading of the category.
   * @property subheading Subheading of the category.
   * @property subcategories List of [Subcategory] objects associated with this category.
   */
  @Serializable
  data class HealthCategory(
    val id: HealthCategoryId,
    val heading: String,
    val subheading: String,
    val subcategories: List<Subcategory>,
  ) {
    /**
     * Represents a leaf-level subcategory within a [HealthCategory].
     *
     * @param heading The heading of ths subcategory.
     * @param profiles The profiles of the category. Read more about profiles and how they are used in the README.md of this module.
     */
    @Serializable
    data class Subcategory(
      val heading: String,
      val profiles: List<String>,
    )
  }
}

@VisibleForTesting
val TEST_HEALTH_CATEGORY_ALERTS =
  HealthCategoryGroupConfig.HealthCategory(
    id = "alerts",
    heading = "hc_alerts.heading",
    subheading = "hc_alerts.subheading",
    subcategories =
      listOf(
        HealthCategoryGroupConfig.HealthCategory.Subcategory(
          heading = "zib_alert.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-Alert"),
        ),
      ),
  )

@VisibleForTesting
val TEST_HEALTH_CATEGORY_PROBLEMS =
  HealthCategoryGroupConfig.HealthCategory(
    id = "problems",
    heading = "hc_problems.heading",
    subheading = "hc_problems.subheading",
    subcategories =
      listOf(
        HealthCategoryGroupConfig.HealthCategory.Subcategory(
          heading = "zib_problem.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-Problem"),
        ),
      ),
  )

@VisibleForTesting
val TEST_HEALTH_CATEGORY_GROUP_HEALTH =
  HealthCategoryGroupConfig(
    id = "health",
    heading = "mhc_health.heading",
    categories = listOf(TEST_HEALTH_CATEGORY_ALERTS, TEST_HEALTH_CATEGORY_PROBLEMS),
  )
