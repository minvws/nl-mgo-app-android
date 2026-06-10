package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup.ListItemsGroup

data class HealthCategoryScreenListItem(
  val title: String,
  val subtitle: String,
  val detail: String?,
  val mgoResource: MgoResource,
  val organization: MgoOrganization,
)

val TEST_LIST_ITEM_1 =
  HealthCategoryScreenListItem(
    title = "Zestril tablet 10mg",
    subtitle = "Tandarts Tandje Erbij",
    detail = "01-01-1970",
    mgoResource = TEST_MGO_RESOURCE,
    organization = TEST_MGO_ORGANIZATION,
  )

val TEST_LIST_ITEM_2 =
  HealthCategoryScreenListItem(
    title = "Atorvastatine Calcium 20 mg Tabletten",
    subtitle = "Streekziekenhuis Willem Alexander",
    detail = "01-01-1970",
    mgoResource = TEST_MGO_RESOURCE,
    organization = TEST_MGO_ORGANIZATION,
  )

val TEST_LIST_ITEM_3 =
  HealthCategoryScreenListItem(
    title = "Amoxicilline Trihydraat 500 mg Capsules",
    subtitle = "Huisartsenpraktijk De Haven",
    detail = "01-01-1970",
    mgoResource = TEST_MGO_RESOURCE,
    organization = TEST_MGO_ORGANIZATION,
  )

val TEST_LIST_ITEM_GROUP_GROUPED_BY_SUBCATEGORY =
  ListItemsGroup(
    heading = "Wat je nu gebruikt",
    items = listOf(TEST_LIST_ITEM_1, TEST_LIST_ITEM_2, TEST_LIST_ITEM_3),
  )

val TEST_LIST_ITEM_GROUP_GROUPED_BY_DATE_1 =
  ListItemsGroup(
    heading = "Vandaag",
    items = listOf(TEST_LIST_ITEM_1, TEST_LIST_ITEM_2),
  )

val TEST_LIST_ITEM_GROUP_GROUPED_BY_DATE_2 =
  ListItemsGroup(
    heading = "Gisteren",
    items = listOf(TEST_LIST_ITEM_3),
  )

val TEST_LIST_ITEM_GROUP_GROUPED_BY_DATE_3 =
  ListItemsGroup(
    heading = "2 Jul 1991",
    items = listOf(TEST_LIST_ITEM_3),
  )

val TEST_LIST_ITEM_GROUP_GROUPED_BY_DATE_4 =
  ListItemsGroup(
    heading = null,
    items = listOf(TEST_LIST_ITEM_1, TEST_LIST_ITEM_2, TEST_LIST_ITEM_3),
  )
