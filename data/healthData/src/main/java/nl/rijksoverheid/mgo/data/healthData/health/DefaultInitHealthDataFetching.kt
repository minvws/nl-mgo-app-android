package nl.rijksoverheid.mgo.data.healthData.health

import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.data.healthData.configuration.HealthDataConfigurationRepository
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject

/**
 * Initializes fetching of the health data of all stored organizations.
 * Observes stored organizations and automatically fetches health data for that organization when it is added.
 *
 * @param organizationRepository The [OrganizationRepository] to observe all stored organizations.
 * @param healthDataRepository The [HealthDataRepository] to fetch the health data.
 * @param healthDataConfigurationRepository The [HealthDataConfigurationRepository] to get configuration how to fetch the health data.
 */
internal class DefaultInitHealthDataFetching
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    private val healthDataRepository: HealthDataRepository,
    private val healthDataConfigurationRepository: HealthDataConfigurationRepository,
  ) : InitHealthDataFetching {
    override suspend fun invoke() {
      val categories = healthDataConfigurationRepository.getGroups().map { group -> group.categories }.flatten()
      organizationRepository.storedOrganizationsFlow.collectLatest { organizations ->
        organizations.forEach { organization ->
          categories.forEach { category ->
            healthDataRepository.fetch(category = category, organization = organization)
          }
        }
      }
    }
  }
