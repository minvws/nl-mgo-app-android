package nl.rijksoverheid.mgo.data.healthcare.mgoResource.category

data class HealthCareCategory(
  val id: HealthCareCategoryId,
  val favorite: Boolean,
)

val TEST_HEALTH_CARE_CATEGORIES = HealthCareCategoryId.entries.map { id -> HealthCareCategory(id = id, favorite = false) }
