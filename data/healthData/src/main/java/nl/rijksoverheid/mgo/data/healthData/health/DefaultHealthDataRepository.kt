package nl.rijksoverheid.mgo.data.healthData.health

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import nl.rijksoverheid.mgo.data.healthData.configuration.HealthDataConfigurationRepository
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryId
import nl.rijksoverheid.mgo.data.healthData.fhir.FhirDataRepository
import nl.rijksoverheid.mgo.data.healthData.health.models.HealthData
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject

internal class DefaultHealthDataRepository
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    private val configurationRepository: HealthDataConfigurationRepository,
    private val fhirDataRepository: FhirDataRepository,
  ) : HealthDataRepository {
    private val cachedHealthData = MutableStateFlow<List<HealthData>>(listOf())

    override suspend fun fetch(categoryId: HealthCategoryId) {
      // Get all the profiles that exist for this category
      val profilesForCategory = categoryId.getProfiles()

      // Get all the organizations that we need to get health data for. This holds information where to get the data from.
      val organizations = organizationRepository.get()

      // Get the configuration that defines which calls we need to make to get the health data based on the profiles.
      val dataSetConfigurations = configurationRepository.getDataSets()

      for (organization in organizations) {
        // For each organization we have saved
        for (dataService in organization.dataServices) {
          // Get the data set configuration
          val dataSetConfiguration = dataSetConfigurations.firstOrNull { configuration -> configuration.id == dataService.id } ?: return

          // Get the endpoints that we need to call
          val endpoints = dataSetConfiguration.endpoints.filter { endpoint -> endpoint.profiles.any { it in profilesForCategory } }
          for (endpoint in endpoints) {
            // For each endpoint, do the request to get the health data
            // TODO: This will be returned in the endpoint
            val fhirVersion =
              when (dataSetConfiguration.fhirVersion) {
                "R3" -> "3.0"
                "R4" -> "4.0"
                else -> return
              }

            // Emit loading state
            updateCachedHealthData(HealthData.Loading(organization = organization, dataServiceId = dataService.id, profiles = endpoint.profiles))

            // Do the request
            val result = fhirDataRepository.fetch(endpoint = endpoint, fhirVersion = fhirVersion)

            // If request succeeded, emit success state
            result.getOrNull()?.let { fhirResponse ->
              updateCachedHealthData(
                HealthData.Success(organization = organization, dataServiceId = dataService.id, profiles = endpoint.profiles, fhirResponse = fhirResponse),
              )
            }

            // If request failed, emit failed state
            result.exceptionOrNull()?.let { error ->
              updateCachedHealthData(
                HealthData.Error(organization = organization, dataServiceId = dataService.id, profiles = endpoint.profiles, error = error),
              )
            }
          }
        }
      }
    }

    private fun HealthCategoryId.getProfiles(): List<String> =
      configurationRepository
        .getGroups()
        .map { group -> group.categories }
        .flatten()
        .first { category -> category.id == this }
        .subcategories
        .map { subcategory -> subcategory.profiles }
        .flatten()

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

    override fun observe(categoryId: HealthCategoryId): Flow<List<HealthData>> =
      cachedHealthData.map { cachedHealthDataList ->
        cachedHealthDataList.filter { cachedHealthData -> cachedHealthData.profiles.any { it in categoryId.getProfiles() } }
      }
  }
