package nl.rijksoverheid.mgo.framework.fhirextension

import org.hl7.fhir.dstu3.model.MedicationStatement
import org.hl7.fhir.dstu3.model.Reference
import timber.log.Timber

fun MedicationStatement.getTitleString(): String? {
    return try {
        medicationReference.display
    } catch (e: Exception) {
        Timber.e(e, "MedicationStatement.getTitle() failed")
        return null
    }
}

fun MedicationStatement.getInstructionsString(): String? {
    if (dosage.isEmpty()) return null
    return dosage.joinToString(", ") { it.text }
}

fun MedicationStatement.getPrescribedByString(): String? {
    try {
        val extension = checkNotNull(extension.find { it.url == "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse-Prescriber" })
        val reference = extension.value as Reference
        return reference.display
    } catch (e: Exception) {
        Timber.e(e, "MedicationStatement.getPrescribedBy() failed")
        return null
    }
}

fun MedicationStatement.getLowercaseStatusString(): String? {
    return try {
        status.display.lowercase()
    } catch (e: Exception) {
        null
    }
}

fun MedicationStatement.getStartDateString(): String? {
    return try {
        effectivePeriod.startElement.valueAsString
    } catch (e: Exception) {
        return null
    }
}
