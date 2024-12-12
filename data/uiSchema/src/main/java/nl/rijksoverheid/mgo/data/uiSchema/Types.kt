// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json                                    = Json { allowStructuredMapKeys = true }
// val downloadLink                            = json.parse(DownloadLink.serializer(), jsonString)
// val uIEntryOptions                          = json.parse(UIEntryOptions.serializer(), jsonString)
// val eAfspraakAppointment                    = json.parse(EAfspraakAppointment.serializer(), jsonString)
// val mgoCodeableConcept                      = json.parse(MgoCodeableConcept.serializer(), jsonString)
// val mgoCoding                               = json.parse(MgoCoding.serializer(), jsonString)
// val mgoReference                            = json.parse(MgoReference.serializer(), jsonString)
// val fhirVersionR3                           = json.parse(FhirVersionR3.serializer(), jsonString)
// val fhirVersionR4                           = json.parse(FhirVersionR4.serializer(), jsonString)
// val gpDiagnosticResult                      = json.parse(GpDiagnosticResult.serializer(), jsonString)
// val mgoPeriod                               = json.parse(MgoPeriod.serializer(), jsonString)
// val mgoQuantity                             = json.parse(MgoQuantity.serializer(), jsonString)
// val mgoRange                                = json.parse(MgoRange.serializer(), jsonString)
// val mgoIdentifier                           = json.parse(MgoIdentifier.serializer(), jsonString)
// val gpEncounter                             = json.parse(GpEncounter.serializer(), jsonString)
// val gpEncounterReport                       = json.parse(GpEncounterReport.serializer(), jsonString)
// val gpJournalEntry                          = json.parse(GpJournalEntry.serializer(), jsonString)
// val gpLaboratoryResult                      = json.parse(GpLaboratoryResult.serializer(), jsonString)
// val iheMhdMinimalDocumentReference          = json.parse(IheMhdMinimalDocumentReference.serializer(), jsonString)
// val mgoAttachment                           = json.parse(MgoAttachment.serializer(), jsonString)
// val mgoString                               = json.parse(MgoString.serializer(), jsonString)
// val mgoUnsignedInt                          = json.parse(MgoUnsignedInt.serializer(), jsonString)
// val mgoDateTime                             = json.parse(MgoDateTime.serializer(), jsonString)
// val mgoAnnotation                           = json.parse(MgoAnnotation.serializer(), jsonString)
// val mgoBoolean                              = json.parse(MgoBoolean.serializer(), jsonString)
// val mgoCode                                 = json.parse(MgoCode.serializer(), jsonString)
// val mgoDate                                 = json.parse(MgoDate.serializer(), jsonString)
// val mgoDecimal                              = json.parse(MgoDecimal.serializer(), jsonString)
// val mgoDuration                             = json.parse(MgoDuration.serializer(), jsonString)
// val mgoInteger                              = json.parse(MgoInteger.serializer(), jsonString)
// val mgoInteger64                            = json.parse(MgoInteger64.serializer(), jsonString)
// val mgoPositiveInt                          = json.parse(MgoPositiveInt.serializer(), jsonString)
// val mgoRatio                                = json.parse(MgoRatio.serializer(), jsonString)
// val multipleGroupedValues                   = json.parse(MultipleGroupedValues.serializer(), jsonString)
// val uIEntryValueMULTIPLEGROUPEDVALUESString = json.parse(UIEntryValueMULTIPLEGROUPEDVALUESString.serializer(), jsonString)
// val multipleValues                          = json.parse(MultipleValues.serializer(), jsonString)
// val uIEntryValueMULTIPLEVALUESString        = json.parse(UIEntryValueMULTIPLEVALUESString.serializer(), jsonString)
// val nlCoreAddress                           = json.parse(NlCoreAddress.serializer(), jsonString)
// val nlCoreContactpoint                      = json.parse(NlCoreContactpoint.serializer(), jsonString)
// val nlCoreHealthProfessionalPractitioner    = json.parse(NlCoreHealthProfessionalPractitioner.serializer(), jsonString)
// val nlCoreHumanname                         = json.parse(NlCoreHumanname.serializer(), jsonString)
// val nlCoreObservation                       = json.parse(NlCoreObservation.serializer(), jsonString)
// val nlCoreOrganization                      = json.parse(NlCoreOrganization.serializer(), jsonString)
// val nlCorePatient                           = json.parse(NlCorePatient.serializer(), jsonString)
// val nlCorePatientR4                         = json.parse(NlCorePatientR4.serializer(), jsonString)
// val nlCorePractitioner                      = json.parse(NlCorePractitioner.serializer(), jsonString)
// val nlCorePractitionerRole                  = json.parse(NlCorePractitionerRole.serializer(), jsonString)
// val nlCoreVaccinationEvent                  = json.parse(NlCoreVaccinationEvent.serializer(), jsonString)
// val referenceValue                          = json.parse(ReferenceValue.serializer(), jsonString)
// val uIEntryValueREFERENCEVALUEString        = json.parse(UIEntryValueREFERENCEVALUEString.serializer(), jsonString)
// val singleValue                             = json.parse(SingleValue.serializer(), jsonString)
// val uIEntryValueSINGLEVALUEString           = json.parse(UIEntryValueSINGLEVALUEString.serializer(), jsonString)
// val uIEntry                                 = json.parse(UIEntry.serializer(), jsonString)
// val uISchema                                = json.parse(UISchema.serializer(), jsonString)
// val uISchemaGroup                           = json.parse(UISchemaGroup.serializer(), jsonString)
// val zibAdministrationAgreement              = json.parse(ZibAdministrationAgreement.serializer(), jsonString)
// val zibInstructionsForUse                   = json.parse(ZibInstructionsForUse.serializer(), jsonString)
// val zibAdministrationSchedule               = json.parse(ZibAdministrationSchedule.serializer(), jsonString)
// val zibAdvanceDirective                     = json.parse(ZibAdvanceDirective.serializer(), jsonString)
// val zibAlcoholUse                           = json.parse(ZibAlcoholUse.serializer(), jsonString)
// val zibAlert                                = json.parse(ZibAlert.serializer(), jsonString)
// val zibAllergyIntolerance                   = json.parse(ZibAllergyIntolerance.serializer(), jsonString)
// val zibBloodPressure                        = json.parse(ZibBloodPressure.serializer(), jsonString)
// val zibBodyHeight                           = json.parse(ZibBodyHeight.serializer(), jsonString)
// val zibBodyWeight                           = json.parse(ZibBodyWeight.serializer(), jsonString)
// val zibDrugUse                              = json.parse(ZibDrugUse.serializer(), jsonString)
// val zibEncounter                            = json.parse(ZibEncounter.serializer(), jsonString)
// val zibFunctionalOrMentalStatus             = json.parse(ZibFunctionalOrMentalStatus.serializer(), jsonString)
// val zibLaboratoryTestResultObservation      = json.parse(ZibLaboratoryTestResultObservation.serializer(), jsonString)
// val zibLaboratoryTestResultSpecimen         = json.parse(ZibLaboratoryTestResultSpecimen.serializer(), jsonString)
// val zibLaboratoryTestResultSpecimenIsolate  = json.parse(ZibLaboratoryTestResultSpecimenIsolate.serializer(), jsonString)
// val zibLaboratoryTestResultSubstance        = json.parse(ZibLaboratoryTestResultSubstance.serializer(), jsonString)
// val zibLivingSituation                      = json.parse(ZibLivingSituation.serializer(), jsonString)
// val zibMedicalDevice                        = json.parse(ZibMedicalDevice.serializer(), jsonString)
// val zibMedicalDeviceProduct                 = json.parse(ZibMedicalDeviceProduct.serializer(), jsonString)
// val zibMedicalDeviceRequest                 = json.parse(ZibMedicalDeviceRequest.serializer(), jsonString)
// val zibMedicationAgreement                  = json.parse(ZibMedicationAgreement.serializer(), jsonString)
// val zibMedicationUse                        = json.parse(ZibMedicationUse.serializer(), jsonString)
// val zibNutritionAdvice                      = json.parse(ZibNutritionAdvice.serializer(), jsonString)
// val zibPayer                                = json.parse(ZibPayer.serializer(), jsonString)
// val zibProblem                              = json.parse(ZibProblem.serializer(), jsonString)
// val zibProcedure                            = json.parse(ZibProcedure.serializer(), jsonString)
// val zibProcedureRequest                     = json.parse(ZibProcedureRequest.serializer(), jsonString)
// val zibProduct                              = json.parse(ZibProduct.serializer(), jsonString)
// val zibProductIngredient                    = json.parse(ZibProductIngredient.serializer(), jsonString)
// val zibProductPackage                       = json.parse(ZibProductPackage.serializer(), jsonString)
// val zibTobaccoUse                           = json.parse(ZibTobaccoUse.serializer(), jsonString)
// val zibTreatmentDirective                   = json.parse(ZibTreatmentDirective.serializer(), jsonString)
// val zibVaccination                          = json.parse(ZibVaccination.serializer(), jsonString)
// val zibVaccinationRecommendation            = json.parse(ZibVaccinationRecommendation.serializer(), jsonString)

