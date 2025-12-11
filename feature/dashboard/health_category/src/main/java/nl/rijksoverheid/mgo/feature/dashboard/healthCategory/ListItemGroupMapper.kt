package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rijksoverheid.mgo.component.healthCategories.getString
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.healthCategories.GetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoByteArrayStorage
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

data class MgoResourceWithOrganization(
  val mgoResource: MgoResource,
  val organization: MgoOrganization,
)

@Singleton
internal class ListItemGroupMapper
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
    private val mgoResourceParser: MgoResourceParser,
    private val uiSchemaParser: UiSchemaParser,
    private val organizationRepository: OrganizationRepository,
    @Named("encryptedMgoByteArrayStorage") private val mgoByteArrayStorage: MgoByteArrayStorage,
    getDataSetsFromDisk: GetDataSetsFromDisk,
  ) {
    private val dataSets = getDataSetsFromDisk()

    suspend fun invoke(
      category: HealthCategoryGroup.HealthCategory,
      fhirResponses: List<FhirResponse.Success>,
    ): List<HealthCategoryScreenListItemsGroup> {
      val subcategories = category.subcategories
      val mgoResourcesWithOrganization = fhirResponses.map { fhirResponse -> fhirResponse.toMgoResourcesWithOrganization() }.flatten()
      val mgoResourcesForSubcategory =
        subcategories.associateWith { subcategory ->
          mgoResourcesWithOrganization.filter { subcategory.profiles.contains(it.mgoResource.profile) }
        }
      return mgoResourcesForSubcategory.toListItems()
    }

    private suspend fun FhirResponse.Success.toMgoResourcesWithOrganization(): List<MgoResourceWithOrganization> {
      // Get the data set that belongs to this response
      val dataSet = dataSets.firstOrNull { dataSet -> dataSet.id == request.dataServiceId } ?: return emptyList()

      // Get the organisation that belongs to this response
      val organization = organizationRepository.get().firstOrNull { organization -> organization.id == request.organizationId } ?: return emptyList()

      // Create the mgo resources
      val mgoResources =
        mgoResourceParser.invoke(
          fhirResponse = mgoByteArrayStorage.get(this.cacheKey)?.toString(Charsets.UTF_8) ?: "{}",
          fhirVersion = dataSet.fhirVersion,
        )
      return mgoResources.map { mgoResource -> MgoResourceWithOrganization(mgoResource = mgoResource, organization = organization) }
    }

    private suspend fun Map<HealthCategoryGroup.HealthCategory.Subcategory, List<MgoResourceWithOrganization>>.toListItems() =
      map {
        HealthCategoryScreenListItemsGroup(
          heading = context.getString(it.key.heading),
          items =
            it.value.map { mgoResourceWithOrganization ->
              val organization = mgoResourceWithOrganization.organization
              val mgoResource = mgoResourceWithOrganization.mgoResource
              val uiSchema =
                uiSchemaParser.getSummary(
                  mgoResourceJson = mgoResource.json,
                  organizationName = organization.name,
                )
              HealthCategoryScreenListItem(
                title = uiSchema.label,
                subtitle = organization.name,
                mgoResource = mgoResource,
                organization = organization,
              )
            },
        )
      }
  }
