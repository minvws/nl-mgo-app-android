package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.fhirParser.models.Profiles
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Bgz
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Documents
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Gp
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Vaccination
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.getProfiles
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.getRequests
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
                Profiles.zibMedicationUse,
                Profiles.zibMedicationAgreement,
                Profiles.zibAdministrationAgreement,
            ),
            medicationProfiles,
        )

        // Lab results
        val labResultsProfiles = HealthCareCategory.LAB_RESULTS.getProfiles()
        assertEquals(
            listOf(
                Profiles.zibLaboratoryTestResultObservation,
                Profiles.gpLaboratoryResult,
            ),
            labResultsProfiles,
        )

        // Documents
        val documentsProfiles = HealthCareCategory.DOCUMENTS.getProfiles()
        assertEquals(
            listOf(
                Profiles.iHEMHDMinimalDocumentReference,
            ),
            documentsProfiles,
        )

        // Documents
        val vaccinationsProfiles = HealthCareCategory.VACCINATIONS.getProfiles()
        assertEquals(
            listOf(
                Profiles.zibVaccination,
                Profiles.zibVaccinationRecommendation,
                Profiles.nlCoreVaccinationEvent,
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
