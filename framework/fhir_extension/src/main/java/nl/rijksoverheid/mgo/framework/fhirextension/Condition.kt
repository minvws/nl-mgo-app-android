package nl.rijksoverheid.mgo.framework.fhirextension

import org.hl7.fhir.dstu3.model.CodeableConcept
import org.hl7.fhir.dstu3.model.Condition
import org.hl7.fhir.dstu3.model.Type

fun Condition.getTitleString(): String? {
    return try {
        code.coding.first().display
    } catch (e: Exception) {
        null
    }
}

fun Condition.getCommentString(): String? {
    if (note.isEmpty()) return null
    return note.joinToString(", ") { it.text }
}

fun Condition.getClinicalStatusString(): String? {
    return try {
        clinicalStatus.display.lowercase()
    } catch (e: Exception) {
        null
    }
}

fun Condition.getCategoryString(): String? {
    if (category.isEmpty()) return null
    return category.map { categoryItem ->
        categoryItem.coding.map { codingItem -> codingItem.display }
    }.flatten().joinToString(", ")
}

fun Condition.getStartDateString(): String? {
    return try {
        onsetDateTimeType.valueAsString
    } catch (e: Exception) {
        null
    }
}

fun Condition.getEndDateString(): String? {
    return try {
        abatementDateTimeType.valueAsString
    } catch (e: Exception) {
        return null
    }
}

fun Condition.getBodyLocationString(): String? {
    if (bodySite.isEmpty()) return null
    val string =
        buildList {
            try {
                add(bodySite.first().coding.first().display)
            } catch (e: Exception) {
                // Do not add first part
            }
            try {
                add(bodySite.first().extension.first().value.asCodeableConcept().coding.first().display)
            } catch (e: Exception) {
                // Do not add second part
            }
        }.joinToString(", ")
    return string.ifEmpty { null }
}

private fun Type.asCodeableConcept(): CodeableConcept {
    return this as CodeableConcept
}
