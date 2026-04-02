package nl.rijksoverheid.mgo.component.fhir

import nl.rijksoverheid.mgo.data.fhir.FhirRequest
import nl.rijksoverheid.mgo.data.healthCategories.models.Endpoint

fun Endpoint.toFhirRequest(): FhirRequest =
  FhirRequest(
    dataServiceId = dataServiceId,
    endpointId = endpointId,
    endpointPath = endpointPath,
    resourceEndpoint = resourceEndpoint,
    fhirVersion = fhirVersion,
    organizationId = organization.id,
    medmijId = organization.medMijId,
    organizationName = organization.name,
  )
