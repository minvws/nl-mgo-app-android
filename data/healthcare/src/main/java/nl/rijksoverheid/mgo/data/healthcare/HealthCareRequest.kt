package nl.rijksoverheid.mgo.data.healthcare

import android.net.Uri
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRequestQueryKey.CATEGORY
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRequestQueryKey.CODE
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRequestQueryKey.DATE
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRequestQueryKey.INCLUDE
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRequestQueryKey.STATUS
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType

// ================
// BGZ
// https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
// ================

sealed class HealthCareRequest(
    open val path: String,
    open val queryParameters: Map<HealthCareRequestQueryKey, String>,
    val dataServiceType: MgoOrganizationDataServiceType,
) {
    // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
    sealed class Bgz(override val path: String, override val queryParameters: Map<HealthCareRequestQueryKey, String>) : HealthCareRequest
        (path, queryParameters, MgoOrganizationDataServiceType.BGZ) {
        data object MedicationUse : Bgz(
            path = "MedicationStatement",
            queryParameters =
                mapOf(
                    CATEGORY to "urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6",
                    INCLUDE to "MedicationStatement:medication",
                ),
        )

        data object LaboratoryTestResult : Bgz(
            path = "Observation",
            queryParameters =
                mapOf(
                    CATEGORY to "urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6",
                    INCLUDE to "Observation:related-target",
                    INCLUDE to "Observation:specimen",
                ),
        )
    }

    // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_GP_Data
    sealed class Gp(override val path: String, override val queryParameters: Map<HealthCareRequestQueryKey, String>) : HealthCareRequest
        (path, queryParameters, MgoOrganizationDataServiceType.GP) {
        data object DiagnosticsAndLabResult : Gp(
            path = "Observation",
            queryParameters =
                mapOf(
                    CODE to "https://referentiemodel.nhg.org/tabellen/nhg-tabel-45-diagnostische-bepalingen|",
                    INCLUDE to "Observation:related-target",
                    INCLUDE to "Observation:specimen",
                    DATE to "ge2017-01-01",
                ),
        )
    }

    // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/OntwerpPDFA
    sealed class Documents(override val path: String, override val queryParameters: Map<HealthCareRequestQueryKey, String>) :
        HealthCareRequest
        (path, queryParameters, MgoOrganizationDataServiceType.DOCUMENTS) {
        data object DocumentReference : Documents(
            path = "DocumentReference",
            queryParameters =
                mapOf(
                    STATUS to "current",
                ),
        )
    }
}

enum class HealthCareRequestQueryKey(val value: String) {
    CATEGORY("category"),
    INCLUDE("_include"),
    CODE("code"),
    DATE("date"),
    STATUS("status"),
}

fun HealthCareRequest.createUrl(baseUrl: String): String {
    val builder = Uri.parse(baseUrl).buildUpon()
    for ((key, value) in queryParameters) {
        builder.appendQueryParameter(key.value, value)
    }
    return builder.build().toString()
}
