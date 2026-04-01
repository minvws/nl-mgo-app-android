package nl.rijksoverheid.mgo.data.hcimParser.mgoResource

typealias MgoResourceReferenceId = String

data class MgoResource(
  val organizationName: String,
  val referenceId: MgoResourceReferenceId,
  val profile: String,
  val json: String,
)

val TEST_MGO_RESOURCE =
  MgoResource(
    organizationName = "",
    referenceId = "1",
    profile = "",
    json = "",
  )
