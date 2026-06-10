package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.type.HealthCategoryScreenType
import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoByteArrayStorage
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.toString

internal class ObserveListItemsState
  @Inject
  constructor(
    private val observeFhirResponses: ObserveFhirResponses,
    private val mgoResourceParser: MgoResourceParser,
    private val mgoResourceStore: MgoResourceStore,
    private val listItemsStateMapper: ListItemsStateMapper,
    @Named("encryptedMgoByteArrayStorage") private val mgoByteArrayStorage: MgoByteArrayStorage,
  ) {
    operator fun invoke(
      type: HealthCategoryScreenType,
      category: HealthCategoryGroup.HealthCategory,
      organizations: List<MgoOrganization>,
    ): Flow<HealthCategoryScreenViewState.ListItemsState> =
      observeFhirResponses(organizations = organizations, categories = listOf(category)).map { responses ->

        // Create mgo resources
        val mgoResources =
          responses
            .filterIsInstance<FhirResponse.Success>()
            .flatMap { response ->
              mgoResourceParser(
                fhirResponse = mgoByteArrayStorage.get(response.cacheKey)?.toString(Charsets.UTF_8) ?: "{}",
                fhirVersion = response.request.fhirVersion,
                organizationId = response.request.organizationId,
                organizationName = response.request.organizationName,
              )
            }

        // Cache mgo resources
        for (mgoResource in mgoResources) {
          mgoResourceStore.store(mgoResource)
        }

        // Create list items from mgo resources
        listItemsStateMapper(type = type, responses = responses, mgoResources = mgoResources, category = category)
      }
  }