package nl.rijksoverheid.mgo.data.uiSchema

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias MgoString = String
typealias MgoUnsignedInt = Double
typealias MgoDateTime = String
typealias MgoBoolean = Boolean
typealias MgoCode = String
typealias MgoDate = String
typealias MgoDecimal = Double
typealias MgoInteger = Double
typealias MgoInteger64 = Double
typealias MgoPositiveInt = Double

@Serializable
data class DownloadLink(
    val label: String,
    val showEmpty: Boolean? = null,
    val type: DownloadLinkType,
    val url: String,
)

@Serializable
enum class DownloadLinkType(val value: String) {
    @SerialName("DOWNLOAD_LINK")
    DownloadLink("DOWNLOAD_LINK"),
}

@Serializable
data class UIEntryOptions(
    val showEmpty: Boolean? = null,
)

@Serializable
data class EAfspraakAppointment(
    val description: String? = null,
    val end: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val participant: List<EAfspraakAppointmentParticipant>? = null,
    val profile: EAfspraakAppointmentProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val specialty: List<MgoCodeableConcept>? = null,
    val start: String? = null,
    val status: String? = null,
)

@Serializable
enum class FhirVersionR3(val value: String) {
    @SerialName("R3")
    R3("R3"),
}

@Serializable
data class EAfspraakAppointmentParticipant(
    val actor: MgoReference? = null,
)

@Serializable
data class MgoReference(
    val display: String? = null,
    val reference: String? = null,
)

@Serializable
enum class EAfspraakAppointmentProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/eAfspraak-Appointment")
    HTTPNictizNlFhirStructureDefinitionEAfspraakAppointment("http://nictiz.nl/fhir/StructureDefinition/eAfspraak-Appointment"),
}

@Serializable
data class MgoCodeableConcept(
    val coding: List<MgoCoding>,
    val text: String? = null,
)

@Serializable
data class MgoCoding(
    val code: String? = null,
    val display: String? = null,
    val system: String? = null,
)

@Serializable
data class GpDiagnosticResult(
    val code: MgoCodeableConcept? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val effective: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val performer: List<MgoReference>? = null,
    val profile: GpDiagnosticResultProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueBoolean: Boolean? = null,
    val valueCodeableConcept: MgoCodeableConcept? = null,
    val valueDateTime: String? = null,
    val valuePeriod: MgoPeriod? = null,
    val valueQuantity: MgoDuration? = null,
    val valueRange: MgoRange? = null,
    val valueString: String? = null,
)

@Serializable
data class MgoIdentifier(
    val system: String? = null,
    val type: MgoCodeableConcept? = null,
    val use: String? = null,
    val value: String? = null,
)

@Serializable
enum class GpDiagnosticResultProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-DiagnosticResult")
    HTTPNictizNlFhirStructureDefinitionGpDiagnosticResult("http://nictiz.nl/fhir/StructureDefinition/gp-DiagnosticResult"),
}

@Serializable
data class MgoPeriod(
    val end: String? = null,
    val start: String? = null,
)

@Serializable
data class MgoDuration(
    val code: String? = null,
    val comparator: String? = null,
    val system: String? = null,
    val unit: String? = null,
    val value: Double? = null,
)

@Serializable
data class MgoRange(
    val high: MgoDuration? = null,
    val low: MgoDuration? = null,
)

@Serializable
data class GpEncounter(
    @SerialName("class")
    val gpEncounterClass: MgoCoding? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val participant: List<GpEncounterParticipant>? = null,
    val period: MgoPeriod? = null,
    val profile: GpEncounterProfile,
    val reason: List<MgoCodeableConcept>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val serviceProvider: MgoReference? = null,
)

@Serializable
data class GpEncounterParticipant(
    val individual: MgoReference? = null,
)

@Serializable
enum class GpEncounterProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-Encounter")
    HTTPNictizNlFhirStructureDefinitionGpEncounter("http://nictiz.nl/fhir/StructureDefinition/gp-Encounter"),
}

@Serializable
data class GpEncounterReport(
    val author: List<MgoReference>? = null,
    val date: String? = null,
    val encounter: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: MgoIdentifier? = null,
    val profile: GpEncounterReportProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val section: List<Section>? = null,
    val status: String? = null,
    val title: String? = null,
    val type: List<MgoCoding>? = null,
)

@Serializable
enum class GpEncounterReportProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-EncounterReport")
    HTTPNictizNlFhirStructureDefinitionGpEncounterReport("http://nictiz.nl/fhir/StructureDefinition/gp-EncounterReport"),
}

@Serializable
data class Section(
    val code: MgoCodeableConcept? = null,
    val entry: List<MgoReference>? = null,
)

@Serializable
data class GpJournalEntry(
    val code: MgoCodeableConcept? = null,
    val context: MgoReference? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    @SerialName("ICPC_E")
    val icpcE: IcpcE,
    @SerialName("ICPC_S")
    val icpcS: IcpcS,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val performer: List<MgoReference>? = null,
    val profile: GpJournalEntryProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val valueString: String? = null,
)

@Serializable
data class IcpcE(
    val valueCodeableConcept: MgoCodeableConcept? = null,
)

@Serializable
data class IcpcS(
    val valueCodeableConcept: MgoCodeableConcept? = null,
)

@Serializable
enum class GpJournalEntryProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-JournalEntry")
    HTTPNictizNlFhirStructureDefinitionGpJournalEntry("http://nictiz.nl/fhir/StructureDefinition/gp-JournalEntry"),
}

