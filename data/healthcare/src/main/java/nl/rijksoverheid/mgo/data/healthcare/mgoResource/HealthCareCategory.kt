package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Bgz
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Documents
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Gp
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Vaccination

enum class HealthCareCategory(val id: String) {
    MEDICATIONS("medication"),
    LAB_RESULTS("lab_results"),
    DOCUMENTS("documents"),
    VACCINATIONS("vaccinations"),
    MEASUREMENTS("measurements"),
    ALLERGIES("allergies"),
    TREATMENTS("treatments"),
    APPOINTMENTS("appointments"),
    COMPLAINTS("complaints"),
    PATIENT("patient"),
    ALERTS("alerts"),
    PAYMENT("payment"),
    PLANS("plans"),
    DEVICES("devices"),
    MENTAL("mental"),
    LIFESTYLE("lifestyle"),
}

fun HealthCareCategory.getRequests(): List<HealthCareRequest> {
    return when (this) {
        HealthCareCategory.MEDICATIONS -> {
            listOf(Bgz.MedicationUse)
        }

        HealthCareCategory.LAB_RESULTS -> {
            listOf(Bgz.LaboratoryTestResult, Gp.DiagnosticsAndLabResult)
        }

        HealthCareCategory.DOCUMENTS -> {
            listOf(Documents.DocumentReference)
        }

        HealthCareCategory.VACCINATIONS -> {
            listOf(Vaccination.Patient)
        }

        else -> listOf()
    }
}

fun HealthCareCategory.getProfiles(): List<String> {
    return when (this) {
        HealthCareCategory.MEDICATIONS -> {
            listOf(
                "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse",
                "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationAgreement",
                "http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement",
            )
        }

        HealthCareCategory.LAB_RESULTS -> {
            listOf(
                "http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Observation",
                "http://nictiz.nl/fhir/StructureDefinition/gp-LaboratoryResult",
            )
        }

        HealthCareCategory.DOCUMENTS -> {
            listOf(
                "http://nictiz.nl/fhir/StructureDefinition/IHE.MHD.Minimal.DocumentReference",
            )
        }

        HealthCareCategory.VACCINATIONS -> {
            listOf(
                "http://nictiz.nl/fhir/StructureDefinition/zib-Vaccination",
                "http://nictiz.nl/fhir/StructureDefinition/zib-VaccinationRecommendation",
                "http://nictiz.nl/fhir/StructureDefinition/nl-core-Vaccination-event",
            )
        }

        else -> listOf()
    }
}
