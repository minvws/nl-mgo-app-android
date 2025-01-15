package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import android.net.Uri
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.FhirVersion
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.CATEGORY
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.CODE
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.DATE
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.INCLUDE
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.STATUS
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType

// ================
// BGZ
// https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
// ================

sealed class HealthCareRequest(
    open val path: String,
    open val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>,
    val dataServiceType: MgoOrganizationDataServiceType,
    val fhirVersion: FhirVersion,
) {
    // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
    sealed class Bgz(override val path: String, override val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>) :
        HealthCareRequest(path, queryParameters, MgoOrganizationDataServiceType.BGZ, FhirVersion.R3) {
        data object MedicationUse : Bgz(
            path = "MedicationStatement",
            queryParameters =
                listOf(
                    Pair(CATEGORY, "urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6"),
                    Pair(INCLUDE, "MedicationStatement:medication"),
                ),
        )

        data object LaboratoryTestResult : Bgz(
            path = "Observation/\$lastn",
            queryParameters =
                listOf(
                    Pair(CATEGORY, "http://snomed.info/sct|275711006"),
                    Pair(INCLUDE, "Observation:related-target"),
                    Pair(INCLUDE, "Observation:specimen"),
                ),
        )
    }

    // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_GP_Data
    sealed class Gp(override val path: String, override val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>) :
        HealthCareRequest
        (path, queryParameters, MgoOrganizationDataServiceType.GP, FhirVersion.R3) {
        data object DiagnosticsAndLabResult : Gp(
            path = "Observation",
            queryParameters =
                listOf(
                    Pair(CODE, "https://referentiemodel.nhg.org/tabellen/nhg-tabel-45-diagnostische-bepalingen|"),
                    Pair(INCLUDE, "Observation:related-target"),
                    Pair(INCLUDE, "Observation:specimen"),
                    Pair(DATE, "ge2017-01-01"),
                ),
        )
    }

    // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/OntwerpPDFA
    sealed class Documents(override val path: String, override val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>) :
        HealthCareRequest
        (path, queryParameters, MgoOrganizationDataServiceType.DOCUMENTS, FhirVersion.R3) {
        data object DocumentReference : Documents(
            path = "DocumentReference",
            queryParameters =
                listOf(
                    Pair(STATUS, "current"),
                ),
        )
    }

    // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V6/FHIR_Vaccination-Immunization
    sealed class Vaccination(override val path: String, override val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>) :
        HealthCareRequest
        (path, queryParameters, MgoOrganizationDataServiceType.VACCINATION, FhirVersion.R4) {
        data object Patient : Vaccination(
            path = "Immunization",
            queryParameters =
                listOf(
                    Pair(INCLUDE, "patient"),
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
