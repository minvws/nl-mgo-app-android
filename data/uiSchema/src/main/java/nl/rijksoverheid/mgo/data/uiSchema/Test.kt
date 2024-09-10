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
                            ChildElement(
                                label = "Gebruiksaanwijzing",
                                display =
                                    ChildDisplay.StringValue(
                                        "1 keer per dag 1 capsule een half uur voor het ontbijt heel " +
                                            "doorslikken, niet kauwen",
                                    ),
                                type = "",
                            ),
                            ChildElement(
                                label = "Reden",
                                display = ChildDisplay.StringValue("Boezemfibrilleren/-fladderen"),
                                type = "",
                            ),
                        ),
                ),
                UISchemaGroup(
                    label = "Periode van gebruik",
                    children =
                        listOf(
                            ChildElement(
                                label = "Startdatum",
                                display = ChildDisplay.StringValue("9 maart 2022"),
                                type = "",
                            ),
                            ChildElement(
                                label = "Einddatum",
                                display = ChildDisplay.StringValue("Niet bekend"),
                                type = "",
                            ),
                        ),
                ),
            ),
    )
