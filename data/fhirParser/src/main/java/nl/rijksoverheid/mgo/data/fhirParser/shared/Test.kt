package nl.rijksoverheid.mgo.data.fhirParser.shared

val TEST_UI_SCHEMA_MEDICATION =
    UISchema(
        label = "Zestril tablet 10mg",
        children =
            listOf(
                UISchemaGroup(
                    label = "Algemeen",
                    children =
                        listOf(
                            UIElement(
                                label = "Gebruiksaanwijzing",
                                display =
                                    UIElementDisplay.StringValue(
                                        "1 keer per dag 1 capsule een half uur voor het ontbijt heel " +
                                            "doorslikken, niet kauwen",
                                    ),
                                type = UIElementType.SingleValue,
                            ),
                            UIElement(
                                label = "Reden",
                                display = UIElementDisplay.StringValue("Boezemfibrilleren/-fladderen"),
                                type = UIElementType.SingleValue,
                            ),
                        ),
                ),
                UISchemaGroup(
                    label = "Periode van gebruik",
                    children =
                        listOf(
                            UIElement(
                                label = "Startdatum",
                                display = UIElementDisplay.StringValue("9 maart 2022"),
                                type = UIElementType.SingleValue,
                            ),
                            UIElement(
                                label = "Einddatum",
                                display = UIElementDisplay.StringValue("Niet bekend"),
                                type = UIElementType.SingleValue,
                            ),
                        ),
                ),
            ),
    )

val TEST_UI_ENTRY =
    UIElement(
        label = "UI Entry Label",
        display = UIElementDisplay.StringValue("Display"),
        type = UIElementType.SingleValue,
    )

val TEST_UI_ENTRY_DOWNLOAD_LINK =
    TEST_UI_ENTRY.copy(
        url = "fhir",
        type = UIElementType.DownloadLink,
    )

val TEST_UI_SCHEMA_GROUP =
    UISchemaGroup(
        label = "UI Schema Group",
        children =
            listOf(
                TEST_UI_ENTRY,
            ),
    )

val TEST_UI_SCHEMA =
    UISchema(
        label = "UI Schema Label",
        children = listOf(TEST_UI_SCHEMA_GROUP),
    )
