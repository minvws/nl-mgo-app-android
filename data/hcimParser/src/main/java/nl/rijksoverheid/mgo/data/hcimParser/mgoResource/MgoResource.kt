package nl.rijksoverheid.mgo.data.hcimParser.mgoResource

data class MgoResource(
  val referenceId: String,
  val profile: String,
  val json: String,
)

val TEST_MGO_RESOURCE =
  MgoResource(
    referenceId = "1",
    profile = "",
    json = "",
  )