@Serializable
data class GpLaboratoryResult(
    val basedOn: List<MgoReference>? = null,
    val category: List<MgoCodeableConcept>? = null,
    val code: MgoCodeableConcept? = null,
    val comment: String? = null,
    val effective: Effective? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val interpretation: MgoCodeableConcept? = null,
    val method: MgoCodeableConcept? = null,
    val profile: GpLaboratoryResultProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val referenceRange: List<GpLaboratoryResultReferenceRange>? = null,
    val related: List<GpLaboratoryResultRelated>? = null,
    val resourceType: String? = null,
    val result: MgoDuration? = null,
    val specimen: MgoReference? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
sealed class Effective {
    class MgoPeriodValue(val value: MgoPeriod) : Effective()

    class StringValue(val value: String) : Effective()
}

@Serializable
enum class GpLaboratoryResultProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-LaboratoryResult")
    HTTPNictizNlFhirStructureDefinitionGpLaboratoryResult("http://nictiz.nl/fhir/StructureDefinition/gp-LaboratoryResult"),
}

@Serializable
data class GpLaboratoryResultReferenceRange(
    val high: MgoDuration? = null,
    val low: MgoDuration? = null,
)

@Serializable
data class GpLaboratoryResultRelated(
    val target: MgoReference? = null,
)

@Serializable
data class IheMhdMinimalDocumentReference(
    val author: List<MgoReference>? = null,
    @SerialName("class")
    val iheMhdMinimalDocumentReferenceClass: MgoCodeableConcept? = null,
    val content: IheMhdMinimalDocumentReferenceContent,
    val created: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val indexed: String? = null,
    val masterIdentifier: MgoIdentifier? = null,
    val profile: IheMhdMinimalDocumentReferenceProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val securityLabel: List<MgoCodeableConcept>? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val type: MgoCodeableConcept? = null,
)

@Serializable
data class IheMhdMinimalDocumentReferenceContent(
    val attachment: MgoAttachment? = null,
)

@Serializable
data class MgoAttachment(
    val contentType: String? = null,
    val creation: String? = null,
    val data: String? = null,
    val hash: String? = null,
    val language: String? = null,
    val size: Double? = null,
    val title: String? = null,
    val url: String? = null,
)

@Serializable
enum class IheMhdMinimalDocumentReferenceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/IHE.MHD.Minimal.DocumentReference")
    HTTPNictizNlFhirStructureDefinitionIHEMHDMinimalDocumentReference(
        "http://nictiz.nl/fhir/StructureDefinition/IHE.MHD.Minimal.DocumentReference",
    ),
}

@Serializable
data class MultipleGroupedValues(
    val display: List<List<String>>? = null,
    val label: String,
    val showEmpty: Boolean? = null,
    val type: MultipleGroupedValuesType,
)

@Serializable
enum class MultipleGroupedValuesType(val value: String) {
    @SerialName("MULTIPLE_GROUPED_VALUES")
    MultipleGroupedValues("MULTIPLE_GROUPED_VALUES"),
}

@Serializable
data class UIEntryValueMULTIPLEGROUPEDVALUESString(
    val display: List<List<String>>? = null,
    val label: String,
    val showEmpty: Boolean? = null,
    val type: MultipleGroupedValuesType,
)

@Serializable
data class MultipleValues(
    val display: List<String>? = null,
    val label: String,
    val showEmpty: Boolean? = null,
    val type: MultipleValuesType,
)

@Serializable
enum class MultipleValuesType(val value: String) {
    @SerialName("MULTIPLE_VALUES")
    MultipleValues("MULTIPLE_VALUES"),
}

@Serializable
data class UIEntryValueMULTIPLEVALUESString(
    val display: List<String>? = null,
    val label: String,
    val showEmpty: Boolean? = null,
    val type: MultipleValuesType,
)

@Serializable
data class NlCoreHealthProfessionalPractitioner(
    val address: List<NlCoreHealthProfessionalPractitionerAddress>? = null,
    val birthDate: String? = null,
    val communication: List<MgoCodeableConcept>? = null,
    val emailAddresses: List<EmailAddress>? = null,
    val fhirVersion: FhirVersionR4,
    val gender: String? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val name: List<NlCoreHealthProfessionalPractitionerName>? = null,
    val profile: NlCoreHealthProfessionalPractitionerProfile,
    val qualification: List<Qualification>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telephoneNumbers: List<TelephoneNumber>? = null,
)

@Serializable
data class NlCoreHealthProfessionalPractitionerAddress(
    val additionalInformation: String? = null,
    val addressType: MgoCodeableConcept? = null,
    val city: String? = null,
    val country: String? = null,
    val countryCode: MgoCodeableConcept? = null,
    val district: String? = null,
    val houseNumber: String? = null,
    val houseNumberAddition: String? = null,
    val houseNumberIndication: String? = null,
    val line: String? = null,
    val period: MgoPeriod? = null,
    val postalCode: String? = null,
    val streetName: String? = null,
)

@Serializable
data class EmailAddress(
    val system: EmailAddressSystem,
    val use: String? = null,
    val value: String? = null,
)

@Serializable
enum class EmailAddressSystem(val value: String) {
    @SerialName("email")
    Email("email"),
}

@Serializable
enum class FhirVersionR4(val value: String) {
    @SerialName("R4")
    R4("R4"),
}

@Serializable
data class NlCoreHealthProfessionalPractitionerName(
    val family: String? = null,
    val given: List<String>? = null,
    val givenInitials: List<String>? = null,
    val givenNames: List<String>? = null,
    val nameUsage: String? = null,
    val period: MgoPeriod? = null,
    val prefix: List<String>? = null,
    val suffix: List<String>? = null,
    val text: String? = null,
    val use: Use,
)

@Serializable
enum class Use(val value: String) {
    @SerialName("official")
    Official("official"),

    @SerialName("usual")
    Usual("usual"),
}

@Serializable
enum class NlCoreHealthProfessionalPractitionerProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthProfessional-Practitioner")
    HTTPNictizNlFhirStructureDefinitionNlCoreHealthProfessionalPractitioner(
        "http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthProfessional-Practitioner",
    ),
}

@Serializable
data class Qualification(
    val code: MgoCodeableConcept? = null,
    val identifier: List<MgoIdentifier>? = null,
    val issuer: MgoReference? = null,
    val period: MgoPeriod? = null,
)

@Serializable
data class TelephoneNumber(
    val comment: String? = null,
    val system: TelephoneNumberSystem,
    val telecomType: MgoCodeableConcept? = null,
    val use: String? = null,
    val value: String? = null,
)

@Serializable
enum class TelephoneNumberSystem(val value: String) {
    @SerialName("phone")
    Phone("phone"),
}

@Serializable
data class NlCoreObservation(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: NlCoreObservationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
)

@Serializable
enum class NlCoreObservationProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-observation")
    HTTPFhirNlFhirStructureDefinitionNlCoreObservation("http://fhir.nl/fhir/StructureDefinition/nl-core-observation"),
}

@Serializable
data class NlCoreOrganization(
    val address: List<NlCoreAddress>? = null,
    val departmentSpecialty: List<MgoCodeableConcept>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val name: String? = null,
    val organizationType: List<MgoCodeableConcept>? = null,
    val profile: NlCoreOrganizationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telecom: List<NlCoreContactpoint>? = null,
)

