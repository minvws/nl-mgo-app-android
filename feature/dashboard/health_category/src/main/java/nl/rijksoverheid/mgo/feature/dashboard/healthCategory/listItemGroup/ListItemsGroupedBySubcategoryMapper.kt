package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import getString
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.first
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ListItemsGroupedBySubcategoryMapper
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
    private val uiSchemaParser: UiSchemaParser,
    private val organizationRepository: OrganizationRepository,
  ) {
    suspend operator fun invoke(
      category: HealthCategoryGroup.HealthCategory,
      mgoResources: List<MgoResource>,
    ): List<ListItemsGroup> {
      val groupedMgoResources = mgoResources.groupBySubCategory(subcategories = category.subcategories)
      return groupedMgoResources.toListItems()
    }

    private suspend fun Map<HealthCategoryGroup.HealthCategory.Subcategory, List<MgoResource>>.toListItems() =
      map {
        ListItemsGroup(
          heading = context.getString(it.key.heading),
          items =
            it.value.map { mgoResource ->
              val organization =
                organizationRepository.getSaved(currentCoroutineContext()).first().first { organization ->
                  organization.id ==
                    mgoResource.organizationId
                }
              val mgoResource = mgoResource
              val cardDetails =
                uiSchemaParser.getCardDetail(
                  mgoResourceJson = mgoResource.json,
                  organizationName = organization.name,
                )
              HealthCategoryScreenListItem(
                title = cardDetails.title,
                subtitle = cardDetails.description ?: "",
                detail = cardDetails.detail,
                mgoResource = mgoResource,
                organization = organization,
              )
            },
        )
      }
  }

internal fun List<MgoResource>.groupBySubCategory(subcategories: List<HealthCategoryGroup.HealthCategory.Subcategory>) =
  subcategories.associateWith { subcategory ->
    this.filter { mgoResource -> subcategory.profiles.contains(mgoResource.profile) }
  }
