package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType
import nl.rijksoverheid.mgo.data.uiSchema.IheMhdMinimalDocumentReferenceProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibAdministrationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibAlcoholUseProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibAlertProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibAllergyIntoleranceProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibDrugUseProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibFunctionalOrMentalStatusProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibLivingSituationProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicalDeviceProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationUseProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibNutritionAdviceProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibProblemProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibTobaccoUseProfile

data class HealthCareRequest(
    val path: String,
    val queryParameters: List<HealthCareRequestQuery>,
    val profile: String,
    val dataServiceType: MgoOrganizationDataServiceType,
)

data class HealthCareRequestQuery(
    val key: HealthCareRequestQueryKey,
    val value: String
)

enum class HealthCareRequestQueryKey(val value: String) {
    CATEGORY("category"),
    INCLUDE("include")
}

// ================
// BGZ
// https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
// ================

enum class BGZ(val request: HealthCareRequest) {
    MEDICATION_USE(
        request = HealthCareRequest(
            path = "MedicationStatement",
            queryParameters = listOf(
                HealthCareRequestQuery(
                    key = HealthCareRequestQueryKey.CATEGORY,
                    value = "urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6"
                ),
                HealthCareRequestQuery(
                    key = HealthCareRequestQueryKey.INCLUDE,
                    value = "MedicationStatement:medication"
                )
            ),
            profile = ZibMedicationUseProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationUse.value,
            dataServiceType = MgoOrganizationDataServiceType.BGZ
        )
    )
}