@Serializable
data class NlCoreAddress(
    val city: String? = null,
    val country: String? = null,
    val district: String? = null,
    val line: List<String>? = null,
    val period: MgoPeriod? = null,
    val postalCode: String? = null,
    val state: String? = null,
    val text: String? = null,
    val type: String? = null,
    val use: String? = null,
)

@Serializable
enum class NlCoreOrganizationProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-organization")
    HTTPFhirNlFhirStructureDefinitionNlCoreOrganization("http://fhir.nl/fhir/StructureDefinition/nl-core-organization"),
}

@Serializable
data class NlCoreContactpoint(
    val period: MgoPeriod? = null,
    val rank: Double? = null,
    val system: String? = null,
    val use: String? = null,
    val value: String? = null,
)

@Serializable
data class NlCorePatient(
    val active: Boolean? = null,
    val address: List<NlCoreAddress>? = null,
    val birthDate: String? = null,
    val communication: List<Communication>? = null,
    val contact: List<Contact>? = null,
    val deceased: Boolean? = null,
    val deceasedDateTime: String? = null,
    val fhirVersion: FhirVersionR3,
    val gender: String? = null,
    val generalPractitioner: List<MgoReference>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val link: List<Link>? = null,
    val managingOrganization: MgoReference? = null,
    val maritalStatus: MgoCodeableConcept? = null,
    val multipleBirth: Boolean? = null,
    val multipleBirthInteger: Double? = null,
    val name: List<NlCoreHumanname>? = null,
    val photo: List<MgoAttachment>? = null,
    val profile: NlCorePatientProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telecom: List<NlCoreContactpoint>? = null,
)

@Serializable
data class Communication(
    val language: MgoCodeableConcept? = null,
    val preferred: Boolean? = null,
)

@Serializable
data class Contact(
    val address: NlCoreAddress,
    val gender: String? = null,
    val name: NlCoreHumanname,
    val organization: MgoReference? = null,
    val period: MgoPeriod? = null,
    val relationship: List<MgoCodeableConcept>,
    val telecom: List<NlCoreContactpoint>,
)

@Serializable
data class NlCoreHumanname(
    val family: String? = null,
    val given: List<String>? = null,
    val period: MgoPeriod? = null,
    val prefix: List<String>? = null,
    val suffix: List<String>? = null,
    val text: String? = null,
    val use: String? = null,
)

@Serializable
data class Link(
    val other: MgoReference? = null,
    val type: String? = null,
)

@Serializable
enum class NlCorePatientProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-patient")
    HTTPFhirNlFhirStructureDefinitionNlCorePatient("http://fhir.nl/fhir/StructureDefinition/nl-core-patient"),
}

@Serializable
data class NlCorePatientR4(
    val address: List<NlCorePatientR4Address>? = null,
    val birthDate: String? = null,
    val deceased: Boolean? = null,
    val deceasedDateTime: String? = null,
    val fhirVersion: FhirVersionR4,
    val gender: String? = null,
    val generalPractitioner: List<MgoReference>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val managingOrganization: MgoReference? = null,
    val maritalStatus: MgoCodeableConcept? = null,
    val multipleBirth: Boolean? = null,
    val name: List<NlCorePatientR4Name>? = null,
    val profile: NlCorePatientR4Profile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
)

@Serializable
data class NlCorePatientR4Address(
    val additionalInformation: String? = null,
    val addressType: MgoCodeableConcept? = null,
    val city: String? = null,
    val country: String? = null,
    val countryCode: MgoCodeableConcept? = null,
    val district: String? = null,
    val houseNumber: String? = null,
    val houseNumberAddition: String? = null,
    val houseNumberIndication: String? = null,
    val line: String? = null,
    val period: MgoPeriod? = null,
    val postalCode: String? = null,
    val streetName: String? = null,
)

@Serializable
data class NlCorePatientR4Name(
    val family: String? = null,
    val given: List<String>? = null,
    val givenInitials: List<String>? = null,
    val givenNames: List<String>? = null,
    val nameUsage: String? = null,
    val period: MgoPeriod? = null,
    val prefix: List<String>? = null,
    val suffix: List<String>? = null,
    val text: String? = null,
    val use: Use,
)

@Serializable
enum class NlCorePatientR4Profile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-Patient")
    HTTPNictizNlFhirStructureDefinitionNlCorePatient("http://nictiz.nl/fhir/StructureDefinition/nl-core-Patient"),
}

@Serializable
data class NlCorePractitioner(
    val address: List<NlCoreAddress>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val name: List<NlCoreHumanname>? = null,
    val profile: NlCorePractitionerProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telecom: List<NlCoreContactpoint>? = null,
)

@Serializable
enum class NlCorePractitionerProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-practitioner")
    HTTPFhirNlFhirStructureDefinitionNlCorePractitioner("http://fhir.nl/fhir/StructureDefinition/nl-core-practitioner"),
}

@Serializable
data class NlCorePractitionerRole(
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val organization: MgoReference? = null,
    val profile: NlCorePractitionerRoleProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val specialty: List<MgoCodeableConcept>? = null,
    val telecom: List<NlCoreContactpoint>? = null,
)

@Serializable
enum class NlCorePractitionerRoleProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-practitionerrole")
    HTTPFhirNlFhirStructureDefinitionNlCorePractitionerrole("http://fhir.nl/fhir/StructureDefinition/nl-core-practitionerrole"),
}

@Serializable
data class NlCoreVaccinationEvent(
    val administrator: List<MgoReference>? = null,
    val doseQuantity: MgoDuration? = null,
    val fhirVersion: FhirVersionR4,
    val id: String? = null,
    val location: MgoReference? = null,
    val note: List<MgoAnnotation>? = null,
    val occurrenceDateTime: String? = null,
    val patient: MgoReference? = null,
    val pharmaceuticalProduct: MgoReference? = null,
    val profile: NlCoreVaccinationEventProfile,
    val protocolApplied: List<ProtocolApplied>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val route: MgoCodeableConcept? = null,
    val site: MgoCodeableConcept? = null,
    val status: String? = null,
    val vaccinationIndication: List<MgoCodeableConcept>? = null,
    val vaccinationMotive: List<MgoCodeableConcept>? = null,
    val vaccineCode: MgoCodeableConcept? = null,
)

@Serializable
data class MgoAnnotation(
    val author: MgoReference? = null,
    val text: String? = null,
    val time: String? = null,
)

@Serializable
enum class NlCoreVaccinationEventProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-Vaccination-event")
    HTTPNictizNlFhirStructureDefinitionNlCoreVaccinationEvent("http://nictiz.nl/fhir/StructureDefinition/nl-core-Vaccination-event"),
}

@Serializable
data class ProtocolApplied(
    val authority: MgoReference? = null,
    val doseNumberPositiveInt: Double? = null,
    val doseNumberString: String? = null,
    val seriesDosesPositiveInt: Double? = null,
    val seriesDosesString: String? = null,
    val targetDisease: List<MgoCodeableConcept>? = null,
)

@Serializable
data class ReferenceValue(
    val display: String? = null,
    val label: String,
    val reference: String? = null,
    val showEmpty: Boolean? = null,
    val type: ReferenceValueType,
)

@Serializable
enum class ReferenceValueType(val value: String) {
    @SerialName("REFERENCE_VALUE")
    ReferenceValue("REFERENCE_VALUE"),
}

