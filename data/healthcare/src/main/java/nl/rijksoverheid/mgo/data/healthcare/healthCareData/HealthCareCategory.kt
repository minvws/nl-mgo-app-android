package nl.rijksoverheid.mgo.data.healthcare.healthCareData

import nl.rijksoverheid.mgo.data.fhirParser.shared.GpLaboratoryResultProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.IheMhdMinimalDocumentReferenceProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.R4NlCoreVaccinationEventProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibAdministrationAgreementProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibLaboratoryTestResultObservationProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibMedicationAgreementProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibMedicationUseProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibVaccinationProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibVaccinationRecommendationProfile
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest.Bgz
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest.Documents
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest.Gp
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest.Vaccination

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
                ZibMedicationUseProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationUse.value,
                ZibMedicationAgreementProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationAgreement.value,
                ZibAdministrationAgreementProfile.HTTPNictizNlFhirStructureDefinitionZibAdministrationAgreement.value,
            )
        }

        HealthCareCategory.LAB_RESULTS -> {
            listOf(
                ZibLaboratoryTestResultObservationProfile.HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultObservation.value,
                GpLaboratoryResultProfile.HTTPNictizNlFhirStructureDefinitionGpLaboratoryResult.value,
            )
        }

        HealthCareCategory.DOCUMENTS -> {
            listOf(
                IheMhdMinimalDocumentReferenceProfile.HTTPNictizNlFhirStructureDefinitionIHEMHDMinimalDocumentReference.value,
            )
        }

        HealthCareCategory.VACCINATIONS -> {
            listOf(
                ZibVaccinationProfile.HTTPNictizNlFhirStructureDefinitionZibVaccination.value,
                ZibVaccinationRecommendationProfile.HTTPNictizNlFhirStructureDefinitionZibVaccinationRecommendation.value,
                R4NlCoreVaccinationEventProfile.HTTPNictizNlFhirStructureDefinitionNlCoreVaccinationEvent.value,
            )
        }

        else -> listOf()
    }
}
