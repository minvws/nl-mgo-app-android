package nl.rijksoverheid.mgo.data.healthData.health.models

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceId
import java.io.File

sealed class HealthData(
  open val organization: MgoOrganization,
  open val dataServiceId: MgoOrganizationDataServiceId,
  open val profiles: List<String>,
) {
  data class Loading(
    override val organization: MgoOrganization,
    override val dataServiceId: MgoOrganizationDataServiceId,
    override val profiles: List<String>,
  ) : HealthData(organization, dataServiceId, profiles)

  data class Success(
    override val organization: MgoOrganization,
    override val dataServiceId: MgoOrganizationDataServiceId,
    override val profiles: List<String>,
    val fhirResponse: File,
  ) : HealthData(organization, dataServiceId, profiles)

  data class Error(
    override val organization: MgoOrganization,
    override val dataServiceId: MgoOrganizationDataServiceId,
    override val profiles: List<String>,
    val error: Throwable,
  ) : HealthData(organization, dataServiceId, profiles)
}
