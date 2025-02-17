package nl.rijksoverheid.mgo.data.fhirParser.shared

import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadLink
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.fhirParser.models.SingleValue

val TEST_UI_SCHEMA_MEDICATION =
    HealthUiSchema(
        label = "Zestril tablet 10mg",
        children =
            listOf(
                HealthUiGroup(
                    label = "Algemeen",
                    children =
                        listOf(
                            SingleValue(
                                label = "Gebruiksaanwijzing",
                                display =
                                    "1 keer per dag 1 capsule een half uur voor het ontbijt heel " +
                                        "doorslikken, niet kauwen",
                                type = "SINGLE_VALUE",
                            ),
                            SingleValue(
                                label = "Reden",
                                display = "Boezemfibrilleren/-fladderen",
                                type = "SINGLE_VALUE",
                            ),
                        ),
                ),
                HealthUiGroup(
                    label = "Periode van gebruik",
                    children =
                        listOf(
                            SingleValue(
                                label = "Startdatum",
                                display = "9 maart 2022",
                                type = "SINGLE_VALUE",
                            ),
                            SingleValue(
                                label = "Startdatum",
                                display = "Niet bekend",
                                type = "SINGLE_VALUE",
                            ),
                        ),
                ),
            ),
    )

val TEST_UI_ENTRY =
    SingleValue(
        label = "UI Entry Label",
        display = "Display",
        type = "SINGLE_VALUE",
    )

val TEST_UI_ENTRY_DOWNLOAD_LINK =
    DownloadLink(
        type = "DOWNLOAD_LINK",
        label = "UI Entry Label",
        url = "fhir",
    )

val TEST_UI_SCHEMA_GROUP =
    HealthUiGroup(
        label = "UI Schema Group",
        children =
            listOf(
                TEST_UI_ENTRY,
            ),
    )

val TEST_UI_SCHEMA =
    HealthUiSchema(
        label = "UI Schema Label",
        children = listOf(TEST_UI_SCHEMA_GROUP),
    )
