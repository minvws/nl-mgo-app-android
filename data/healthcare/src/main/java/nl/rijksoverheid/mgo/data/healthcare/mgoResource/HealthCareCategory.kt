package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.models.Profiles
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
                Profiles.zibMedicationUse,
                Profiles.zibMedicationAgreement,
                Profiles.zibAdministrationAgreement,
            )
        }

        HealthCareCategory.LAB_RESULTS -> {
            listOf(
                Profiles.zibLaboratoryTestResultObservation,
                Profiles.gpLaboratoryResult,
            )
        }

        HealthCareCategory.DOCUMENTS -> {
            listOf(
                Profiles.iHEMHDMinimalDocumentReference,
            )
        }

        HealthCareCategory.VACCINATIONS -> {
            listOf(
                Profiles.zibVaccination,
                Profiles.zibVaccinationRecommendation,
                Profiles.nlCoreVaccinationEvent,
            )
        }

        else -> listOf()
    }
}
