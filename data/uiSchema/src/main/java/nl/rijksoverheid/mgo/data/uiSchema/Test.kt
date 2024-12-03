package nl.rijksoverheid.mgo.data.uiSchema

val TEST_UI_SCHEMA_MEDICATION =
    UISchema(
        label = "Zestril tablet 10mg",
        children =
            listOf(
                UISchemaGroup(
                    label = "Algemeen",
                    children =
                        listOf(
                            UIEntry(
                                label = "Gebruiksaanwijzing",
                                display =
                                    UIEntryDisplay.StringValue(
                                        "1 keer per dag 1 capsule een half uur voor het ontbijt heel " +
                                            "doorslikken, niet kauwen",
                                    ),
                                type = UIEntryType.SingleValue,
                            ),
                            UIEntry(
                                label = "Reden",
                                display = UIEntryDisplay.StringValue("Boezemfibrilleren/-fladderen"),
                                type = UIEntryType.SingleValue,
                            ),
                        ),
                ),
                UISchemaGroup(
                    label = "Periode van gebruik",
                    children =
                        listOf(
                            UIEntry(
                                label = "Startdatum",
                                display = UIEntryDisplay.StringValue("9 maart 2022"),
                                type = UIEntryType.SingleValue,
                            ),
                            UIEntry(
                                label = "Einddatum",
                                display = UIEntryDisplay.StringValue("Niet bekend"),
                                type = UIEntryType.SingleValue,
                            ),
                        ),
                ),
            ),
    )

val TEST_UI_ENTRY =
    UIEntry(
        label = "UI Entry Label",
        display = UIEntryDisplay.StringValue("Display"),
        type = UIEntryType.SingleValue,
    )

val TEST_UI_ENTRY_DOWNLOAD_LINK =
    TEST_UI_ENTRY.copy(
        url = "example.pdf",
        type = UIEntryType.DownloadLink,
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