@Serializable
data class UIEntryValueREFERENCEVALUEString(
    val display: String? = null,
    val label: String,
    val showEmpty: Boolean? = null,
    val type: ReferenceValueType,
)

@Serializable
data class SingleValue(
    val display: String? = null,
    val label: String,
    val showEmpty: Boolean? = null,
    val type: SingleValueType,
)

@Serializable
enum class SingleValueType(val value: String) {
    @SerialName("SINGLE_VALUE")
    SingleValue("SINGLE_VALUE"),
}

@Serializable
data class UIEntryValueSINGLEVALUEString(
    val display: String? = null,
    val label: String,
    val showEmpty: Boolean? = null,
    val type: SingleValueType,
)

@Serializable
@Parcelize
data class UISchema(
    val children: List<UISchemaGroup>,
    val label: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class UISchemaGroup(
    val children: List<UIEntry>,
    val label: String,
) : Parcelable

@Serializable
@Parcelize
data class UIEntry(
    @Serializable(with = UIEntryDisplaySerializer::class)
    val display: UIEntryDisplay? = null,
    val label: String,
    val showEmpty: Boolean? = null,
    val type: UIEntryType,
    val reference: String? = null,
    val url: String? = null,
) : Parcelable

@Serializable
@Parcelize
sealed class UIEntryDisplay : Parcelable {
    @Parcelize
    class StringValue(val value: String) : UIEntryDisplay()

    @Parcelize
    class UnionArrayValue(val value: List<DisplayElement>) : UIEntryDisplay()
}

@Serializable
@Parcelize
sealed class DisplayElement : Parcelable {
    @Parcelize
    class StringArrayValue(val value: List<String>) : DisplayElement()

    @Parcelize
    class StringValue(val value: String) : DisplayElement()
}

@Serializable
enum class UIEntryType(val value: String) {
    @SerialName("DOWNLOAD_LINK")
    DownloadLink("DOWNLOAD_LINK"),

    @SerialName("MULTIPLE_GROUPED_VALUES")
    MultipleGroupedValues("MULTIPLE_GROUPED_VALUES"),

    @SerialName("MULTIPLE_VALUES")
    MultipleValues("MULTIPLE_VALUES"),

    @SerialName("REFERENCE_VALUE")
    ReferenceValue("REFERENCE_VALUE"),

    @SerialName("SINGLE_VALUE")
    SingleValue("SINGLE_VALUE"),
}

@Serializable
data class ZibAdministrationAgreement(
    val additionalInformation: MgoCodeableConcept? = null,
    val agreementReason: String? = null,
    val authoredOn: String? = null,
    val category: MgoCodeableConcept? = null,
    val daysSupply: MgoDuration? = null,
    val dossageInstruction: List<ZibInstructionsForUse>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val medicationReference: MgoReference? = null,
    val medicationTreatment: MgoIdentifier? = null,
    val note: List<MgoAnnotation>? = null,
    val profile: ZibAdministrationAgreementProfile,
    val quantity: MgoDuration? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val repeatPeriodCyclicalSchedule: MgoDuration? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val stopType: MgoCodeableConcept? = null,
    val usageDuration: MgoDuration? = null,
)

@Serializable
data class ZibInstructionsForUse(
    val additionalInstruction: List<MgoCodeableConcept>? = null,
    val asNeeded: MgoCodeableConcept? = null,
    val doseQuantity: MgoDuration? = null,
    val doseRange: MgoRange? = null,
    val maxDosePerPeriod: MgoRatio? = null,
    val rateQuantity: MgoDuration? = null,
    val rateRange: MgoRange? = null,
    val rateRatio: MgoRatio? = null,
    val route: MgoCodeableConcept? = null,
    val sequence: Double? = null,
    val text: String? = null,
    val timing: ZibAdministrationSchedule,
)

@Serializable
data class MgoRatio(
    val denominator: MgoDuration? = null,
    val numerator: MgoDuration? = null,
)

@Serializable
data class ZibAdministrationSchedule(
    val repeat: Repeat,
)

@Serializable
data class Repeat(
    val boundsDuration: MgoDuration? = null,
    val boundsPeriod: MgoPeriod? = null,
    val boundsRange: MgoRange? = null,
    val dayOfWeek: List<String>? = null,
    val duration: Double? = null,
    val durationUnit: String? = null,
    val frequency: Double? = null,
    val frequencyMax: Double? = null,
    val period: Double? = null,
    val periodUnit: String? = null,
    val timeOfDay: List<String>? = null,
    @SerialName("when")
    val repeatWhen: List<String>? = null,
)

@Serializable
enum class ZibAdministrationAgreementProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement")
    HTTPNictizNlFhirStructureDefinitionZibAdministrationAgreement("http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement"),
}

@Serializable
data class ZibAdvanceDirective(
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val consentingParty: List<MgoReference>? = null,
    val dateTime: String? = null,
    val disorder: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val profile: ZibAdvanceDirectiveProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val source: Source,
)

@Serializable
enum class ZibAdvanceDirectiveProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AdvanceDirective")
    HTTPNictizNlFhirStructureDefinitionZibAdvanceDirective("http://nictiz.nl/fhir/StructureDefinition/zib-AdvanceDirective"),
}

@Serializable
data class Source(
    val attachment: MgoAttachment,
    val identifier: MgoIdentifier? = null,
    val reference: MgoReference? = null,
)

@Serializable
data class ZibAlcoholUse(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibAlcoholUseProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
)

@Serializable
enum class ZibAlcoholUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AlcoholUse")
    HTTPNictizNlFhirStructureDefinitionZibAlcoholUse("http://nictiz.nl/fhir/StructureDefinition/zib-AlcoholUse"),
}

@Serializable
data class ZibAlert(
    val author: MgoReference? = null,
    val category: MgoCodeableConcept? = null,
    val code: MgoCodeableConcept? = null,
    val encounter: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val period: MgoPeriod? = null,
    val profile: ZibAlertProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
enum class ZibAlertProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Alert")
    HTTPNictizNlFhirStructureDefinitionZibAlert("http://nictiz.nl/fhir/StructureDefinition/zib-Alert"),
}

@Serializable
data class ZibAllergyIntolerance(
    val category: List<String>? = null,
    val clinicalStatus: String? = null,
    val code: MgoCodeableConcept? = null,
    val criticality: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val patient: MgoReference? = null,
    val profile: ZibAllergyIntoleranceProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val type: String? = null,
    val verificationStatus: String? = null,
)

@Serializable
enum class ZibAllergyIntoleranceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AllergyIntolerance")
    HTTPNictizNlFhirStructureDefinitionZibAllergyIntolerance("http://nictiz.nl/fhir/StructureDefinition/zib-AllergyIntolerance"),
}

@Serializable
data class ZibBloodPressure(
    val averageBloodPressureLOINC: AverageBloodPressureLOINC,
    val averageBloodPressureSNOMED: AverageBloodPressureSNOMED,
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val cuffTypeLOINC: CuffTypeLOINC,
    val cuffTypeSNOMED: CuffTypeSNOMED,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val diastolicBP: DiastolicBP,
    val diastolicEndpoint: DiastolicEndpoint,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val positionLOINC: PositionLOINC,
    val positionSNOMED: PositionSNOMED,
    val profile: ZibBloodPressureProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val systolicBP: SystolicBP,
    val valueQuantity: MgoDuration? = null,
)

