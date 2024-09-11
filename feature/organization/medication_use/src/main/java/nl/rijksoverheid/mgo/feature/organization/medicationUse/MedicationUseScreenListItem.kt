package nl.rijksoverheid.mgo.feature.organization.medicationUse

import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.UISchema

data class MedicationUseScreenListItem(
    val title: String,
    val subtitle: String,
    val uiSchema: UISchema,
)

val TEST_LIST_ITEM_1 =
    MedicationUseScreenListItem(
        title = "Zestril tablet 10mg",
        subtitle = "Tandarts Tandje Erbij",
        uiSchema = TEST_UI_SCHEMA_MEDICATION,
    )

val TEST_LIST_ITEM_2 =
    MedicationUseScreenListItem(
        title = "Atorvastatine Calcium 20 mg Tabletten",
        subtitle = "Streekziekenhuis Willem Alexander",
        uiSchema = TEST_UI_SCHEMA_MEDICATION,
    )

val TEST_LIST_ITEM_3 =
    MedicationUseScreenListItem(
        title = "Amoxicilline Trihydraat 500 mg Capsules",
        subtitle = "Huisartsenpraktijk De Haven",
        uiSchema = TEST_UI_SCHEMA_MEDICATION,
    )
