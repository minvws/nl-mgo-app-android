package nl.rijksoverheid.mgo.data.medication.models

import nl.rijksoverheid.mgo.framework.fhirextension.getInstructionsString
import nl.rijksoverheid.mgo.framework.fhirextension.getLowercaseStatusString
import nl.rijksoverheid.mgo.framework.fhirextension.getPrescribedByString
import nl.rijksoverheid.mgo.framework.fhirextension.getStartDateString
import nl.rijksoverheid.mgo.framework.fhirextension.getTitleString
import org.hl7.fhir.dstu3.model.MedicationStatement

data class MgoMedication(
    val title: String?,
    val instructions: String?,
    val prescribedBy: String?,
    val startDate: String?,
    val status: String?,
)

internal fun MedicationStatement.toMedication(): MgoMedication {
    return MgoMedication(
        title = getTitleString(),
        instructions = getInstructionsString(),
        prescribedBy = getPrescribedByString(),
        status = getLowercaseStatusString(),
        startDate = getStartDateString(),
    )
}