@Serializable
data class AverageBloodPressureLOINC(
    val valueQuantity: MgoDuration? = null,
)

@Serializable
data class AverageBloodPressureSNOMED(
    val valueQuantity: MgoDuration? = null,
)

@Serializable
data class CuffTypeLOINC(
    val valueCodeableConcept: MgoCodeableConcept? = null,
)

@Serializable
data class CuffTypeSNOMED(
    val valueCodeableConcept: MgoCodeableConcept? = null,
)

@Serializable
data class DiastolicBP(
    val valueQuantity: MgoDuration? = null,
)

@Serializable
data class DiastolicEndpoint(
    val valueCodeableConcept: MgoCodeableConcept? = null,
)

@Serializable
data class PositionLOINC(
    val valueCodeableConcept: MgoCodeableConcept? = null,
)

@Serializable
data class PositionSNOMED(
    val valueCodeableConcept: MgoCodeableConcept? = null,
)

@Serializable
enum class ZibBloodPressureProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-BloodPressure")
    HTTPNictizNlFhirStructureDefinitionZibBloodPressure("http://nictiz.nl/fhir/StructureDefinition/zib-BloodPressure"),
}

@Serializable
data class SystolicBP(
    val valueQuantity: MgoDuration? = null,
)

@Serializable
data class ZibBodyHeight(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibBodyHeightProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
)

@Serializable
enum class ZibBodyHeightProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-BodyHeight")
    HTTPNictizNlFhirStructureDefinitionZibBodyHeight("http://nictiz.nl/fhir/StructureDefinition/zib-BodyHeight"),
}

@Serializable
data class ZibBodyWeight(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val clothing: Clothing,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibBodyWeightProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
)

@Serializable
data class Clothing(
    val valueCodeableConcept: MgoCodeableConcept? = null,
)

@Serializable
enum class ZibBodyWeightProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-BodyWeight")
    HTTPNictizNlFhirStructureDefinitionZibBodyWeight("http://nictiz.nl/fhir/StructureDefinition/zib-BodyWeight"),
}

@Serializable
data class ZibDrugUse(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibDrugUseProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
)

@Serializable
enum class ZibDrugUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-DrugUse")
    HTTPNictizNlFhirStructureDefinitionZibDrugUse("http://nictiz.nl/fhir/StructureDefinition/zib-DrugUse"),
}

@Serializable
data class ZibEncounter(
    @SerialName("class")
    val zibEncounterClass: MgoCoding? = null,
    val diagnosis: List<Diagnosis>? = null,
    val fhirVersion: FhirVersionR3,
    val hospitalization: Hospitalization,
    val id: String? = null,
    val participant: List<ZibEncounterParticipant>? = null,
    val period: MgoPeriod? = null,
    val profile: ZibEncounterProfile,
    val reason: List<MgoCodeableConcept>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val serviceProvider: MgoReference? = null,
)

@Serializable
data class Diagnosis(
    val condition: MgoReference? = null,
    val rank: Double? = null,
    val role: MgoCodeableConcept? = null,
)

@Serializable
data class Hospitalization(
    val admitSource: MgoCodeableConcept? = null,
    val dischargeDisposition: MgoCodeableConcept? = null,
)

@Serializable
data class ZibEncounterParticipant(
    val individual: MgoReference? = null,
)

@Serializable
enum class ZibEncounterProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Encounter")
    HTTPNictizNlFhirStructureDefinitionZibEncounter("http://nictiz.nl/fhir/StructureDefinition/zib-Encounter"),
}

@Serializable
data class ZibFunctionalOrMentalStatus(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibFunctionalOrMentalStatusProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
)

@Serializable
enum class ZibFunctionalOrMentalStatusProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-FunctionalOrMentalStatus")
    HTTPNictizNlFhirStructureDefinitionZibFunctionalOrMentalStatus(
        "http://nictiz.nl/fhir/StructureDefinition/zib-FunctionalOrMentalStatus",
    ),
}

@Serializable
data class ZibLaboratoryTestResultObservation(
    val basedOn: List<MgoReference>? = null,
    val category: List<MgoCodeableConcept>? = null,
    val code: MgoCodeableConcept? = null,
    val comment: String? = null,
    val effective: Effective? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val interpretation: MgoCodeableConcept? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibLaboratoryTestResultObservationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val referenceRange: List<ZibLaboratoryTestResultObservationReferenceRange>? = null,
    val related: List<ZibLaboratoryTestResultObservationRelated>? = null,
    val resourceType: String? = null,
    val result: MgoDuration? = null,
    val specimen: MgoReference? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
enum class ZibLaboratoryTestResultObservationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Observation")
    HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultObservation(
        "http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Observation",
    ),
}

@Serializable
data class ZibLaboratoryTestResultObservationReferenceRange(
    val high: MgoDuration? = null,
    val low: MgoDuration? = null,
)

@Serializable
data class ZibLaboratoryTestResultObservationRelated(
    val target: MgoReference? = null,
)

@Serializable
data class ZibLaboratoryTestResultSpecimen(
    val collection: ZibLaboratoryTestResultSpecimenCollection,
    val container: List<ZibLaboratoryTestResultSpecimenContainer>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val note: List<MgoAnnotation>? = null,
    val profile: ZibLaboratoryTestResultSpecimenProfile,
    val receivedTime: String? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val subject: MgoReference? = null,
    val type: MgoCodeableConcept? = null,
)

@Serializable
data class ZibLaboratoryTestResultSpecimenCollection(
    val bodySite: PurpleBodySite,
    val collectedDateTime: String? = null,
    val collectedPeriod: MgoPeriod? = null,
    val method: MgoCodeableConcept? = null,
    val quantity: MgoDuration? = null,
)

@Serializable
data class PurpleBodySite(
    val laterality: MgoCodeableConcept? = null,
    val morphology: MgoCodeableConcept? = null,
    val value: MgoCodeableConcept? = null,
)

@Serializable
data class ZibLaboratoryTestResultSpecimenContainer(
    val identifier: List<MgoIdentifier>? = null,
    val type: MgoCodeableConcept? = null,
)

@Serializable
enum class ZibLaboratoryTestResultSpecimenProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Specimen")
    HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultSpecimen(
        "http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Specimen",
    ),
}

@Serializable
data class ZibLaboratoryTestResultSpecimenIsolate(
    val collection: ZibLaboratoryTestResultSpecimenIsolateCollection,
    val container: List<ZibLaboratoryTestResultSpecimenIsolateContainer>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val note: List<MgoAnnotation>? = null,
    val profile: ZibLaboratoryTestResultSpecimenIsolateProfile,
    val receivedTime: String? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val subject: MgoReference? = null,
    val type: MgoCodeableConcept? = null,
)

@Serializable
data class ZibLaboratoryTestResultSpecimenIsolateCollection(
    val bodySite: FluffyBodySite,
    val collectedDateTime: String? = null,
    val collectedPeriod: MgoPeriod? = null,
    val method: MgoCodeableConcept? = null,
    val quantity: MgoDuration? = null,
)

@Serializable
data class FluffyBodySite(
    val laterality: MgoCodeableConcept? = null,
    val morphology: MgoCodeableConcept? = null,
    val value: MgoCodeableConcept? = null,
)

