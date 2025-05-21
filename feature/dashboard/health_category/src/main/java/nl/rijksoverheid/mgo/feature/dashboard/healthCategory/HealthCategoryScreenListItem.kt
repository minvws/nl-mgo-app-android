package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Represents a list item to show in [HealthCategoryScreen].
 *
 * @param title The title of the list item.
 * @param subtitle The sub title of the list item.
 * @param mgoResource The [MgoResource] that is used to display health care data in the list item.
 * @param organization The [MgoOrganization] that is used to display the organization in the list item.
 */
data class HealthCategoryScreenListItem(
  val title: String,
  val subtitle: String,
  val mgoResource: MgoResource,
  val organization: MgoOrganization,
)

val TEST_LIST_ITEM_1 =
  HealthCategoryScreenListItem(
    title = "Zestril tablet 10mg",
    subtitle = "Tandarts Tandje Erbij",
    mgoResource = TEST_MGO_RESOURCE,
    organization = TEST_MGO_ORGANIZATION,
  )

val TEST_LIST_ITEM_2 =
  HealthCategoryScreenListItem(
    title = "Atorvastatine Calcium 20 mg Tabletten",
    subtitle = "Streekziekenhuis Willem Alexander",
    mgoResource = TEST_MGO_RESOURCE,
    organization = TEST_MGO_ORGANIZATION,
  )

val TEST_LIST_ITEM_3 =
  HealthCategoryScreenListItem(
    title = "Amoxicilline Trihydraat 500 mg Capsules",
    subtitle = "Huisartsenpraktijk De Haven",
    mgoResource = TEST_MGO_RESOURCE,
    organization = TEST_MGO_ORGANIZATION,
  )

val TEST_LIST_ITEM_GROUP_1 =
  HealthCategoryScreenListItemsGroup(
    heading = CopyR.string.zib_medication_use_heading,
    items = listOf(TEST_LIST_ITEM_1, TEST_LIST_ITEM_2, TEST_LIST_ITEM_3),
  )
