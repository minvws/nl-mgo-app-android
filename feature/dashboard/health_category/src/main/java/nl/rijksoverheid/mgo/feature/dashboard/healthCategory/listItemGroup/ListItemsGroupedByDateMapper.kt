package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup

import android.content.Context
import androidx.annotation.VisibleForTesting
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.first
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.IheMhdMinimalDocumentReference
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.R4BbsDocumentReference
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItem
import java.time.Clock
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject
import javax.inject.Named
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal class ListItemsGroupedByDateMapper
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
    @Named("systemUTC") private val clock: Clock,
    private val organizationRepository: OrganizationRepository,
    private val uiSchemaParser: UiSchemaParser,
  ) {
    suspend operator fun invoke(mgoResources: List<MgoResource>): List<ListItemsGroup> {
      val groupedMgoResources = getGroupedResources(mgoResources)
      return getListItems(groupedMgoResources)
    }

    /**
     * Group resources by date
     */
    @VisibleForTesting
    fun getGroupedResources(mgoResources: List<MgoResource>): Map<String?, List<MgoResource>> {
      val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
      val today = LocalDate.now(clock)
      val yesterday = today.minusDays(1)
      return mgoResources
        .groupBy { mgoResource ->
          val dateString =
            when (val decodedObject = mgoResource.decodedObject) {
              is IheMhdMinimalDocumentReference -> decodedObject.indexed?.value
              is R4BbsDocumentReference -> decodedObject.date?.value
              else -> null
            }
          dateString
            ?.let { OffsetDateTime.parse(it).toLocalDate() }
        }.toSortedMap(
          compareByDescending { it ?: LocalDate.MIN },
        ).mapKeys { (date, _) ->
          when (date) {
            today -> context.getString(CopyR.string.common_today)
            yesterday -> context.getString(CopyR.string.common_yesterday)
            else -> date?.format(formatter)
          }
        }
    }

    /**
     * Create list items from grouped resources
     */
    @VisibleForTesting
    suspend fun getListItems(groupedMgoResources: Map<String?, List<MgoResource>>): List<ListItemsGroup> =
      groupedMgoResources.map { groupedMgoResource ->
        ListItemsGroup(
          heading = groupedMgoResource.key,
          items =
            groupedMgoResource.value.map { mgoResource ->
              val organization =
                organizationRepository.getSaved(currentCoroutineContext()).first().first { organization ->
                  organization.id ==
                    mgoResource.organizationId
                }
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
