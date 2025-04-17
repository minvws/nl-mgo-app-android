package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.fhirParser.models.Profiles
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.copy.R
import org.junit.Assert.assertEquals
import org.junit.Test

internal class HealthCategoryScreenListItemsGroupTest {
  private val uiSchemaMapper = TestUiSchemaMapper()

  @Test
  fun testMappingToListItemsGroup() =
    runTest {
      // Given: Some mgo resources
      val mgoResource1 = TEST_MGO_RESOURCE.copy(referenceId = "1")
      val mgoResource2 = TEST_MGO_RESOURCE.copy(referenceId = "2")
      val mgoResource3 = TEST_MGO_RESOURCE.copy(referenceId = "3")

      // When: mapping them to list items group
      val groupedMgoResources = mapOf(0 to listOf(mgoResource1), 1 to listOf(mgoResource2, mgoResource3))
      val listItemsGroup = groupedMgoResources.toListItemsGroup(uiSchemaMapper = uiSchemaMapper, organization = TEST_MGO_ORGANIZATION)

      // Then: List items are correctly grouped
      assertEquals(0, listItemsGroup[0].heading)
      assertEquals(1, listItemsGroup[0].items.size)
      assertEquals(1, listItemsGroup[1].heading)
      assertEquals(2, listItemsGroup[1].items.size)
    }

