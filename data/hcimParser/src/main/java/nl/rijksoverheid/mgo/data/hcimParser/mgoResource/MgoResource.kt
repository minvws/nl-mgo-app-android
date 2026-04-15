package nl.rijksoverheid.mgo.data.hcimParser.mgoResource

typealias MgoResourceReferenceId = String

data class MgoResource(
  val organizationId: String,
  val organizationName: String,
  val referenceId: MgoResourceReferenceId,
  val profile: String,
  val json: String,
)

val TEST_MGO_RESOURCE =
  MgoResource(
    organizationId = "",
    organizationName = "",
    referenceId = "1",
    profile = "",
    json = "",
  )
