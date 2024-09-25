package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.uiSchema.ZibAdministrationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationAgreementProfile
import nl.rijksoverheid.mgo.data.uiSchema.ZibMedicationUseProfile

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

// ================
// GP
// https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_GP_Data
// ================

internal val GP_MEDICATION_AGREEMENT =
    HealthCareRequest(
        urlPath = "MedicationRequest?periodofuse=ge[today]&category=http://snomed.info/sct|16076005&_include=MedicationRequest:medication",
        profile = ZibMedicationAgreementProfile.HTTPNictizNlFhirStructureDefinitionZibMedicationAgreement.value,
    )
