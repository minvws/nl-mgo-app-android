package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.TEST_MGO_RESOURCE

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

val TEST_LIST_ITEM_GROUP =
  HealthCategoryScreenListItemsGroup(
    heading = "Wat je nu gebruikt",
    items = listOf(TEST_LIST_ITEM_1, TEST_LIST_ITEM_2, TEST_LIST_ITEM_3),
  )
