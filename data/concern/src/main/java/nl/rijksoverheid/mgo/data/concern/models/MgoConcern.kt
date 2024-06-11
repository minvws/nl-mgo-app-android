package nl.rijksoverheid.mgo.data.concern.models

import nl.rijksoverheid.mgo.framework.fhirextension.getBodyLocationString
import nl.rijksoverheid.mgo.framework.fhirextension.getCategoryString
import nl.rijksoverheid.mgo.framework.fhirextension.getClinicalStatusString
import nl.rijksoverheid.mgo.framework.fhirextension.getCommentString
import nl.rijksoverheid.mgo.framework.fhirextension.getEndDateString
import nl.rijksoverheid.mgo.framework.fhirextension.getStartDateString
import nl.rijksoverheid.mgo.framework.fhirextension.getTitleString
import org.hl7.fhir.dstu3.model.Condition

data class MgoConcern(
    val title: String?,
    val comment: String?,
    val clinicalStatus: String?,
    val category: String?,
    val startDate: String?,
    val endDate: String?,
    val bodyLocation: String?,
)

val TEST_MGO_CONCERN =
    MgoConcern(
        title = "Fractuur van pols (aandoening)",
        comment = "Gevallen op kunstijsbaan.",
        clinicalStatus = "inactive",
        category = "interpretatie van diagnose (waarneembare entiteit)",
        startDate = "2001",
        endDate = "",
        bodyLocation = "Gehele polsregio (lichaamsstructuur), Rechts",
    )

internal fun Condition.toConcern(): MgoConcern {
    return MgoConcern(
        title = getTitleString(),
        comment = getCommentString(),
        clinicalStatus = getClinicalStatusString(),
        category = getCategoryString(),
        startDate = getStartDateString(),
        endDate = getEndDateString(),
        bodyLocation = getBodyLocationString(),
    )
}
