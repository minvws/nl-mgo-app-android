package nl.rijksoverheid.mgo.data.categories

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

typealias HealthGroupId = String

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class HealthGroup(
  val id: HealthGroupId,
  val heading: String,
  val categories: List<HealthCategory>,
) {
  @Serializable
  data class HealthCategory(
    val id: String,
    val heading: String,
    val subheading: String,
    val subcategories: List<SubCategory>,
  ) {
    @Serializable
    data class SubCategory(
      val heading: String,
      val profiles: List<String>,
    )
  }
}
