package nl.rijksoverheid.mgo.data.fhir

import nl.rijksoverheid.mgo.framework.fhir.FhirVersion

data class FhirRequest(
  val organizationId: String,
  val medmijId: String?,
  val dataServiceId: String,
  val endpointId: String,
  val endpointPath: String,
  val resourceEndpoint: String,
  val fhirVersion: FhirVersion,
)

val TEST_FHIR_REQUEST =
  FhirRequest(
    organizationId = "1",
    medmijId = "1",
    dataServiceId = "1",
    endpointId = "1",
    endpointPath = "",
    resourceEndpoint = "",
    fhirVersion = FhirVersion.R3,
  )

val TEST_FHIR_REQUEST_ALCOHOL_USE =
  FhirRequest(
    organizationId = "1",
    medmijId = "1",
    dataServiceId = "48",
    endpointId = "alcoholUse",
    endpointPath = "/Observation?code=http://snomed.info/sct|228273003",
    resourceEndpoint = "",
    fhirVersion = FhirVersion.R3,
  )

val TEST_FHIR_REQUEST_DRUG_USE =
  FhirRequest(
    organizationId = "1",
    medmijId = "1",
    dataServiceId = "48",
    endpointId = "drugUse",
    endpointPath = "/Observation?code=http://snomed.info/sct|228366006",
    resourceEndpoint = "",
    fhirVersion = FhirVersion.R3,
  )

val TEST_FHIR_REQUEST_TOBACCO_USE =
  FhirRequest(
    organizationId = "1",
    medmijId = "1",
    dataServiceId = "48",
    endpointId = "tobaccoUse",
    endpointPath = "/Observation?code=http://snomed.info/sct|365980008",
    resourceEndpoint = "",
    fhirVersion = FhirVersion.R3,
  )

val TEST_FHIR_REQUEST_LIVING_SITUATION =
  FhirRequest(
    organizationId = "1",
    medmijId = "1",
    dataServiceId = "48",
    endpointId = "livingSituation",
    endpointPath = "/Observation/\$lastn?code=http://snomed.info/sct|365508006",
    resourceEndpoint = "",
    fhirVersion = FhirVersion.R3,
  )

val TEST_FHIR_REQUEST_NUTRITION_ADVICE =
  FhirRequest(
    organizationId = "1",
    medmijId = "1",
    dataServiceId = "48",
    endpointId = "nutritionAdvice",
    endpointPath = "/NutritionOrder",
    resourceEndpoint = "",
    fhirVersion = FhirVersion.R3,
  )
