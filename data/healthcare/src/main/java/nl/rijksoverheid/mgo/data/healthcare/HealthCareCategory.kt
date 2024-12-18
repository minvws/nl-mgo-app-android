package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.uiSchema.GpLaboratoryResultProfile
import nl.rijksoverheid.mgo.data.uiSchema.IheMhdMinimalDocumentReferenceProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibAdministrationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibLaboratoryTestResultObservationProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationUseProfile

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
            listOf(BGZ.MEDICATION_USE.request)
        }

        HealthCareCategory.LAB_RESULTS -> {
            listOf(BGZ.LABORATORY_TEST_RESULTS.request, GP.DIAGNOSTIC_AND_LAB_RESULTS.request)
        }

        HealthCareCategory.DOCUMENTS -> {
            listOf(DOCUMENTS.DOCUMENT_REFERENCE.request)
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

        else -> listOf()
    }
}
