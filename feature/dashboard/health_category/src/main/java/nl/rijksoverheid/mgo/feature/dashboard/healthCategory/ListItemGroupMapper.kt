package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rijksoverheid.mgo.component.healthCategories.getString
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject
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
    private val uiSchemaParser: UiSchemaParser,
  ) {
    suspend fun invoke(
      category: HealthCategoryGroup.HealthCategory,
      mgoResourcesWithOrganization: List<MgoResourceWithOrganization>,
    ): List<HealthCategoryScreenListItemsGroup> {
      val subcategories = category.subcategories
      val mgoResourcesForSubcategory =
        subcategories.associateWith { subcategory ->
          mgoResourcesWithOrganization.filter { subcategory.profiles.contains(it.mgoResource.profile) }
        }
      return mgoResourcesForSubcategory.toListItems()
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
