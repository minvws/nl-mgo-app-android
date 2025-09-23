package nl.rijksoverheid.mgo.data.healthData.health

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import nl.rijksoverheid.mgo.data.healthData.configuration.HealthDataConfigurationRepository
import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryGroupConfig
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryId
import nl.rijksoverheid.mgo.data.healthData.fhir.FhirDataRepository
import nl.rijksoverheid.mgo.data.healthData.health.models.HealthData
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataService
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import javax.inject.Inject

/**
 * Repository for accessing and observing health data.
 */
internal class DefaultHealthDataRepository
  @Inject
  constructor(
    private val configurationRepository: HealthDataConfigurationRepository,
    private val fhirDataRepository: FhirDataRepository,
    private val cacheFileStore: CacheFileStore,
  ) : HealthDataRepository {
    private val cachedHealthData = MutableStateFlow<List<HealthData>>(listOf())

    /**
     * Fetches health data for a [HealthCategoryGroupConfig.HealthCategory] and [MgoOrganization].
     *
     * @param category The [HealthCategoryGroupConfig.HealthCategory] to fetch the health data for.
     * @param organization The [MgoOrganization] to fetch the health data for.
     */
    override suspend fun fetch(
      category: HealthCategoryGroupConfig.HealthCategory,
      organization: MgoOrganization,
    ) {
      // Get all the profiles that exist for this category
      val profilesForCategory = category.subcategories.map { subcategory -> subcategory.profiles }.flatten()

      // Get the configuration that defines which calls we need to make to get the health data based on the profiles.
      val dataSetConfigurations = configurationRepository.getDataSets()

      // Emit loading states for category
      for (dataService in organization.dataServices) {
        // Get the data set configuration
        val dataSetConfiguration = dataSetConfigurations.firstOrNull { configuration -> configuration.id == dataService.id } ?: return

        // Get the endpoints that we need to call
        val endpoints = dataSetConfiguration.endpoints.filter { endpoint -> endpoint.profiles.any { it in profilesForCategory } }
        for (endpoint in endpoints) {
          updateCachedHealthData(
            HealthData.Loading(organization = organization, dataServiceId = dataService.id, profiles = endpoint.profiles),
          )
        }
      }

      for (dataService in organization.dataServices) {
        // Get the data set configuration
        val dataSetConfiguration = dataSetConfigurations.firstOrNull { configuration -> configuration.id == dataService.id } ?: return

        // Get the endpoints that we need to call
        val endpoints = dataSetConfiguration.endpoints.filter { endpoint -> endpoint.profiles.any { it in profilesForCategory } }
        for (endpoint in endpoints) {
          // If the fhir response is cached, do not fetch it again
          val cachedFhirResponse = cacheFileStore.getFile(getFhirResponseFileName(organization = organization, dataService = dataService, endpoint = endpoint))
          if (cachedFhirResponse != null) {
            updateCachedHealthData(
              HealthData.Success(
                organization = organization,
                dataServiceId = dataService.id,
                profiles = endpoint.profiles,
                fhirResponse = cachedFhirResponse,
              ),
            )
            continue
          }

          // For each endpoint, do the request to get the health data
          // TODO: This will be returned in the endpoint
          val fhirVersion =
            when (dataSetConfiguration.fhirVersion) {
              "R3" -> "3.0"
              "R4" -> "4.0"
              else -> return
            }

          // Do the request
          val responseBodyResult = fhirDataRepository.fetch(resourceEndpoint = dataService.resourceEndpoint, endpoint = endpoint, fhirVersion = fhirVersion)

          // If request succeeded, emit success state
          responseBodyResult.getOrNull()?.let { responseBody ->
            val file =
              cacheFileStore.saveFile(
                name = getFhirResponseFileName(organization = organization, dataService = dataService, endpoint = endpoint),
                contentType = "application/json",
                responseBody.string().toByteArray(Charsets.UTF_8),
              )

            updateCachedHealthData(
              HealthData.Success(organization = organization, dataServiceId = dataService.id, profiles = endpoint.profiles, fhirResponse = file),
            )
          }

          // If request failed, emit failed state
          responseBodyResult.exceptionOrNull()?.let { error ->
            updateCachedHealthData(
              HealthData.Error(organization = organization, dataServiceId = dataService.id, profiles = endpoint.profiles, error = error),
            )
          }
        }
      }
    }

    private fun getFhirResponseFileName(
      organization: MgoOrganization,
      dataService: MgoOrganizationDataService,
      endpoint: DataSetConfig.Endpoint,
    ): String = "${organization.id}_${dataService.id}_${endpoint.id}"

    private fun updateCachedHealthData(newHealthData: HealthData) {
      cachedHealthData.update { cachedHealthData ->
        val existing =
          cachedHealthData.find { healthData ->
            healthData.profiles == newHealthData.profiles && healthData.organization == newHealthData.organization &&
              healthData.dataServiceId == newHealthData.dataServiceId
          }
        if (existing == null) {
          cachedHealthData.toMutableList().also { it.add(newHealthData) }
        } else {
          val index = cachedHealthData.indexOf(existing)
          cachedHealthData.toMutableList().also { it[index] = newHealthData }
        }
      }
    }

    /**
     * Observes health data updates for the given [HealthCategoryId].
     *
     * @param category The [HealthCategoryGroupConfig.HealthCategory] to observe health data for.
     * @return A [Flow] that emits the current list of [HealthData] and updates whenever data changes.
     */
    override fun observe(category: HealthCategoryGroupConfig.HealthCategory): Flow<List<HealthData>> =
      cachedHealthData.map { cachedHealthDataList ->
        val categoryProfiles = category.subcategories.map { subcategory -> subcategory.profiles }.flatten()
        cachedHealthDataList.filter { cachedHealthData -> cachedHealthData.profiles.any { it in categoryProfiles } }
      }
  }
