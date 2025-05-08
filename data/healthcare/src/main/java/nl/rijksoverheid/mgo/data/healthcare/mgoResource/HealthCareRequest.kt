package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.FhirVersion
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.CATEGORY
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.CLASS
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.CODE
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.INCLUDE
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.PERIOD_OF_USE
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.STATUS
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequestQueryKey.TYPE
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// ================
// BGZ
// https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
// ================

sealed class HealthCareRequest(
  open val path: String,
  open val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>,
  val dataServiceType: MgoOrganizationDataServiceType,
  val fhirVersion: FhirVersion,
) {
  // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
  sealed class Bgz(override val path: String, override val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>) :
    HealthCareRequest(path, queryParameters, MgoOrganizationDataServiceType.BGZ, FhirVersion.R3) {
    data object MedicationUse : Bgz(
      path = "MedicationStatement",
      queryParameters =
        listOf(
          Pair(CATEGORY, "urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6"),
          Pair(INCLUDE, "MedicationStatement:medication"),
        ),
    )

    data object MedicationAgreement : Bgz(
      path = "MedicationRequest",
      queryParameters =
        listOf(
          Pair(CATEGORY, "http://snomed.info/sct|16076005"),
          Pair(INCLUDE, "MedicationRequest:medication"),
        ),
    )

    data object AdministrationAgreement : Bgz(
      path = "MedicationDispense",
      queryParameters =
        listOf(
          Pair(CATEGORY, "http://snomed.info/sct|422037009"),
          Pair(INCLUDE, "MedicationDispense:medication"),
        ),
    )

    data object BloodPressure : Bgz(
      path = "Observation/\$lastn",
      queryParameters =
        listOf(
          Pair(CODE, "http://loinc.org|85354-9"),
        ),
    )

    data object BodyWeight : Bgz(
      path = "Observation/\$lastn",
      queryParameters =
        listOf(
          Pair(CODE, "http://loinc.org|29463-7"),
        ),
    )

    data object BodyHeight : Bgz(
      path = "Observation/\$lastn",
      queryParameters =
        listOf(
          Pair(CODE, "http://loinc.org|8302-2,http://loinc.org|8306-3,http://loinc.org|8308-9"),
        ),
    )

    data object LaboratoryTestResult : Bgz(
      path = "Observation/\$lastn",
      queryParameters =
        listOf(
          Pair(CATEGORY, "http://snomed.info/sct|275711006"),
          Pair(INCLUDE, "Observation:related-target"),
          Pair(INCLUDE, "Observation:specimen"),
        ),
    )

    data object AllergyIntolerance : Bgz(
      path = "AllergyIntolerance",
      queryParameters = listOf(),
    )

    data object Procedure : Bgz(
      path = "Procedure",
      queryParameters =
        listOf(
          Pair(CATEGORY, "http://snomed.info/sct|387713003"),
        ),
    )

    data object PlannedProcedure : Bgz(
      path = "ProcedureRequest",
      queryParameters =
        listOf(
          Pair(STATUS, "active"),
        ),
    )

    data object Encounter : Bgz(
      path = "Encounter",
      queryParameters =
        listOf(
          Pair(
            CLASS,
            "http://hl7.org/fhir/v3/ActCode|IMP,http://hl7.org/fhir/v3/ActCode|ACUTE,http://hl7.org/fhir/v3/ActCode|NONAC",
          ),
        ),
    )

    data object PlannedEncounters : Bgz(
      path = "Appointment",
      queryParameters =
        listOf(
          Pair(STATUS, "booked,pending,proposed"),
        ),
    )

    data object Vaccination : Bgz(
      path = "Immunization",
      queryParameters =
        listOf(
          Pair(STATUS, "completed"),
        ),
    )

    data object PlannedImmunization : Bgz(
      path = "ImmunizationRecommendation",
      queryParameters = listOf(),
    )

    data object Problem : Bgz(
      path = "Condition",
      queryParameters = listOf(),
    )

    data object Patient : Bgz(
      path = "Patient",
      queryParameters =
        listOf(
          Pair(INCLUDE, "Patient:general-practitioner"),
        ),
    )

    data object FunctionalOrMentalStatus : Bgz(
      path = "Observation/\$lastn",
      queryParameters =
        listOf(
          Pair(CATEGORY, "http://snomed.info/sct|118228005,http://snomed.info/sct|384821006"),
        ),
    )

    data object Alert : Bgz(
      path = "Flag",
      queryParameters = listOf(),
    )

    data object LivingSituation : Bgz(
      path = "Observation/\$lastn",
      queryParameters =
        listOf(
          Pair(CODE, "http://snomed.info/sct|365508006"),
        ),
    )

    data object DrugUse : Bgz(
      path = "Observation",
      queryParameters =
        listOf(
          Pair(CODE, "http://snomed.info/sct|228366006"),
        ),
    )

    data object AlcoholUse : Bgz(
      path = "Observation",
      queryParameters =
        listOf(
          Pair(CODE, "http://snomed.info/sct|228273003"),
        ),
    )

    data object TabacoUse : Bgz(
      path = "Observation",
      queryParameters =
        listOf(
          Pair(CODE, "http://snomed.info/sct|365980008"),
        ),
    )

    data object NutritionAdvice : Bgz(
      path = "NutritionOrder",
      queryParameters = listOf(),
    )

    data object MedicalDevice : Bgz(
      path = "DeviceUseStatement",
      queryParameters =
        listOf(
          Pair(INCLUDE, "DeviceUseStatement:device"),
        ),
    )

    data object PlannedMedicalDevices : Bgz(
      path = "DeviceRequest",
      queryParameters =
        listOf(
          Pair(STATUS, "active"),
          Pair(INCLUDE, "DeviceRequest:device"),
        ),
    )

    data object TreatmentDirective : Bgz(
      path = "Consent",
      queryParameters =
        listOf(
          Pair(CATEGORY, "http://snomed.info/sct|11291000146105"),
        ),
    )

    data object AdvanceDirective : Bgz(
      path = "Consent",
      queryParameters =
        listOf(
          Pair(CATEGORY, "http://snomed.info/sct|11341000146107"),
        ),
    )

    data object Payer : Bgz(
      path = "Coverage",
      queryParameters =
        listOf(
          Pair(INCLUDE, "Coverage:payor:Patient"),
          Pair(INCLUDE, "Coverage:payor:Organization"),
        ),
    )
  }

  // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_GP_Data
  sealed class Gp(override val path: String, override val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>) :
    HealthCareRequest
    (path, queryParameters, MgoOrganizationDataServiceType.GP, FhirVersion.R3) {
    data object CurrentMedication : Gp(
      path = "MedicationRequest",
      queryParameters =
        listOf(
          Pair(PERIOD_OF_USE, "ge${getCurrentDate()}"),
          Pair(CATEGORY, "http://snomed.info/sct|16076005"),
          Pair(INCLUDE, "MedicationRequest:medication"),
        ),
    )

    data object DiagnosticsAndLabResult : Gp(
      path = "Observation",
      queryParameters =
        listOf(
          Pair(CODE, "https://referentiemodel.nhg.org/tabellen/nhg-tabel-45-diagnostische-bepalingen|"),
          Pair(INCLUDE, "Observation:related-target"),
          Pair(INCLUDE, "Observation:specimen"),
        ),
    )

    data object AllergyIntolerance : Gp(
      path = "AllergyIntolerance",
      queryParameters =
        listOf(
          Pair(CATEGORY, "medication"),
        ),
    )

    data object Encounter : Gp(
      path = "Encounter",
      queryParameters = listOf(),
    )

    data object SoapEntries : Gp(
      path = "Composition",
      queryParameters =
        listOf(
          Pair(TYPE, "http://loinc.org|67781-5"),
        ),
    )

    data object Patient : Gp(
      path = "Patient",
      queryParameters =
        listOf(
          Pair(INCLUDE, "Patient:general-practitioner"),
        ),
    )

    data object Episodes : Gp(
      path = "EpisodeOfCare",
      queryParameters = listOf(),
    )
  }

  // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/OntwerpPDFA
  sealed class Documents(override val path: String, override val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>) :
    HealthCareRequest
    (path, queryParameters, MgoOrganizationDataServiceType.DOCUMENTS, FhirVersion.R3) {
    data object DocumentReference : Documents(
      path = "DocumentReference",
      queryParameters = listOf(),
    )
  }

  // https://informatiestandaarden.nictiz.nl/wiki/MedMij:V6/FHIR_Vaccination-Immunization
  sealed class Vaccination(override val path: String, override val queryParameters: List<Pair<HealthCareRequestQueryKey, String>>) :
    HealthCareRequest
    (path, queryParameters, MgoOrganizationDataServiceType.VACCINATION, FhirVersion.R4) {
    data object Patient : Vaccination(
      path = "Immunization",
      queryParameters = listOf(),
    )
  }
}

enum class HealthCareRequestQueryKey(val value: String) {
  CATEGORY("category"),
  INCLUDE("_include"),
  CODE("code"),
  DATE("date"),
  STATUS("status"),
  PERIOD_OF_USE("periodofuse"),
  CLASS("class"),
  TYPE("type"),
}

internal fun getCurrentDate(): String {
  val currentDate = LocalDate.now()
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  return currentDate.format(formatter)
}