@Serializable
data class ZibLaboratoryTestResultSpecimenIsolateContainer(
    val identifier: List<MgoIdentifier>? = null,
    val type: MgoCodeableConcept? = null,
)

@Serializable
enum class ZibLaboratoryTestResultSpecimenIsolateProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Specimen-Isolate")
    HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultSpecimenIsolate(
        "http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Specimen-Isolate",
    ),
}

@Serializable
data class ZibLaboratoryTestResultSubstance(
    val category: List<MgoCodeableConcept>? = null,
    val code: MgoCodeableConcept? = null,
    val description: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val profile: ZibLaboratoryTestResultSubstanceProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
)

@Serializable
enum class ZibLaboratoryTestResultSubstanceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Substance")
    HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultSubstance(
        "http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Substance",
    ),
}

@Serializable
data class ZibLivingSituation(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibLivingSituationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
)

@Serializable
enum class ZibLivingSituationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LivingSituation")
    HTTPNictizNlFhirStructureDefinitionZibLivingSituation("http://nictiz.nl/fhir/StructureDefinition/zib-LivingSituation"),
}

@Serializable
data class ZibMedicalDevice(
    val bodySite: MgoCodeableConcept? = null,
    val device: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val laterality: MgoCodeableConcept? = null,
    val note: List<MgoAnnotation>? = null,
    val organization: MgoReference? = null,
    val patient: MgoReference? = null,
    val practitioner: MgoReference? = null,
    val profile: ZibMedicalDeviceProfile,
    val reason: MgoReference? = null,
    val recordedOn: String? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val source: MgoReference? = null,
    val status: String? = null,
    val whenUsed: MgoPeriod? = null,
)

@Serializable
enum class ZibMedicalDeviceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDevice")
    HTTPNictizNlFhirStructureDefinitionZibMedicalDevice("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDevice"),
}

@Serializable
data class ZibMedicalDeviceProduct(
    val expirationDate: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val note: List<MgoAnnotation>? = null,
    val patient: MgoReference? = null,
    val profile: ZibMedicalDeviceProductProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
)

@Serializable
enum class ZibMedicalDeviceProductProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceProduct")
    HTTPNictizNlFhirStructureDefinitionZibMedicalDeviceProduct("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceProduct"),
}

@Serializable
data class ZibMedicalDeviceRequest(
    val codeCodeableConcept: MgoCodeableConcept? = null,
    val codeReference: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val intent: MgoCodeableConcept? = null,
    val occurrence: MgoPeriod? = null,
    val perfomer: MgoReference? = null,
    val profile: ZibMedicalDeviceRequestProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
enum class ZibMedicalDeviceRequestProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceRequest")
    HTTPNictizNlFhirStructureDefinitionZibMedicalDeviceRequest("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceRequest"),
}

@Serializable
data class ZibMedicationAgreement(
    val basedOn: List<MgoReference>? = null,
    val category: MgoCodeableConcept? = null,
    val definition: List<MgoReference>? = null,
    val dossageInstruction: List<ZibInstructionsForUse>? = null,
    val fhirVersion: FhirVersionR3,
    val groupIdentifier: MgoIdentifier? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val intent: String? = null,
    val medicationReference: MgoReference? = null,
    val medicationTreatment: MgoIdentifier? = null,
    val note: List<MgoAnnotation>? = null,
    val periodOfUse: MgoPeriod? = null,
    val priority: String? = null,
    val profile: ZibMedicationAgreementProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val repeatPeriodCyclicalSchedule: MgoDuration? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val stopType: MgoCodeableConcept? = null,
    val usageDuration: MgoDuration? = null,
)

@Serializable
enum class ZibMedicationAgreementProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationAgreement")
    HTTPNictizNlFhirStructureDefinitionZibMedicationAgreement("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationAgreement"),
}

@Serializable
data class ZibMedicationUse(
    val asAgreedIndicator: Boolean? = null,
    val author: MgoReference? = null,
    val category: MgoCodeableConcept? = null,
    val dateAsserted: String? = null,
    val dosage: List<ZibInstructionsForUse>? = null,
    val effectiveDuration: MgoDuration? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val informationSource: MgoReference? = null,
    val medicationReference: MgoReference? = null,
    val medicationTreatment: MgoIdentifier? = null,
    val note: List<MgoAnnotation>? = null,
    val prescriber: MgoReference? = null,
    val profile: ZibMedicationUseProfile,
    val reasonCode: List<MgoCodeableConcept>? = null,
    val reasonForChangeOrDiscontinuationOfUse: MgoCodeableConcept? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val repeatPeriodCyclicalSchedule: MgoDuration? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val taken: String? = null,
)

@Serializable
enum class ZibMedicationUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse")
    HTTPNictizNlFhirStructureDefinitionZibMedicationUse("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse"),
}

@Serializable
data class ZibNutritionAdvice(
    val comment: String? = null,
    val dateTime: String? = null,
    val fhirVersion: FhirVersionR3,
    val foodPreferenceModifier: List<MgoCodeableConcept>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val patient: MgoReference? = null,
    val profile: ZibNutritionAdviceProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
)

@Serializable
enum class ZibNutritionAdviceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-NutritionAdvice")
    HTTPNictizNlFhirStructureDefinitionZibNutritionAdvice("http://nictiz.nl/fhir/StructureDefinition/zib-NutritionAdvice"),
}

@Serializable
data class ZibPayer(
    val beneficiary: MgoReference? = null,
    val contract: List<MgoReference>? = null,
    val dependent: String? = null,
    val fhirVersion: FhirVersionR3,
    val grouping: Grouping,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val network: String? = null,
    val order: Double? = null,
    val payor: List<MgoReference>? = null,
    val period: MgoPeriod? = null,
    val policyHolder: MgoReference? = null,
    val profile: ZibPayerProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val relationship: MgoCodeableConcept? = null,
    val resourceType: String? = null,
    val sequence: String? = null,
    val status: String? = null,
    val subscriber: MgoReference? = null,
    @SerialName("subscriberId")
    val subscriberID: String? = null,
    val type: MgoCodeableConcept? = null,
)

@Serializable
data class Grouping(
    @SerialName("class")
    val groupingClass: String? = null,
    val classDisplay: String? = null,
    val group: String? = null,
    val groupDisplay: String? = null,
    val plan: String? = null,
    val planDisplay: String? = null,
    val subClass: String? = null,
    val subClassDisplay: String? = null,
    val subGroup: String? = null,
    val subGroupDisplay: String? = null,
    val subPlan: String? = null,
    val subPlanDisplay: String? = null,
)

@Serializable
enum class ZibPayerProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Payer")
    HTTPNictizNlFhirStructureDefinitionZibPayer("http://nictiz.nl/fhir/StructureDefinition/zib-Payer"),
}

@Serializable
data class ZibProblem(
    val abatementDateTime: String? = null,
    val assertedDate: String? = null,
    val asserter: MgoReference? = null,
    val bodySite: List<MgoCodeableConcept>? = null,
    val category: List<MgoCodeableConcept>? = null,
    val clinicalStatus: String? = null,
    val code: MgoCodeableConcept? = null,
    val context: MgoReference? = null,
    val evidence: List<Evidence>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val note: List<MgoAnnotation>? = null,
    val onsetDateTime: String? = null,
    val profile: ZibProblemProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val severity: MgoCodeableConcept? = null,
    val stage: Stage,
    val subject: MgoReference? = null,
    val verificationStatus: String? = null,
)

