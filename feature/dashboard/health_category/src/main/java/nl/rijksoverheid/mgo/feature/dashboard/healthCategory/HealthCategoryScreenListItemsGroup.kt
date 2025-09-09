package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.models.Profiles
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.copy.R

/**
 * Represents a group of [HealthCategoryScreenListItem] to show in [HealthCategoryScreen].
 *
 * @param heading The string resource to show as heading.
 * @param items The list of [HealthCategoryScreenListItem] that fall under this group.
 */
data class HealthCategoryScreenListItemsGroup(
  @StringRes val heading: Int,
  val items: List<HealthCategoryScreenListItem>,
)

typealias MgoResourceGroupHeading = Int

internal suspend fun Map<MgoResourceGroupHeading, List<MgoResource>>.toListItemsGroup(
  uiSchemaMapper: UiSchemaMapper,
  organization: MgoOrganization,
) = map { mgoResourcesMap ->
  HealthCategoryScreenListItemsGroup(
    heading = mgoResourcesMap.key,
    items =
      mgoResourcesMap.value.map { mgoResource ->
        val uiSchema = uiSchemaMapper.getSummary(organization.name, mgoResource)
        HealthCategoryScreenListItem(
          title = uiSchema.label,
          subtitle = organization.name,
          mgoResource = mgoResource,
          organization = organization,
        )
      },
  )
}

/**
 * @receiver The [MgoResource] to retrieve the heading for.
 * @return The heading of the [MgoResource].
 */
@StringRes
internal fun MgoResource.getGroupHeading(): MgoResourceGroupHeading =
  when (profile) {
    Profiles.zibMedicationUse -> {
      R.string.zib_medication_use_heading
    }
    Profiles.zibMedicationAgreement -> {
      R.string.zib_medication_agreement_heading
    }
    Profiles.zibAdministrationAgreement -> {
      R.string.zib_administration_agreement_heading
    }
    Profiles.zibBloodPressure -> {
      R.string.zib_blood_pressure_heading
    }
    Profiles.zibBodyWeight -> {
      R.string.zib_body_weight_heading
    }
    Profiles.zibBodyHeight -> {
      R.string.zib_body_height_heading
    }
    Profiles.gpDiagnosticResult -> {
      R.string.gp_diagnostic_result_heading
    }
    Profiles.zibLaboratoryTestResultObservation -> {
      R.string.zib_laboratory_test_result_observation_heading
    }
    Profiles.zibLaboratoryTestResultSpecimen -> {
      R.string.zib_laboratory_test_result_specimen_heading
    }
    Profiles.gpLaboratoryResult -> {
      R.string.gp_laboratory_result_heading
    }
    Profiles.zibAllergyIntolerance -> {
      R.string.zib_allergy_intolerance_heading
    }
    Profiles.zibProcedure -> {
      R.string.zib_procedure_heading
    }
    Profiles.zibProcedureRequest -> {
      R.string.zib_procedure_request_heading
    }
    Profiles.eAfspraakAppointment -> {
      R.string.eAfspraak_appointment_heading
    }
    Profiles.zibEncounter -> {
      R.string.zib_encounter_heading
    }
    Profiles.gpEncounter -> {
      R.string.zib_encounter_heading
    }
    Profiles.gpEncounterReport -> {
      R.string.zib_encounter_heading
    }
    Profiles.zibVaccination -> {
      R.string.zib_vaccination_heading
    }
    Profiles.nlCoreVaccinationEvent -> {
      R.string.zib_vaccination_heading
    }
    Profiles.zibVaccinationRecommendation -> {
      R.string.zib_vaccination_recommendation_heading
    }
    Profiles.iHEMHDMinimalDocumentReference -> {
      R.string.ihe_mhd_minimal_document_reference_heading
    }
    Profiles.zibProblem -> {
      R.string.zib_problem_heading
    }
    Profiles.nlCorePatient -> {
      R.string.zib_patient_heading
    }
    Profiles.zibFunctionalOrMentalStatus -> {
      R.string.zib_functional_or_mental_status_heading
    }
    Profiles.zibAlert -> {
      R.string.zib_alert_heading
    }
    Profiles.zibLivingSituation -> {
      R.string.zib_living_situation_heading
    }
    Profiles.zibDrugUse -> {
      R.string.zib_drug_use_heading
    }
    Profiles.zibAlcoholUse -> {
      R.string.zib_alcohol_use_heading
    }
    Profiles.zibTobaccoUse -> {
      R.string.zib_tobacco_use_heading
    }
    Profiles.zibNutritionAdvice -> {
      R.string.zib_nutrition_advice_heading
    }
    Profiles.zibMedicalDeviceProduct -> {
      R.string.zib_medical_device_heading
    }
    Profiles.zibMedicalDevice -> {
      R.string.zib_medical_device_heading
    }
    Profiles.zibMedicalDeviceRequest -> {
      R.string.zib_medical_device_heading
    }
    Profiles.zibTreatmentDirective -> {
      R.string.zib_treatment_directive_heading
    }
    Profiles.zibAdvanceDirective -> {
      R.string.zib_advance_directive_heading
    }
    Profiles.zibPayer -> {
      R.string.zib_payer_heading
    }
    Profiles.nlCoreEpisodeofcare -> {
      R.string.zib_procedure_request_heading
    }
    else -> R.string.common_error_heading
  }
