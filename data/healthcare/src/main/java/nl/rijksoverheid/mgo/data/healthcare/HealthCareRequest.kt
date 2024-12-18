package nl.rijksoverheid.mgo.data.healthcare

import android.net.Uri
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType

data class HealthCareRequest(
    val path: String,
    val queryParameters: List<HealthCareRequestQuery>,
    val dataServiceType: MgoOrganizationDataServiceType,
)

fun HealthCareRequest.createUrl(baseUrl: String): String {
    val builder = Uri.parse(baseUrl).buildUpon()
    for ((key, value) in queryParameters) {
        builder.appendQueryParameter(key.value, value)
    }
    return builder.build().toString()
}

data class HealthCareRequestQuery(
    val key: HealthCareRequestQueryKey,
    val value: String,
)

enum class HealthCareRequestQueryKey(val value: String) {
    CATEGORY("category"),
    INCLUDE("_include"),
    CODE("code"),
    DATE("date"),
    STATUS("status"),
}

// ================
// BGZ
// https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
// ================

enum class BGZ(val request: HealthCareRequest) {
    MEDICATION_USE(
        request =
            HealthCareRequest(
                path = "MedicationStatement",
                queryParameters =
                    listOf(
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.CATEGORY,
                            value = "urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6",
                        ),
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.INCLUDE,
                            value = "MedicationStatement:medication",
                        ),
                    ),
                dataServiceType = MgoOrganizationDataServiceType.BGZ,
            ),
    ),
    LABORATORY_TEST_RESULTS(
        request =
            HealthCareRequest(
                path = "Observation",
                queryParameters =
                    listOf(
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.CATEGORY,
                            value = "http://snomed.info/sct|275711006",
                        ),
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.INCLUDE,
                            value = "Observation:related-target",
                        ),
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.INCLUDE,
                            value = "Observation:specimen",
                        ),
                    ),
                dataServiceType = MgoOrganizationDataServiceType.BGZ,
            ),
    ),
}

// ================
// GP
// See https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_GP_Data
// ================

enum class GP(val request: HealthCareRequest) {
    DIAGNOSTIC_AND_LAB_RESULTS(
        request =
            HealthCareRequest(
                path = "Observation",
                queryParameters =
                    listOf(
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.CODE,
                            value = "https://referentiemodel.nhg.org/tabellen/nhg-tabel-45-diagnostische-bepalingen|",
                        ),
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.INCLUDE,
                            value = "Observation:related-target",
                        ),
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.INCLUDE,
                            value = "Observation:specimen",
                        ),
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.DATE,
                            value = "ge2017-01-01",
                        ),
                    ),
                dataServiceType = MgoOrganizationDataServiceType.GP,
            ),
    ),
}

// ================
// DOCUMENTS
// See https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/OntwerpPDFA
// ================

enum class DOCUMENTS(val request: HealthCareRequest) {
    DOCUMENT_REFERENCE(
        request =
            HealthCareRequest(
                path = "DocumentReference",
                queryParameters =
                    listOf(
                        HealthCareRequestQuery(
                            key = HealthCareRequestQueryKey.STATUS,
                            value = "current",
                        ),
                    ),
                dataServiceType = MgoOrganizationDataServiceType.DOCUMENTS,
            ),
    ),
}