  @Test
  fun testGetGroupHeading() {
    val medicationUse = TEST_MGO_RESOURCE.copy(profile = Profiles.zibMedicationUse)
    assertEquals(R.string.zib_medication_use_heading, medicationUse.getGroupHeading())

    val medicationAgreement = TEST_MGO_RESOURCE.copy(profile = Profiles.zibMedicationAgreement)
    assertEquals(R.string.zib_medication_agreement_heading, medicationAgreement.getGroupHeading())

    val administrationAgreement = TEST_MGO_RESOURCE.copy(profile = Profiles.zibAdministrationAgreement)
    assertEquals(R.string.zib_administration_agreement_heading, administrationAgreement.getGroupHeading())

    val bloodPressure = TEST_MGO_RESOURCE.copy(profile = Profiles.zibBloodPressure)
    assertEquals(R.string.zib_blood_pressure_heading, bloodPressure.getGroupHeading())

    val bodyWeight = TEST_MGO_RESOURCE.copy(profile = Profiles.zibBodyWeight)
    assertEquals(R.string.zib_body_weight_heading, bodyWeight.getGroupHeading())

    val bodyHeight = TEST_MGO_RESOURCE.copy(profile = Profiles.zibBodyHeight)
    assertEquals(R.string.zib_body_height_heading, bodyHeight.getGroupHeading())

    val diagnosticResult = TEST_MGO_RESOURCE.copy(profile = Profiles.gpDiagnosticResult)
    assertEquals(R.string.gp_diagnostic_result_heading, diagnosticResult.getGroupHeading())

    val laboratoryTestResultObservation = TEST_MGO_RESOURCE.copy(profile = Profiles.zibLaboratoryTestResultObservation)
    assertEquals(R.string.zib_laboratory_test_result_observation_heading, laboratoryTestResultObservation.getGroupHeading())

    val laboratoryTestResultSpecimen = TEST_MGO_RESOURCE.copy(profile = Profiles.zibLaboratoryTestResultSpecimen)
    assertEquals(R.string.zib_laboratory_test_result_specimen_heading, laboratoryTestResultSpecimen.getGroupHeading())

    val laboratoryResult = TEST_MGO_RESOURCE.copy(profile = Profiles.gpLaboratoryResult)
    assertEquals(R.string.gp_laboratory_result_heading, laboratoryResult.getGroupHeading())

    val allergyIntolerance = TEST_MGO_RESOURCE.copy(profile = Profiles.zibAllergyIntolerance)
    assertEquals(R.string.zib_allergy_intolerance_heading, allergyIntolerance.getGroupHeading())

    val procedure = TEST_MGO_RESOURCE.copy(profile = Profiles.zibProcedure)
    assertEquals(R.string.zib_procedure_heading, procedure.getGroupHeading())

    val eAfspraakAppointment = TEST_MGO_RESOURCE.copy(profile = Profiles.eAfspraakAppointment)
    assertEquals(R.string.eAfspraak_appointment_heading, eAfspraakAppointment.getGroupHeading())

    val zibEncounter = TEST_MGO_RESOURCE.copy(profile = Profiles.zibEncounter)
    assertEquals(R.string.zib_encounter_heading, zibEncounter.getGroupHeading())

    val gpEncounter = TEST_MGO_RESOURCE.copy(profile = Profiles.gpEncounter)
    assertEquals(R.string.zib_encounter_heading, gpEncounter.getGroupHeading())

    val gpEncounterReport = TEST_MGO_RESOURCE.copy(profile = Profiles.gpEncounterReport)
    assertEquals(R.string.zib_encounter_heading, gpEncounterReport.getGroupHeading())

    val vaccination = TEST_MGO_RESOURCE.copy(profile = Profiles.zibVaccination)
    assertEquals(R.string.zib_vaccination_heading, vaccination.getGroupHeading())

    val vaccinationEvent = TEST_MGO_RESOURCE.copy(profile = Profiles.nlCoreVaccinationEvent)
    assertEquals(R.string.zib_vaccination_heading, vaccinationEvent.getGroupHeading())

    val vaccinationRecommendation = TEST_MGO_RESOURCE.copy(profile = Profiles.zibVaccinationRecommendation)
    assertEquals(R.string.zib_vaccination_recommendation_heading, vaccinationRecommendation.getGroupHeading())

    val iHEMHDMinimalDocumentReference = TEST_MGO_RESOURCE.copy(profile = Profiles.iHEMHDMinimalDocumentReference)
    assertEquals(R.string.ihe_mhd_minimal_document_reference_heading, iHEMHDMinimalDocumentReference.getGroupHeading())

    val problem = TEST_MGO_RESOURCE.copy(profile = Profiles.zibProblem)
    assertEquals(R.string.zib_problem_heading, problem.getGroupHeading())

    val nlCorePatient = TEST_MGO_RESOURCE.copy(profile = Profiles.nlCorePatient)
    assertEquals(R.string.zib_patient_heading, nlCorePatient.getGroupHeading())

    val functionalOrMentalStatus = TEST_MGO_RESOURCE.copy(profile = Profiles.zibFunctionalOrMentalStatus)
    assertEquals(R.string.zib_functional_or_mental_status_heading, functionalOrMentalStatus.getGroupHeading())

    val alert = TEST_MGO_RESOURCE.copy(profile = Profiles.zibAlert)
    assertEquals(R.string.zib_alert_heading, alert.getGroupHeading())

    val livingSituation = TEST_MGO_RESOURCE.copy(profile = Profiles.zibLivingSituation)
    assertEquals(R.string.zib_living_situation_heading, livingSituation.getGroupHeading())

    val drugUse = TEST_MGO_RESOURCE.copy(profile = Profiles.zibDrugUse)
    assertEquals(R.string.zib_drug_use_heading, drugUse.getGroupHeading())

    val alcoholUse = TEST_MGO_RESOURCE.copy(profile = Profiles.zibAlcoholUse)
    assertEquals(R.string.zib_alcohol_use_heading, alcoholUse.getGroupHeading())

    val tobaccoUse = TEST_MGO_RESOURCE.copy(profile = Profiles.zibTobaccoUse)
    assertEquals(R.string.zib_tobacco_use_heading, tobaccoUse.getGroupHeading())

    val nutritionAdvice = TEST_MGO_RESOURCE.copy(profile = Profiles.zibNutritionAdvice)
    assertEquals(R.string.zib_nutrition_advice_heading, nutritionAdvice.getGroupHeading())

    val medicalDeviceProduct = TEST_MGO_RESOURCE.copy(profile = Profiles.zibMedicalDeviceProduct)
    assertEquals(R.string.zib_medical_device_heading, medicalDeviceProduct.getGroupHeading())

    val medicalDevice = TEST_MGO_RESOURCE.copy(profile = Profiles.zibMedicalDevice)
    assertEquals(R.string.zib_medical_device_heading, medicalDevice.getGroupHeading())

    val medicalDeviceRequest = TEST_MGO_RESOURCE.copy(profile = Profiles.zibMedicalDeviceRequest)
    assertEquals(R.string.zib_medical_device_heading, medicalDeviceRequest.getGroupHeading())

    val treatmentDirective = TEST_MGO_RESOURCE.copy(profile = Profiles.zibTreatmentDirective)
    assertEquals(R.string.zib_treatment_directive_heading, treatmentDirective.getGroupHeading())

    val advanceDirective = TEST_MGO_RESOURCE.copy(profile = Profiles.zibAdvanceDirective)
    assertEquals(R.string.zib_advance_directive_heading, advanceDirective.getGroupHeading())

    val payer = TEST_MGO_RESOURCE.copy(profile = Profiles.zibPayer)
    assertEquals(R.string.zib_payer_heading, payer.getGroupHeading())

    val error = TEST_MGO_RESOURCE.copy(profile = "-1")
    assertEquals(R.string.common_error_heading, error.getGroupHeading())
  }
}
