package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION

data class HealthCategoryScreenListItem(
    val title: String,
    val subtitle: String,
    val mgoResource: MgoResourceJson,
    val organization: MgoOrganization,
)

val TEST_LIST_ITEM_1 =
    HealthCategoryScreenListItem(
        title = "Zestril tablet 10mg",
        subtitle = "Tandarts Tandje Erbij",
        mgoResource = "",
        organization = TEST_MGO_ORGANIZATION,
    )

val TEST_LIST_ITEM_2 =
    HealthCategoryScreenListItem(
        title = "Atorvastatine Calcium 20 mg Tabletten",
        subtitle = "Streekziekenhuis Willem Alexander",
        mgoResource = "",
        organization = TEST_MGO_ORGANIZATION,
    )

val TEST_LIST_ITEM_3 =
    HealthCategoryScreenListItem(
        title = "Amoxicilline Trihydraat 500 mg Capsules",
        subtitle = "Huisartsenpraktijk De Haven",
        mgoResource = "",
        organization = TEST_MGO_ORGANIZATION,
    )
