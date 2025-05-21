package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceReferenceId

/**
 * Handles various operations on [MgoResource].
 */
interface MgoResourceRepository {
  /**
   * Fetches health care data from given endpoint and maps to [MgoResource].
   *
   * @param endpoint The endpoint to get the data from.
   * @param request The [HealthCareRequest] used to construct the url.
   * @return [Result] that if successful, contains a list of [MgoResource].
   */
  suspend fun get(
    endpoint: String,
    request: HealthCareRequest,
  ): Result<List<MgoResource>>

  /**
   * Get health care data from the store.
   *
   * @param referenceId The [MgoResourceReferenceId] for the [MgoResource] to get.
   * @return [Result] that if successful ([MgoResource] exists in store), returns the [MgoResource].
   */
  suspend fun get(referenceId: MgoResourceReferenceId): Result<MgoResource>

  /**
   * Filters a list of [MgoResource] based on the [MgoResourceProfile].
   *
   * @param resources A list of [MgoResource]. Will be filtered to contain only the provided [MgoResourceProfile].
   * @param profiles A list of [MgoResourceProfile].
   * @return The filtered list of [MgoResource].
   */
  suspend fun filter(
    resources: List<MgoResource>,
    profiles: List<String>,
  ): List<MgoResource>
}
