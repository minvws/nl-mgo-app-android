package nl.rijksoverheid.mgo.data.healthcare.mgoResource.category

/**
 * Wrapper for [HealthCareCategoryId] to also include if this category is marked as a favorite or not.
 *
 * @param id The [HealthCareCategoryId].
 * @param favoritePosition -1 if not favorite, else the position of the favorite.
 */
data class HealthCareCategory(
  val id: HealthCareCategoryId,
  val favoritePosition: Int,
)

val TEST_HEALTH_CARE_CATEGORIES = HealthCareCategoryId.entries.map { id -> HealthCareCategory(id = id, favoritePosition = -1) }
