package nl.rijksoverheid.mgo.data.healthcare.mgoResource.category

import androidx.annotation.Keep
import nl.rijksoverheid.mgo.data.fhirParser.models.Profiles
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Bgz
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Documents
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Gp
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Vaccination

/**
 * Enum representing various categories of health care data.
 */
@Keep
enum class HealthCareCategoryId(
  val id: String,
) {
  COMPLAINTS("complaints"), // Medische klachten
  LAB_RESULTS("lab_results"), // Uitslagen
  MEASUREMENTS("measurements"), // Metingen
  MEDICATIONS("medication"), // Medicijnen
  TREATMENTS("treatments"), // Behandelingen
  APPOINTMENTS("appointments"), // Afspraken
  VACCINATIONS("vaccinations"), // Vaccinaties
  DOCUMENTS("documents"), // Documenten
  ALLERGIES("allergies"), // Allergieën
  MENTAL("mental"), // Mentaal welzijn
  LIFESTYLE("lifestyle"), // Leefstijl
  DEVICES("devices"), // Medische hulpmiddelen
  PLANS("plans"), // Behandelplan
  ALERTS("alerts"), // Waarschuwingen
  PATIENT("patient"), // Persoonlijke gegevens
  PAYMENT("payment"), // Betaalgegevens
}

/**
 * Get a list of [nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest] for a [HealthCareCategoryId].
 * These requests should be made to fill the category with health care data.
 *
 * @receiver The health care category for which requests are retrieved.
 * @return A list of [nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest] objects corresponding to the category.
 */
fun HealthCareCategoryId.getRequests(): List<HealthCareRequest> =
  when (this) {
    HealthCareCategoryId.MEDICATIONS -> {
      listOf(Bgz.MedicationUse, Bgz.MedicationAgreement, Bgz.AdministrationAgreement, Gp.CurrentMedication)
    }

    HealthCareCategoryId.MEASUREMENTS -> {
      listOf(Bgz.BloodPressure, Bgz.BodyWeight, Bgz.BodyHeight, Gp.DiagnosticsAndLabResult)
    }

    HealthCareCategoryId.LAB_RESULTS -> {
      listOf(Bgz.LaboratoryTestResult, Gp.DiagnosticsAndLabResult)
    }

    HealthCareCategoryId.ALLERGIES -> {
      listOf(Bgz.AllergyIntolerance, Gp.AllergyIntolerance)
    }

    HealthCareCategoryId.TREATMENTS -> {
      listOf(Bgz.Procedure, Bgz.PlannedProcedure, Gp.Episodes)
    }

    HealthCareCategoryId.APPOINTMENTS -> {
      listOf(Bgz.Encounter, Bgz.PlannedEncounters, Gp.Encounter, Gp.SoapEntries)
    }

    HealthCareCategoryId.VACCINATIONS -> {
      listOf(Bgz.Vaccination, Bgz.PlannedImmunization, Vaccination.Patient)
    }

    HealthCareCategoryId.DOCUMENTS -> {
      listOf(Documents.DocumentReference)
    }

    HealthCareCategoryId.COMPLAINTS -> {
      listOf(Bgz.Problem)
    }

    HealthCareCategoryId.PATIENT -> {
      listOf(Bgz.Patient, Gp.Patient)
    }

    HealthCareCategoryId.MENTAL -> {
      listOf(Bgz.FunctionalOrMentalStatus)
    }

    HealthCareCategoryId.ALERTS -> {
      listOf(Bgz.Alert, Gp.Episodes)
    }

    HealthCareCategoryId.LIFESTYLE -> {
      listOf(Bgz.LivingSituation, Bgz.DrugUse, Bgz.AlcoholUse, Bgz.TabacoUse, Bgz.NutritionAdvice)
    }

    HealthCareCategoryId.DEVICES -> {
      listOf(Bgz.MedicalDevice, Bgz.PlannedMedicalDevices)
    }

    HealthCareCategoryId.PLANS -> {
      listOf(Bgz.TreatmentDirective, Bgz.AdvanceDirective)
    }

    HealthCareCategoryId.PAYMENT -> {
      listOf(Bgz.Payer)
    }
  }

/**
 * Get a list of profiles for a [HealthCareCategoryId].
 * These profiles can be used to filter out which health care data to show for which category.
 *
 * @receiver The health care category for which profiles are retrieved.
 * @return A list of profile identifier strings corresponding to the category.
 */
fun HealthCareCategoryId.getProfiles(): List<String> =
  when (this) {
    HealthCareCategoryId.MEDICATIONS -> {
      listOf(
        Profiles.zibMedicationUse,
        Profiles.zibMedicationAgreement,
        Profiles.zibAdministrationAgreement,
      )
    }

    HealthCareCategoryId.MEASUREMENTS -> {
      listOf(
        Profiles.zibBloodPressure,
        Profiles.zibBodyWeight,
        Profiles.zibBodyHeight,
        Profiles.gpDiagnosticResult,
      )
    }

    HealthCareCategoryId.LAB_RESULTS -> {
      listOf(
        Profiles.zibLaboratoryTestResultObservation,
        Profiles.zibLaboratoryTestResultSpecimen,
        Profiles.gpLaboratoryResult,
      )
    }

    HealthCareCategoryId.ALLERGIES -> {
      listOf(
        Profiles.zibAllergyIntolerance,
      )
    }

    HealthCareCategoryId.TREATMENTS -> {
      listOf(
        Profiles.zibProcedure,
        Profiles.zibProcedureRequest,
        Profiles.nlCoreEpisodeofcare,
      )
    }

    HealthCareCategoryId.APPOINTMENTS -> {
      listOf(
        Profiles.eAfspraakAppointment,
        Profiles.zibEncounter,
        Profiles.gpEncounter,
        Profiles.gpEncounterReport,
      )
    }

    HealthCareCategoryId.VACCINATIONS -> {
      listOf(
        Profiles.zibVaccination,
        Profiles.nlCoreVaccinationEvent,
        Profiles.zibVaccinationRecommendation,
      )
    }

    HealthCareCategoryId.DOCUMENTS -> {
      listOf(
        Profiles.iHEMHDMinimalDocumentReference,
      )
    }

    HealthCareCategoryId.COMPLAINTS -> {
      listOf(
        Profiles.zibProblem,
      )
    }

    HealthCareCategoryId.PATIENT -> {
      listOf(
        Profiles.nlCorePatient,
      )
    }

    HealthCareCategoryId.PAYMENT -> {
      listOf(
        Profiles.zibPayer,
      )
    }

    HealthCareCategoryId.PLANS -> {
      listOf(
        Profiles.zibTreatmentDirective,
        Profiles.zibAdvanceDirective,
      )
    }

    HealthCareCategoryId.DEVICES -> {
      listOf(
        Profiles.zibMedicalDevice,
        Profiles.zibMedicalDeviceRequest,
      )
    }

    HealthCareCategoryId.MENTAL -> {
      listOf(
        Profiles.zibFunctionalOrMentalStatus,
      )
    }

    HealthCareCategoryId.LIFESTYLE -> {
      listOf(
        Profiles.zibLivingSituation,
        Profiles.zibDrugUse,
        Profiles.zibAlcoholUse,
        Profiles.zibTobaccoUse,
        Profiles.zibNutritionAdvice,
      )
    }

    HealthCareCategoryId.ALERTS ->
      listOf(
        Profiles.zibAlert,
      )
  }
