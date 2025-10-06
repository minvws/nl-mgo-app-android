package nl.rijksoverheid.mgo.data.healthCategories.models

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
data class HealthCategoryGroup(
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

fun HealthCategoryGroup.HealthCategory.getEndpoints(dataSets: List<DataSet>): List<EndpointsWithDataSetId> {
  val profilesForCategory = this.subcategories.map { subcategory -> subcategory.profiles }.flatten()
  return dataSets
    .map { dataSet ->
      val endpoints = dataSet.endpoints.filter { endpoint -> endpoint.profiles.any { it in profilesForCategory } }
      EndpointsWithDataSetId(
        id = dataSet.id,
        endpoints = endpoints,
      )
    }.filter { it.endpoints.isNotEmpty() }
}
