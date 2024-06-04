package nl.rijksoverheid.mgo.data.medication.models

import org.hl7.fhir.dstu3.model.MedicationStatement

data class MgoMedication(
    val title: String,
    val instructions: String,
    val prescribedBy: String,
    val startDate: String,
    val status: String,
)

val TEST_MGO_MEDICATION =
    MgoMedication(
        title = "OMEPRAZOL CAPSTLE MSR 20MG",
        instructions = "1 x per dag 1 capsule een half uur voor het ontbijt heel doorslikken, niet kauwen",
        prescribedBy = "Dekker, A.",
        startDate = "9 maart 2023",
        status = "active",
    )

internal fun MedicationStatement.toMedication(): MgoMedication {
    val startDate = if (effectiveDateTimeType.value != null) effectiveDateTimeType.value else effectivePeriod.start
    return MgoMedication(
        title = medicationReference.display,
        instructions = dosage.map { it.text }.joinToString(" "),
        prescribedBy =
            extension.find { it.url == "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse-Prescriber" }?.value
                .toString(),
        status = status.display,
        startDate = startDate.toString(),
    )
}
