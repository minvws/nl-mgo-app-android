package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.fhirParser.models.Profiles
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
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
    val medicationsRequests = HealthCareCategory.MEDICATIONS.getRequests()
    assertEquals(
      listOf(Bgz.MedicationUse, Bgz.MedicationAgreement, Bgz.AdministrationAgreement, Gp.CurrentMedication),
      medicationsRequests,
    )

    val measurementsRequests = HealthCareCategory.MEASUREMENTS.getRequests()
    assertEquals(
      listOf(Bgz.BloodPressure, Bgz.BodyWeight, Bgz.BodyHeight, Gp.DiagnosticsAndLabResult),
      measurementsRequests,
    )

    val labResultsRequests = HealthCareCategory.LAB_RESULTS.getRequests()
    assertEquals(
      listOf(Bgz.LaboratoryTestResult, Gp.DiagnosticsAndLabResult),
      labResultsRequests,
    )

    val allergiesRequests = HealthCareCategory.ALLERGIES.getRequests()
    assertEquals(
      listOf(Bgz.AllergyIntolerance, Gp.AllergyIntolerance),
      allergiesRequests,
    )

    val treatmentsRequests = HealthCareCategory.TREATMENTS.getRequests()
    assertEquals(
      listOf(Bgz.Procedure, Bgz.PlannedProcedure, Gp.Episodes),
      treatmentsRequests,
    )

    val appointmentRequests = HealthCareCategory.APPOINTMENTS.getRequests()
    assertEquals(
      listOf(Bgz.Encounter, Bgz.PlannedEncounters, Gp.Encounter, Gp.SoapEntries),
      appointmentRequests,
    )

    val vaccinationsRequests = HealthCareCategory.VACCINATIONS.getRequests()
    assertEquals(
      listOf(Bgz.Vaccination, Bgz.PlannedImmunization, Vaccination.Patient),
      vaccinationsRequests,
    )

    val documentsRequests = HealthCareCategory.DOCUMENTS.getRequests()
    assertEquals(
      listOf(Documents.DocumentReference),
      documentsRequests,
    )

    val complaintsRequests = HealthCareCategory.COMPLAINTS.getRequests()
    assertEquals(
      listOf(Bgz.Problem),
      complaintsRequests,
    )

    val patientRequests = HealthCareCategory.PATIENT.getRequests()
    assertEquals(
      listOf(Bgz.Patient, Gp.Patient),
      patientRequests,
    )

    val mentalRequests = HealthCareCategory.MENTAL.getRequests()
    assertEquals(
      listOf(Bgz.FunctionalOrMentalStatus),
      mentalRequests,
    )

    val alertsRequests = HealthCareCategory.ALERTS.getRequests()
    assertEquals(
      listOf(Bgz.Alert, Gp.Episodes),
      alertsRequests,
    )

    val lifestyleRequests = HealthCareCategory.LIFESTYLE.getRequests()
    assertEquals(
      listOf(Bgz.LivingSituation, Bgz.DrugUse, Bgz.AlcoholUse, Bgz.TabacoUse, Bgz.NutritionAdvice),
      lifestyleRequests,
    )

    val devicesRequests = HealthCareCategory.DEVICES.getRequests()
    assertEquals(
      listOf(Bgz.MedicalDevice, Bgz.PlannedMedicalDevices),
      devicesRequests,
    )

    val plansRequests = HealthCareCategory.PLANS.getRequests()
    assertEquals(
      listOf(Bgz.TreatmentDirective, Bgz.AdvanceDirective),
      plansRequests,
    )

    val paymentRequests = HealthCareCategory.PAYMENT.getRequests()
    assertEquals(
      listOf(Bgz.Payer),
      paymentRequests,
    )
  }

  @Test
  fun testGetProfiles() {
    val medicationProfiles = HealthCareCategory.MEDICATIONS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibMedicationUse,
        Profiles.zibMedicationAgreement,
        Profiles.zibAdministrationAgreement,
      ),
      medicationProfiles,
    )

    val measurementsProfiles = HealthCareCategory.MEASUREMENTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibBloodPressure,
        Profiles.zibBodyWeight,
        Profiles.zibBodyHeight,
        Profiles.gpDiagnosticResult,
      ),
      measurementsProfiles,
    )

    val labResultsProfiles = HealthCareCategory.LAB_RESULTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibLaboratoryTestResultObservation,
        Profiles.zibLaboratoryTestResultSpecimen,
        Profiles.gpLaboratoryResult,
      ),
      labResultsProfiles,
    )

    val allergiesProfiles = HealthCareCategory.ALLERGIES.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibAllergyIntolerance,
      ),
      allergiesProfiles,
    )

    val treatmentsProfiles = HealthCareCategory.TREATMENTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibProcedure,
        Profiles.zibProcedureRequest,
        Profiles.nlCoreEpisodeofcare,
      ),
      treatmentsProfiles,
    )

    val appointmentsProfiles = HealthCareCategory.APPOINTMENTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.eAfspraakAppointment,
        Profiles.zibEncounter,
        Profiles.gpEncounter,
        Profiles.gpEncounterReport,
      ),
      appointmentsProfiles,
    )

    val vaccinationProfiles = HealthCareCategory.VACCINATIONS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibVaccination,
        Profiles.nlCoreVaccinationEvent,
        Profiles.zibVaccinationRecommendation,
      ),
      vaccinationProfiles,
    )

    val documentsProfiles = HealthCareCategory.DOCUMENTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.iHEMHDMinimalDocumentReference,
      ),
      documentsProfiles,
    )

    val complaintsProfiles = HealthCareCategory.COMPLAINTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibProblem,
      ),
      complaintsProfiles,
    )

    val patientProfiles = HealthCareCategory.PATIENT.getProfiles()
    assertEquals(
      listOf(
        Profiles.nlCorePatient,
      ),
      patientProfiles,
    )

    val paymentProfiles = HealthCareCategory.PAYMENT.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibPayer,
      ),
      paymentProfiles,
    )

    val plansProfiles = HealthCareCategory.PLANS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibTreatmentDirective,
        Profiles.zibAdvanceDirective,
      ),
      plansProfiles,
    )

    val devicesProfiles = HealthCareCategory.DEVICES.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibMedicalDevice,
        Profiles.zibMedicalDeviceRequest,
      ),
      devicesProfiles,
    )

    val mentalProfiles = HealthCareCategory.MENTAL.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibFunctionalOrMentalStatus,
      ),
      mentalProfiles,
    )

    val lifestyleProfiles = HealthCareCategory.LIFESTYLE.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibLivingSituation,
        Profiles.zibDrugUse,
        Profiles.zibAlcoholUse,
        Profiles.zibTobaccoUse,
        Profiles.zibNutritionAdvice,
      ),
      lifestyleProfiles,
    )

    val alertsProfiles = HealthCareCategory.ALERTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibAlert,
      ),
      alertsProfiles,
    )
  }
}
