package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.models.Profiles
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Bgz
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Documents
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Gp
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest.Vaccination

/**
 * Enum representing various categories of health care data.
 */
enum class HealthCareCategory(val id: String) {
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
 * Get a list of [HealthCareRequest] for a [HealthCareCategory].
 * These requests should be made to fill the category with health care data.
 *
 * @receiver The health care category for which requests are retrieved.
 * @return A list of [HealthCareRequest] objects corresponding to the category.
 */
fun HealthCareCategory.getRequests(): List<HealthCareRequest> {
  return when (this) {
    HealthCareCategory.MEDICATIONS -> {
      listOf(Bgz.MedicationUse, Bgz.MedicationAgreement, Bgz.AdministrationAgreement, Gp.CurrentMedication)
    }

    HealthCareCategory.MEASUREMENTS -> {
      listOf(Bgz.BloodPressure, Bgz.BodyWeight, Bgz.BodyHeight, Gp.DiagnosticsAndLabResult)
    }

    HealthCareCategory.LAB_RESULTS -> {
      listOf(Bgz.LaboratoryTestResult, Gp.DiagnosticsAndLabResult)
    }

    HealthCareCategory.ALLERGIES -> {
      listOf(Bgz.AllergyIntolerance, Gp.AllergyIntolerance)
    }

    HealthCareCategory.TREATMENTS -> {
      listOf(Bgz.Procedure, Bgz.PlannedProcedure, Gp.Episodes)
    }

    HealthCareCategory.APPOINTMENTS -> {
      listOf(Bgz.Encounter, Bgz.PlannedEncounters, Gp.Encounter, Gp.SoapEntries)
    }

    HealthCareCategory.VACCINATIONS -> {
      listOf(Bgz.Vaccination, Bgz.PlannedImmunization, Vaccination.Patient)
    }

    HealthCareCategory.DOCUMENTS -> {
      listOf(Documents.DocumentReference)
    }

    HealthCareCategory.COMPLAINTS -> {
      listOf(Bgz.Problem)
    }

    HealthCareCategory.PATIENT -> {
      listOf(Bgz.Patient, Gp.Patient)
    }

    HealthCareCategory.MENTAL -> {
      listOf(Bgz.FunctionalOrMentalStatus)
    }

    HealthCareCategory.ALERTS -> {
      listOf(Bgz.Alert, Gp.Episodes)
    }

    HealthCareCategory.LIFESTYLE -> {
      listOf(Bgz.LivingSituation, Bgz.DrugUse, Bgz.AlcoholUse, Bgz.TabacoUse, Bgz.NutritionAdvice)
    }

    HealthCareCategory.DEVICES -> {
      listOf(Bgz.MedicalDevice, Bgz.PlannedMedicalDevices)
    }

    HealthCareCategory.PLANS -> {
      listOf(Bgz.TreatmentDirective, Bgz.AdvanceDirective)
    }

    HealthCareCategory.PAYMENT -> {
      listOf(Bgz.Payer)
    }
  }
}

/**
 * Get a list of profiles for a [HealthCareCategory].
 * These profiles can be used to filter out which health care data to show for which category.
 *
 * @receiver The health care category for which profiles are retrieved.
 * @return A list of profile identifier strings corresponding to the category.
 */
fun HealthCareCategory.getProfiles(): List<String> {
  return when (this) {
    HealthCareCategory.MEDICATIONS -> {
      listOf(
        Profiles.zibMedicationUse,
        Profiles.zibMedicationAgreement,
        Profiles.zibAdministrationAgreement,
      )
    }

    HealthCareCategory.MEASUREMENTS -> {
      listOf(
        Profiles.zibBloodPressure,
        Profiles.zibBodyWeight,
        Profiles.zibBodyHeight,
        Profiles.gpDiagnosticResult,
      )
    }

    HealthCareCategory.LAB_RESULTS -> {
      listOf(
        Profiles.zibLaboratoryTestResultObservation,
        Profiles.zibLaboratoryTestResultSpecimen,
        Profiles.gpLaboratoryResult,
      )
    }

    HealthCareCategory.ALLERGIES -> {
      listOf(
        Profiles.zibAllergyIntolerance,
      )
    }

    HealthCareCategory.TREATMENTS -> {
      listOf(
        Profiles.zibProcedure,
        Profiles.zibProcedureRequest,
        Profiles.nlCoreEpisodeofcare,
      )
    }

    HealthCareCategory.APPOINTMENTS -> {
      listOf(
        Profiles.eAfspraakAppointment,
        Profiles.zibEncounter,
        Profiles.gpEncounter,
        Profiles.gpEncounterReport,
      )
    }

    HealthCareCategory.VACCINATIONS -> {
      listOf(
        Profiles.zibVaccination,
        Profiles.nlCoreVaccinationEvent,
        Profiles.zibVaccinationRecommendation,
      )
    }

    HealthCareCategory.DOCUMENTS -> {
      listOf(
        Profiles.iHEMHDMinimalDocumentReference,
      )
    }

    HealthCareCategory.COMPLAINTS -> {
      listOf(
        Profiles.zibProblem,
      )
    }

    HealthCareCategory.PATIENT -> {
      listOf(
        Profiles.nlCorePatient,
      )
    }

    HealthCareCategory.PAYMENT -> {
      listOf(
        Profiles.zibPayer,
      )
    }

    HealthCareCategory.PLANS -> {
      listOf(
        Profiles.zibTreatmentDirective,
        Profiles.zibAdvanceDirective,
      )
    }

    HealthCareCategory.DEVICES -> {
      listOf(
        Profiles.zibMedicalDevice,
        Profiles.zibMedicalDeviceRequest,
      )
    }

    HealthCareCategory.MENTAL -> {
      listOf(
        Profiles.zibFunctionalOrMentalStatus,
      )
    }

    HealthCareCategory.LIFESTYLE -> {
      listOf(
        Profiles.zibLivingSituation,
        Profiles.zibDrugUse,
        Profiles.zibAlcoholUse,
        Profiles.zibTobaccoUse,
        Profiles.zibNutritionAdvice,
      )
    }

    HealthCareCategory.ALERTS ->
      listOf(
        Profiles.zibAlert,
      )
  }
}
