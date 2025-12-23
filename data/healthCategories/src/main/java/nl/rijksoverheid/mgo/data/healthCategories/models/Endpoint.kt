package nl.rijksoverheid.mgo.data.healthCategories.models

import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion

data class Endpoint(
  val dataServiceId: String,
  val endpointId: String,
  val endpointPath: String,
  val resourceEndpoint: String,
  val fhirVersion: FhirVersion,
  val organization: MgoOrganization,
)

val TEST_ENDPOINT =
  Endpoint(
    dataServiceId = "1",
    endpointId = "1",
    endpointPath = "",
    resourceEndpoint = "",
    fhirVersion = FhirVersion.R3,
    organization = TEST_MGO_ORGANIZATION,
  )
