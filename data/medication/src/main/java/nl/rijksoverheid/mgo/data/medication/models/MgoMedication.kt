package nl.rijksoverheid.mgo.data.medication.models

import org.hl7.fhir.dstu3.model.MedicationStatement
import org.hl7.fhir.dstu3.model.Reference
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

data class MgoMedication(
    val title: String?,
    val instructions: String?,
    val prescribedBy: String?,
    val startDate: String?,
    val status: String?,
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
    val extension = extension.find { it.url == "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse-Prescriber" }
    return MgoMedication(
        title = Optional.ofNullable(medicationReference.display).getOrNull(),
        instructions = Optional.ofNullable(dosage).getOrNull()?.joinToString(", ") { it.text },
        prescribedBy = (extension?.value as? Reference)?.display,
        status = Optional.ofNullable(status.display).getOrNull()?.lowercase(),
        startDate = Optional.ofNullable(effectivePeriod).getOrNull()?.startElement?.valueAsString,
    )
}
