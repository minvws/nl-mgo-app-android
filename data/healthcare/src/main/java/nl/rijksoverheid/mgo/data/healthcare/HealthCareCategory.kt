package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.healthcare.HealthCareRequest.Bgz
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRequest.Documents
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRequest.Gp
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRequest.Vaccination
import nl.rijksoverheid.mgo.data.uiSchema.GpLaboratoryResultProfile
import nl.rijksoverheid.mgo.data.uiSchema.IheMhdMinimalDocumentReferenceProfile
import nl.rijksoverheid.mgo.data.uiSchema.R4NlCoreVaccinationEventProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibAdministrationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibLaboratoryTestResultObservationProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationUseProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibVaccinationProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibVaccinationRecommendationProfile

enum class HealthCareCategory(val id: String) {
    MEDICATIONS("medication"),
    MEASUREMENTS("measurements"),
    LAB_RESULTS("lab_results"),
    ALLERGIES("allergies"),
    TREATMENTS("treatments"),
    APPOINTMENTS("appointments"),
    VACCINATIONS("vaccinations"),
    DOCUMENTS("documents"),
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
