package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.UISchema

data class HealthCategoryScreenListItem(
    val title: String,
    val subtitle: String,
    val uiSchema: UISchema,
)

val TEST_LIST_ITEM_1 =
    HealthCategoryScreenListItem(
        title = "Zestril tablet 10mg",
        subtitle = "Tandarts Tandje Erbij",
        uiSchema = TEST_UI_SCHEMA_MEDICATION,
    )

val TEST_LIST_ITEM_2 =
    HealthCategoryScreenListItem(
        title = "Atorvastatine Calcium 20 mg Tabletten",
        subtitle = "Streekziekenhuis Willem Alexander",
        uiSchema = TEST_UI_SCHEMA_MEDICATION,
    )

val TEST_LIST_ITEM_3 =
    HealthCategoryScreenListItem(
        title = "Amoxicilline Trihydraat 500 mg Capsules",
        subtitle = "Huisartsenpraktijk De Haven",
        uiSchema = TEST_UI_SCHEMA_MEDICATION,
    )
