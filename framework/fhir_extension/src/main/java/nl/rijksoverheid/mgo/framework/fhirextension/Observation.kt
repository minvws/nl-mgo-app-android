package nl.rijksoverheid.mgo.framework.fhirextension

import org.hl7.fhir.dstu3.model.Observation
import org.hl7.fhir.dstu3.model.Specimen

fun Observation.getTitleString(): String? {
    return try {
        category.first().coding.first().display
    } catch (e: Exception) {
        null
    }
}

fun Observation.getCodeString(): String? {
    return try {
        code.coding.first().display
    } catch (e: Exception) {
        null
    }
}

fun Observation.getStatusString(): String? {
    return try {
        return status.display.lowercase()
    } catch (e: Exception) {
        null
    }
}

fun Observation.getDateTimeString(): String? {
    return try {
        return effectiveDateTimeType.valueAsString
    } catch (e: Exception) {
        null
    }
}

fun Observation.getResultString(): String? {
    return try {
        return "${valueQuantity.value} ${valueQuantity.unit}"
    } catch (e: Exception) {
        null
    }
}

fun Observation.getReferenceRangeLowString(): String? {
    return try {
        val low = referenceRange.map { it.low }.first()
        return "${low.value} ${low.unit}"
    } catch (e: Exception) {
        null
    }
}

fun Observation.getReferenceRangeHighString(): String? {
    return try {
        val low = referenceRange.map { it.high }.first()
        return "${low.value} ${low.unit}"
    } catch (e: Exception) {
        null
    }
}

fun Observation.getSpecimenString(): String? {
    return try {
        (specimen.resource as Specimen).type.coding.first().display
    } catch (e: Exception) {
        null
    }
}

fun Observation.getInterpretationString(): String? {
    return try {
        interpretation.coding.first { it.system == "http://snomed.info/sct" }.display
    } catch (e: Exception) {
        null
    }
}


fun Observation.getCollectionDateTimeString(): String? {
    return try {
        (specimen.resource as Specimen).collection.collectedDateTimeType.valueAsString
    } catch (e: Exception) {
        null
    }
}
