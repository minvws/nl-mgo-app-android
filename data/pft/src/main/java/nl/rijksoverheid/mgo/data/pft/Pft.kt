package nl.rijksoverheid.mgo.data.pft

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class PftSnomedCode(
  val code: String,
)

@Serializable
data class Pft(
  val name: String? = null,
  val synonym: String? = null,
  val description: String,
)