@Serializable
data class Evidence(
    val code: List<MgoCodeableConcept>? = null,
    val detail: List<MgoReference>? = null,
)

@Serializable
enum class ZibProblemProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Problem")
    HTTPNictizNlFhirStructureDefinitionZibProblem("http://nictiz.nl/fhir/StructureDefinition/zib-Problem"),
}

@Serializable
data class Stage(
    val assessment: List<MgoReference>? = null,
    val summary: MgoCodeableConcept? = null,
)

@Serializable
data class ZibProcedure(
    val bodySite: List<MgoCodeableConcept>? = null,
    val bodySiteQualifier: List<MgoCodeableConcept>? = null,
    val code: MgoCodeableConcept? = null,
    val fhirVersion: FhirVersionR3,
    val focalDevice: List<FocalDevice>? = null,
    val id: String? = null,
    val location: MgoReference? = null,
    val performedPeriod: MgoPeriod? = null,
    val performer: List<Performer>? = null,
    val procedureMethod: MgoCodeableConcept? = null,
    val profile: ZibProcedureProfile,
    val reasonReference: List<MgoReference>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
data class FocalDevice(
    val manipulated: MgoReference? = null,
)

@Serializable
data class Performer(
    val actor: MgoReference? = null,
)

@Serializable
enum class ZibProcedureProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Procedure")
    HTTPNictizNlFhirStructureDefinitionZibProcedure("http://nictiz.nl/fhir/StructureDefinition/zib-Procedure"),
}

@Serializable
data class ZibProcedureRequest(
    val code: MgoCodeableConcept? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val intent: String? = null,
    val occurrence: MgoPeriod? = null,
    val perfomer: MgoReference? = null,
    val profile: ZibProcedureRequestProfile,
    val reason: List<MgoReference>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
enum class ZibProcedureRequestProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-ProcedureRequest")
    HTTPNictizNlFhirStructureDefinitionZibProcedureRequest("http://nictiz.nl/fhir/StructureDefinition/zib-ProcedureRequest"),
}

@Serializable
data class ZibProduct(
    val code: MgoCodeableConcept? = null,
    val description: String? = null,
    val fhirVersion: FhirVersionR3,
    val form: MgoCodeableConcept? = null,
    val id: String? = null,
    val ingredient: List<ZibProductIngredient>? = null,
    @SerialName("package")
    val zibProductPackage: Package,
    val profile: ZibProductProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
)

@Serializable
data class ZibProductIngredient(
    val amount: MgoRatio? = null,
    val item: MgoCodeableConcept? = null,
)

@Serializable
enum class ZibProductProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Product")
    HTTPNictizNlFhirStructureDefinitionZibProduct("http://nictiz.nl/fhir/StructureDefinition/zib-Product"),
}

@Serializable
data class Package(
    val content: List<PackageContent>? = null,
)

@Serializable
data class PackageContent(
    val item: MgoCodeableConcept? = null,
    val reference: MgoReference? = null,
)

@Serializable
data class ZibProductPackage(
    val content: List<ZibProductPackageContent>? = null,
)

@Serializable
data class ZibProductPackageContent(
    val item: MgoCodeableConcept? = null,
    val reference: MgoReference? = null,
)

@Serializable
data class ZibTobaccoUse(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibTobaccoUseProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
)

@Serializable
enum class ZibTobaccoUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-TobaccoUse")
    HTTPNictizNlFhirStructureDefinitionZibTobaccoUse("http://nictiz.nl/fhir/StructureDefinition/zib-TobaccoUse"),
}

@Serializable
data class ZibTreatmentDirective(
    val action: List<MgoCodeableConcept>? = null,
    val actor: List<ZibTreatmentDirectiveActor>? = null,
    val category: List<MgoCodeableConcept>? = null,
    val consentingParty: List<MgoReference>? = null,
    val data: List<ZibTreatmentDirectiveDatum>? = null,
    val dataPeriod: MgoPeriod? = null,
    val dateTime: String? = null,
    val except: List<Except>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: MgoIdentifier? = null,
    val organization: List<MgoReference>? = null,
    val patient: MgoReference? = null,
    val period: MgoPeriod? = null,
    val policy: List<Policy>? = null,
    val policyRule: String? = null,
    val profile: ZibTreatmentDirectiveProfile,
    val purpose: List<MgoCoding>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val securityLabel: List<MgoCoding>? = null,
    val sourceAttachment: MgoAttachment,
    val sourceIdentifier: MgoIdentifier? = null,
    val sourceReference: MgoReference? = null,
    val status: String? = null,
)

@Serializable
data class ZibTreatmentDirectiveActor(
    val reference: MgoReference? = null,
    val role: MgoCodeableConcept? = null,
)

@Serializable
data class ZibTreatmentDirectiveDatum(
    val meaning: String? = null,
    val reference: MgoReference? = null,
)

@Serializable
data class Except(
    val action: List<MgoCodeableConcept>? = null,
    val actor: List<ExceptActor>? = null,
    @SerialName("class")
    val exceptClass: List<MgoCoding>? = null,
    val code: List<MgoCoding>? = null,
    val data: List<ExceptDatum>? = null,
    val dataPeriod: MgoPeriod? = null,
    val period: MgoPeriod? = null,
    val purpose: List<MgoCoding>? = null,
    val securityLabel: List<MgoCoding>? = null,
    val type: String? = null,
)

@Serializable
data class ExceptActor(
    val reference: MgoReference? = null,
    val role: MgoCodeableConcept? = null,
)

@Serializable
data class ExceptDatum(
    val meaning: String? = null,
    val reference: MgoReference? = null,
)

@Serializable
data class Policy(
    val authority: String? = null,
    val id: String? = null,
    val uri: String? = null,
)

@Serializable
enum class ZibTreatmentDirectiveProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-TreatmentDirective")
    HTTPNictizNlFhirStructureDefinitionZibTreatmentDirective("http://nictiz.nl/fhir/StructureDefinition/zib-TreatmentDirective"),
}

@Serializable
data class ZibVaccination(
    val dose: MgoDuration? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val note: List<MgoAnnotation>? = null,
    val patient: MgoReference? = null,
    val practitioner: List<Practitioner>? = null,
    val profile: ZibVaccinationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val vaccinationDate: String? = null,
    val vaccineCode: MgoCodeableConcept? = null,
)

@Serializable
data class Practitioner(
    val actor: MgoReference? = null,
)

@Serializable
enum class ZibVaccinationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Vaccination")
    HTTPNictizNlFhirStructureDefinitionZibVaccination("http://nictiz.nl/fhir/StructureDefinition/zib-Vaccination"),
}

@Serializable
data class ZibVaccinationRecommendation(
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val orderStatus: MgoCodeableConcept? = null,
    val profile: ZibVaccinationRecommendationProfile,
    val recommendation: List<Recommendation>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
)

@Serializable
enum class ZibVaccinationRecommendationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-VaccinationRecommendation")
    HTTPNictizNlFhirStructureDefinitionZibVaccinationRecommendation(
        "http://nictiz.nl/fhir/StructureDefinition/zib-VaccinationRecommendation",
    ),
}

@Serializable
data class Recommendation(
    val code: MgoCodeableConcept? = null,
    val date: String? = null,
    val dateCriterion: List<String>? = null,
)
