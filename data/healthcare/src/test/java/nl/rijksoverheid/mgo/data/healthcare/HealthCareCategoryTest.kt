package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.fhirParser.models.Profiles
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Bgz
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Documents
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Gp
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Vaccination
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.getProfiles
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.getRequests
import org.junit.Assert.assertEquals
import org.junit.Test

class HealthCareCategoryTest {
  @Test
  fun testGetRequests() {
    val medicationsRequests = HealthCareCategoryId.MEDICATIONS.getRequests()
    assertEquals(
      listOf(Bgz.MedicationUse, Bgz.MedicationAgreement, Bgz.AdministrationAgreement, Gp.CurrentMedication),
      medicationsRequests,
    )

    val measurementsRequests = HealthCareCategoryId.MEASUREMENTS.getRequests()
    assertEquals(
      listOf(Bgz.BloodPressure, Bgz.BodyWeight, Bgz.BodyHeight, Gp.DiagnosticsAndLabResult),
      measurementsRequests,
    )

    val labResultsRequests = HealthCareCategoryId.LAB_RESULTS.getRequests()
    assertEquals(
      listOf(Bgz.LaboratoryTestResult, Gp.DiagnosticsAndLabResult),
      labResultsRequests,
    )

    val allergiesRequests = HealthCareCategoryId.ALLERGIES.getRequests()
    assertEquals(
      listOf(Bgz.AllergyIntolerance, Gp.AllergyIntolerance),
      allergiesRequests,
    )

    val treatmentsRequests = HealthCareCategoryId.TREATMENTS.getRequests()
    assertEquals(
      listOf(Bgz.Procedure, Bgz.PlannedProcedure, Gp.Episodes),
      treatmentsRequests,
    )

    val appointmentRequests = HealthCareCategoryId.APPOINTMENTS.getRequests()
    assertEquals(
      listOf(Bgz.Encounter, Bgz.PlannedEncounters, Gp.Encounter, Gp.SoapEntries),
      appointmentRequests,
    )

    val vaccinationsRequests = HealthCareCategoryId.VACCINATIONS.getRequests()
    assertEquals(
      listOf(Bgz.Vaccination, Bgz.PlannedImmunization, Vaccination.Patient),
      vaccinationsRequests,
    )

    val documentsRequests = HealthCareCategoryId.DOCUMENTS.getRequests()
    assertEquals(
      listOf(Documents.DocumentReference),
      documentsRequests,
    )

    val complaintsRequests = HealthCareCategoryId.COMPLAINTS.getRequests()
    assertEquals(
      listOf(Bgz.Problem),
      complaintsRequests,
    )

    val patientRequests = HealthCareCategoryId.PATIENT.getRequests()
    assertEquals(
      listOf(Bgz.Patient, Gp.Patient),
      patientRequests,
    )

    val mentalRequests = HealthCareCategoryId.MENTAL.getRequests()
    assertEquals(
      listOf(Bgz.FunctionalOrMentalStatus),
      mentalRequests,
    )

    val alertsRequests = HealthCareCategoryId.ALERTS.getRequests()
    assertEquals(
      listOf(Bgz.Alert, Gp.Episodes),
      alertsRequests,
    )

    val lifestyleRequests = HealthCareCategoryId.LIFESTYLE.getRequests()
    assertEquals(
      listOf(Bgz.LivingSituation, Bgz.DrugUse, Bgz.AlcoholUse, Bgz.TabacoUse, Bgz.NutritionAdvice),
      lifestyleRequests,
    )

    val devicesRequests = HealthCareCategoryId.DEVICES.getRequests()
    assertEquals(
      listOf(Bgz.MedicalDevice, Bgz.PlannedMedicalDevices),
      devicesRequests,
    )

    val plansRequests = HealthCareCategoryId.PLANS.getRequests()
    assertEquals(
      listOf(Bgz.TreatmentDirective, Bgz.AdvanceDirective),
      plansRequests,
    )

    val paymentRequests = HealthCareCategoryId.PAYMENT.getRequests()
    assertEquals(
      listOf(Bgz.Payer),
      paymentRequests,
    )
  }

  @Test
  fun testGetProfiles() {
    val medicationProfiles = HealthCareCategoryId.MEDICATIONS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibMedicationUse,
        Profiles.zibMedicationAgreement,
        Profiles.zibAdministrationAgreement,
      ),
      medicationProfiles,
    )

    val measurementsProfiles = HealthCareCategoryId.MEASUREMENTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibBloodPressure,
        Profiles.zibBodyWeight,
        Profiles.zibBodyHeight,
        Profiles.gpDiagnosticResult,
      ),
      measurementsProfiles,
    )

    val labResultsProfiles = HealthCareCategoryId.LAB_RESULTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibLaboratoryTestResultObservation,
        Profiles.zibLaboratoryTestResultSpecimen,
        Profiles.gpLaboratoryResult,
      ),
      labResultsProfiles,
    )

    val allergiesProfiles = HealthCareCategoryId.ALLERGIES.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibAllergyIntolerance,
      ),
      allergiesProfiles,
    )

    val treatmentsProfiles = HealthCareCategoryId.TREATMENTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibProcedure,
        Profiles.zibProcedureRequest,
        Profiles.nlCoreEpisodeofcare,
      ),
      treatmentsProfiles,
    )

    val appointmentsProfiles = HealthCareCategoryId.APPOINTMENTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.eAfspraakAppointment,
        Profiles.zibEncounter,
        Profiles.gpEncounter,
        Profiles.gpEncounterReport,
      ),
      appointmentsProfiles,
    )

    val vaccinationProfiles = HealthCareCategoryId.VACCINATIONS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibVaccination,
        Profiles.nlCoreVaccinationEvent,
        Profiles.zibVaccinationRecommendation,
      ),
      vaccinationProfiles,
    )

    val documentsProfiles = HealthCareCategoryId.DOCUMENTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.iHEMHDMinimalDocumentReference,
      ),
      documentsProfiles,
    )

    val complaintsProfiles = HealthCareCategoryId.COMPLAINTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibProblem,
      ),
      complaintsProfiles,
    )

    val patientProfiles = HealthCareCategoryId.PATIENT.getProfiles()
    assertEquals(
      listOf(
        Profiles.nlCorePatient,
      ),
      patientProfiles,
    )

    val paymentProfiles = HealthCareCategoryId.PAYMENT.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibPayer,
      ),
      paymentProfiles,
    )

    val plansProfiles = HealthCareCategoryId.PLANS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibTreatmentDirective,
        Profiles.zibAdvanceDirective,
      ),
      plansProfiles,
    )

    val devicesProfiles = HealthCareCategoryId.DEVICES.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibMedicalDevice,
        Profiles.zibMedicalDeviceRequest,
      ),
      devicesProfiles,
    )

    val mentalProfiles = HealthCareCategoryId.MENTAL.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibFunctionalOrMentalStatus,
      ),
      mentalProfiles,
    )

    val lifestyleProfiles = HealthCareCategoryId.LIFESTYLE.getProfiles()
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

    val alertsProfiles = HealthCareCategoryId.ALERTS.getProfiles()
    assertEquals(
      listOf(
        Profiles.zibAlert,
      ),
      alertsProfiles,
    )
  }
}
