package nl.rijksoverheid.mgo.component.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Organization(
  val id: String,
  @SerialName("display_name") val displayName: String,
  @SerialName("search_blob") val searchBlob: String,
)
