package nl.rijksoverheid.mgo.component.pdf

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import nl.rijksoverheid.mgo.component.theme.Gray600
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema

data class MgoPdf(
  val fileName: MgoPdfFileName,
  val heading: String,
  val subheading: String,
  val tables: List<Tables>,
) {
  data class Tables(
    val heading: String,
    val tables: List<Table>,
  )

  data class Table(
    val sections: List<Section>,
  )

  data class Section(
    val heading: String,
    val rows: List<Row>,
  )

  data class Row(
    val label: String?,
    val content: List<String>,
    val labelColor: Color = Gray600,
    @field:DrawableRes val labelIcon: ByteArray? = null,
  )
}

fun List<HealthUiSchema>.toMgoPdf(): MgoPdf =
  MgoPdf(
    fileName = "test.pdf",
    heading = "Medicijnen",
    subheading = "Opgeslagen op 1 januari 2026 om 14:45 uur",
    tables =
      listOf(
        MgoPdf.Tables(
          heading = "Wat u nu gebruikt",
          tables =
            listOf(
              MgoPdf.Table(
                sections =
                  listOf(
                    MgoPdf.Section(
                      heading = "Zestril tablet 10mg",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Gebruiksaanwijzing",
                            content = listOf("1 maal per dag 1 tablet, oraal"),
                          ),
                          MgoPdf.Row(
                            label = "Hoeveelheid per keer",
                            content = listOf("1 stuk"),
                          ),
                          MgoPdf.Row(
                            label = "Status",
                            content = listOf("Actief"),
                          ),
                          MgoPdf.Row(
                            label = "Reden gebruik",
                            content = listOf("Gegeneraliseerde psoriasis pustulosa (verspreide psoriasis met puistjes)"),
                          ),
                        ),
                    ),
                    MgoPdf.Section(
                      heading = "Periode van gebruik",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Ingangsdatum",
                            content = listOf("28 juni 2018"),
                          ),
                          MgoPdf.Row(
                            label = "Einddatum",
                            content = listOf("Niet bekend"),
                          ),
                        ),
                    ),
                    MgoPdf.Section(
                      heading = "Voorgeschreven door",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Zorgverlener",
                            content = listOf("Huisartsen, niet nader gespecificeerd"),
                          ),
                          MgoPdf.Row(
                            label = "Zorgaanbieder",
                            content = listOf("Kwalificatie Medmij: BGZ"),
                          ),
                        ),
                    ),
                  ),
              ),
              MgoPdf.Table(
                sections =
                  listOf(
                    MgoPdf.Section(
                      heading = "Metformine tablet 500mg",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Gebruiksaanwijzing",
                            content = listOf("2 maal per dag 1 tablet, oraal bij de maaltijd"),
                          ),
                          MgoPdf.Row(
                            label = "Hoeveelheid per keer",
                            content = listOf("1 stuk"),
                          ),
                          MgoPdf.Row(
                            label = "Status",
                            content = listOf("In gebruik"),
                          ),
                          MgoPdf.Row(
                            label = "Reden gebruik",
                            content = listOf("Type 2 diabetes"),
                          ),
                        ),
                    ),
                    MgoPdf.Section(
                      heading = "Periode van gebruik",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Ingangsdatum",
                            content = listOf("15 januari 2028"),
                          ),
                          MgoPdf.Row(
                            label = "Einddatum",
                            content = listOf("Niet bekend"),
                          ),
                        ),
                    ),
                    MgoPdf.Section(
                      heading = "Voorgeschreven door",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Zorgverlener",
                            content = listOf("Huisartsen, niet nader gespecificeerd"),
                          ),
                          MgoPdf.Row(
                            label = "Zorgaanbieder",
                            content = listOf("Kwalificatie Medmij: BGZ"),
                          ),
                        ),
                    ),
                  ),
              ),
            ),
        ),
        MgoPdf.Tables(
          heading = "Wat u nu gebruikt",
          tables =
            listOf(
              MgoPdf.Table(
                sections =
                  listOf(
                    MgoPdf.Section(
                      heading = "Zestril tablet 10mg",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Gebruiksaanwijzing",
                            content = listOf("1 maal per dag 1 tablet, oraal"),
                          ),
                          MgoPdf.Row(
                            label = "Hoeveelheid per keer",
                            content = listOf("1 stuk"),
                          ),
                          MgoPdf.Row(
                            label = "Status",
                            content = listOf("Actief"),
                          ),
                          MgoPdf.Row(
                            label = "Reden gebruik",
                            content = listOf("Gegeneraliseerde psoriasis pustulosa (verspreide psoriasis met puistjes)"),
                          ),
                        ),
                    ),
                    MgoPdf.Section(
                      heading = "Periode van gebruik",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Ingangsdatum",
                            content = listOf("28 juni 2018"),
                          ),
                          MgoPdf.Row(
                            label = "Einddatum",
                            content = listOf("Niet bekend"),
                          ),
                        ),
                    ),
                    MgoPdf.Section(
                      heading = "Voorgeschreven door",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Zorgverlener",
                            content = listOf("Huisartsen, niet nader gespecificeerd"),
                          ),
                          MgoPdf.Row(
                            label = "Zorgaanbieder",
                            content = listOf("Kwalificatie Medmij: BGZ"),
                          ),
                        ),
                    ),
                  ),
              ),
              MgoPdf.Table(
                sections =
                  listOf(
                    MgoPdf.Section(
                      heading = "Metformine tablet 500mg",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Gebruiksaanwijzing",
                            content = listOf("2 maal per dag 1 tablet, oraal bij de maaltijd"),
                          ),
                          MgoPdf.Row(
                            label = "Hoeveelheid per keer",
                            content = listOf("1 stuk"),
                          ),
                          MgoPdf.Row(
                            label = "Status",
                            content = listOf("In gebruik"),
                          ),
                          MgoPdf.Row(
                            label = "Reden gebruik",
                            content = listOf("Type 2 diabetes"),
                          ),
                        ),
                    ),
                    MgoPdf.Section(
                      heading = "Periode van gebruik",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Ingangsdatum",
                            content = listOf("15 januari 2028"),
                          ),
                          MgoPdf.Row(
                            label = "Einddatum",
                            content = listOf("Niet bekend"),
                          ),
                        ),
                    ),
                    MgoPdf.Section(
                      heading = "Voorgeschreven door",
                      rows =
                        listOf(
                          MgoPdf.Row(
                            label = "Zorgverlener",
                            content = listOf("Huisartsen, niet nader gespecificeerd"),
                          ),
                          MgoPdf.Row(
                            label = "Zorgaanbieder",
                            content = listOf("Kwalificatie Medmij: BGZ"),
                          ),
                        ),
                    ),
                  ),
              ),
            ),
        ),
      ),
  )
