package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.fhirParser.shared.GpLaboratoryResultProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.IheMhdMinimalDocumentReferenceProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.R4NlCoreVaccinationEventProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibAdministrationAgreementProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibLaboratoryTestResultObservationProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibMedicationAgreementProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibMedicationUseProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibVaccinationProfile
import nl.rijksoverheid.mgo.data.fhirParser.shared.ZibVaccinationRecommendationProfile
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest.Bgz
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest.Documents
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest.Gp
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest.Vaccination
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.getProfiles
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.getRequests
import org.junit.Assert.assertEquals
import org.junit.Test

class HealthCareCategoryTest {
    @Test
    fun testGetRequests() {
        // Medications
        val medicationsRequests = HealthCareCategory.MEDICATIONS.getRequests()
        assertEquals(
            listOf(Bgz.MedicationUse),
            medicationsRequests,
        )

        // Lab results
        val labResultsRequests = HealthCareCategory.LAB_RESULTS.getRequests()
        assertEquals(
            listOf(Bgz.LaboratoryTestResult, Gp.DiagnosticsAndLabResult),
            labResultsRequests,
        )

        // Documents
        val documentsRequests = HealthCareCategory.DOCUMENTS.getRequests()
        assertEquals(
            listOf(Documents.DocumentReference),
            documentsRequests,
        )

        // Documents
        val vaccinationRequests = HealthCareCategory.VACCINATIONS.getRequests()
        assertEquals(
            listOf(Vaccination.Patient),
            vaccinationRequests,
        )

        val otherRequests = HealthCareCategory.ALERTS.getRequests()
        assertEquals(
            listOf<HealthCareRequest>(),
            otherRequests,
        )
    }

    @Test
    fun testGetProfiles() {
        // Medications
        val medicationProfiles = HealthCareCategory.MEDICATIONS.getProfiles()
        assertEquals(
            listOf(
                ZibMedicationUseProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationUse.value,
                ZibMedicationAgreementProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationAgreement.value,
                ZibAdministrationAgreementProfile.HTTPNictizNlFhirStructureDefinitionZibAdministrationAgreement.value,
            ),
            medicationProfiles,
        )

        // Lab results
        val labResultsProfiles = HealthCareCategory.LAB_RESULTS.getProfiles()
        assertEquals(
            listOf(
                ZibLaboratoryTestResultObservationProfile.HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultObservation.value,
                GpLaboratoryResultProfile.HTTPNictizNlFhirStructureDefinitionGpLaboratoryResult.value,
            ),
            labResultsProfiles,
        )

        // Documents
        val documentsProfiles = HealthCareCategory.DOCUMENTS.getProfiles()
        assertEquals(
            listOf(
                IheMhdMinimalDocumentReferenceProfile.HTTPNictizNlFhirStructureDefinitionIHEMHDMinimalDocumentReference.value,
            ),
            documentsProfiles,
        )

        // Documents
        val vaccinationsProfiles = HealthCareCategory.VACCINATIONS.getProfiles()
        assertEquals(
            listOf(
                ZibVaccinationProfile.HTTPNictizNlFhirStructureDefinitionZibVaccination.value,
                ZibVaccinationRecommendationProfile.HTTPNictizNlFhirStructureDefinitionZibVaccinationRecommendation.value,
                R4NlCoreVaccinationEventProfile.HTTPNictizNlFhirStructureDefinitionNlCoreVaccinationEvent.value,
            ),
            vaccinationsProfiles,
        )

        val otherProfiles = HealthCareCategory.ALERTS.getProfiles()
        assertEquals(
            listOf<String>(),
            otherProfiles,
        )
    }
}
