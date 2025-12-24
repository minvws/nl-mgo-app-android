package nl.rijksoverheid.mgo.component.fhir

import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirRequest
import nl.rijksoverheid.mgo.data.healthCategories.models.Endpoint
import java.time.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named

class FetchEndpoint
  @Inject
  constructor(
    @Named("systemUTC") private val clock: Clock,
    private val fhirRepository: FhirRepository,
  ) {
    suspend operator fun invoke(
      endpoint: Endpoint,
      forceRefresh: Boolean,
    ) {
      val today = LocalDate.now(clock)
      val endpointPath = endpoint.endpointPath.replace("{{today}}", today.format(DateTimeFormatter.ISO_LOCAL_DATE))

      val request =
        FhirRequest(
          organizationId = endpoint.organization.id,
          medmijId = endpoint.organization.medMijId,
          dataServiceId = endpoint.dataServiceId,
          endpointId = endpoint.endpointId,
          endpointPath = endpointPath,
          resourceEndpoint = endpoint.resourceEndpoint,
          fhirVersion = endpoint.fhirVersion,
        )

      fhirRepository.fetch(request = request, forceRefresh = forceRefresh)
    }
  }
