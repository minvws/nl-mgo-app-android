package nl.rijksoverheid.mgo.data.concern.models

import org.hl7.fhir.dstu3.model.CodeableConcept
import org.hl7.fhir.dstu3.model.Condition
import org.hl7.fhir.dstu3.model.Type
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

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
    val bodyLocationString =
        buildString {
            val firstPart = bodySite.firstOrNull()?.coding?.firstOrNull()?.display
            if (firstPart != null) {
                append(firstPart)
                append(", ")
            }
            val secondPart = bodySite.firstOrNull()?.extension?.firstOrNull()?.value?.asCodeableConcept()?.coding?.firstOrNull()?.display
            if (secondPart != null) {
                append(secondPart)
            }
        }
    return MgoConcern(
        title = Optional.ofNullable(code).getOrNull()?.coding?.firstOrNull()?.display,
        comment = Optional.ofNullable(note).getOrNull()?.joinToString(", ") { it.text },
        clinicalStatus = Optional.ofNullable(clinicalStatus).getOrNull()?.display?.lowercase(),
        category =
            category.map { categoryItem ->
                categoryItem.coding.map { codingItem -> codingItem.display }
            }.flatten().joinToString(", "),
        startDate = Optional.ofNullable(onsetDateTimeType).getOrNull()?.valueAsString,
        endDate = Optional.ofNullable(abatementDateTimeType).getOrNull()?.valueAsString,
        bodyLocation = bodyLocationString,
    )
}

private fun Type.asCodeableConcept(): CodeableConcept? {
    return (this as? CodeableConcept)
}
