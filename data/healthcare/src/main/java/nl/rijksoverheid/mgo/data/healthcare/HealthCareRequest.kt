package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.uiSchema.ZibAdministrationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibAlcoholUseProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibAllergyIntoleranceProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibDrugUseProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibLivingSituationProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationUseProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibNutritionAdviceProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibProblemProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibTobaccoUseProfile

data class HealthCareRequest(
    val urlPath: String,
    val profile: String,
)

// ================
// BGZ
// https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
// ================

internal val BGZ_MEDICATION_USE =
    HealthCareRequest(
        urlPath = "MedicationStatement?category=urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6&_include=MedicationStatement:medication",
        profile = ZibMedicationUseProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationUse.value,
    )

internal val BGZ_MEDICATION_AGREEMENT =
    HealthCareRequest(
        urlPath = "MedicationRequest?category=http://snomed.info/sct|16076005&_include=MedicationRequest:medication",
        profile = ZibMedicationAgreementProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationAgreement.value,
    )

internal val BGZ_ADMINISTRATION_AGREEMENT =
    HealthCareRequest(
        urlPath =
            "MedicationDispense?category=http://snomed" +
                ".info/sct|422037009&_include=MedicationDispense:medication",
        profile = ZibAdministrationAgreementProfile.HTTPNictizNlFhirStructureDefinitionZibAdministrationAgreement.value,
    )

internal val BGZ_ALLERGIES =
    HealthCareRequest(
        urlPath = "AllergyIntolerance",
        profile = ZibAllergyIntoleranceProfile.HTTPNictizNlFhirStructureDefinitionZibAllergyIntolerance.value,
    )

internal val BGZ_PROBLEM =
    HealthCareRequest(
        urlPath = "Condition",
        profile = ZibProblemProfile.HTTPNictizNlFhirStructureDefinitionZibProblem.value,
    )

internal val BGZ_LIVING_SITUATION =
    HealthCareRequest(
        urlPath = "Observation/${'$'}lastn?code=http://snomed.info/sct|365508006",
        profile = ZibLivingSituationProfile.HTTPNictizNlFhirStructureDefinitionZibLivingSituation.value,
    )

internal val BGZ_DRUGS_USE =
    HealthCareRequest(
        urlPath = "Observation?code=http://snomed.info/sct|228366006",
        profile = ZibDrugUseProfile.HTTPNictizNlFhirStructureDefinitionZibDrugUse.value,
    )

internal val BGZ_ALCOHOL_USE =
    HealthCareRequest(
        urlPath = "Observation?code=http://snomed.info/sct|228273003",
        profile = ZibAlcoholUseProfile.HTTPNictizNlFhirStructureDefinitionZibAlcoholUse.value,
    )

internal val BGZ_TABACCO_USE =
    HealthCareRequest(
        urlPath = "Observation?code=http://snomed.info/sct|365980008",
        profile = ZibTobaccoUseProfile.HTTPNictizNlFhirStructureDefinitionZibTobaccoUse.value,
    )

internal val BGZ_NUTRITION_USE =
    HealthCareRequest(
        urlPath = "NutritionOrder",
        profile = ZibNutritionAdviceProfile.HTTPNictizNlFhirStructureDefinitionZibNutritionAdvice.value,
    )

// ================
// GP
// https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_GP_Data
// ================

internal val GP_MEDICATION_AGREEMENT =
    HealthCareRequest(
        urlPath = "MedicationRequest?periodofuse=ge[today]&category=http://snomed.info/sct|16076005&_include=MedicationRequest:medication",
        profile = ZibMedicationAgreementProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationAgreement.value,
    )
