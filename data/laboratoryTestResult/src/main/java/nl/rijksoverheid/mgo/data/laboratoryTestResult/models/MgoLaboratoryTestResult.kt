package nl.rijksoverheid.mgo.data.laboratoryTestResult.models

import nl.rijksoverheid.mgo.framework.fhirextension.getCodeString
import nl.rijksoverheid.mgo.framework.fhirextension.getCollectionDateTimeString
import nl.rijksoverheid.mgo.framework.fhirextension.getDateTimeString
import nl.rijksoverheid.mgo.framework.fhirextension.getInterpretationString
import nl.rijksoverheid.mgo.framework.fhirextension.getReferenceRangeHighString
import nl.rijksoverheid.mgo.framework.fhirextension.getReferenceRangeLowString
import nl.rijksoverheid.mgo.framework.fhirextension.getResultString
import nl.rijksoverheid.mgo.framework.fhirextension.getSpecimenString
import nl.rijksoverheid.mgo.framework.fhirextension.getStatusString
import nl.rijksoverheid.mgo.framework.fhirextension.getTitleString
import org.hl7.fhir.dstu3.model.Observation

data class MgoLaboratoryTestResult(
    val title: String?,
    val code: String?,
    val status: String?,
    val dateTime: String?,
    val result: String?,
    val referenceRangeLow: String?,
    val referenceRangeHigh: String?,
    val interpretation: String?,
    val specimen: String?,
    val collectionDateTime: String?,
)

internal fun Observation.toMgoLaboratoryTestResult(): MgoLaboratoryTestResult {
    return MgoLaboratoryTestResult(
        title = getTitleString(),
        code = getCodeString(),
        status = getStatusString(),
        dateTime = getDateTimeString(),
        result = getResultString(),
        referenceRangeLow = getReferenceRangeLowString(),
        referenceRangeHigh = getReferenceRangeHighString(),
        interpretation = getInterpretationString(),
        specimen = getSpecimenString(),
        collectionDateTime = getCollectionDateTimeString(),
    )
}
