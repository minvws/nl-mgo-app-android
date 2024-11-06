// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json                        = Json { allowStructuredMapKeys = true }
// val mgoAnnotation               = json.parse(MgoAnnotation.serializer(), jsonString)
// val mgoReference                = json.parse(MgoReference.serializer(), jsonString)
// val mgoBoolean                  = json.parse(MgoBoolean.serializer(), jsonString)
// val mgoCode                     = json.parse(MgoCode.serializer(), jsonString)
// val mgoCodeableConcept          = json.parse(MgoCodeableConcept.serializer(), jsonString)
// val mgoCoding                   = json.parse(MgoCoding.serializer(), jsonString)
// val mgoDate                     = json.parse(MgoDate.serializer(), jsonString)
// val mgoDateTime                 = json.parse(MgoDateTime.serializer(), jsonString)
// val mgoDecimal                  = json.parse(MgoDecimal.serializer(), jsonString)
// val mgoDuration                 = json.parse(MgoDuration.serializer(), jsonString)
// val mgoQuantity                 = json.parse(MgoQuantity.serializer(), jsonString)
// val mgoIdentifier               = json.parse(MgoIdentifier.serializer(), jsonString)
// val mgoInteger                  = json.parse(MgoInteger.serializer(), jsonString)
// val mgoInteger64                = json.parse(MgoInteger64.serializer(), jsonString)
// val mgoPeriod                   = json.parse(MgoPeriod.serializer(), jsonString)
// val mgoPositiveInt              = json.parse(MgoPositiveInt.serializer(), jsonString)
// val mgoRange                    = json.parse(MgoRange.serializer(), jsonString)
// val mgoRatio                    = json.parse(MgoRatio.serializer(), jsonString)
// val mgoString                   = json.parse(MgoString.serializer(), jsonString)
// val mgoUnsignedInt              = json.parse(MgoUnsignedInt.serializer(), jsonString)
// val multipleGroupValue          = json.parse(MultipleGroupValue.serializer(), jsonString)
// val valueOptions                = json.parse(ValueOptions.serializer(), jsonString)
// val multipleValue               = json.parse(MultipleValue.serializer(), jsonString)
// val nlCoreAddress               = json.parse(NlCoreAddress.serializer(), jsonString)
// val nlCoreContactpoint          = json.parse(NlCoreContactpoint.serializer(), jsonString)
// val nlCoreHumanname             = json.parse(NlCoreHumanname.serializer(), jsonString)
// val nlCoreObservation           = json.parse(NlCoreObservation.serializer(), jsonString)
// val nlCorePatient               = json.parse(NlCorePatient.serializer(), jsonString)
// val referenceValue              = json.parse(ReferenceValue.serializer(), jsonString)
// val singleValue                 = json.parse(SingleValue.serializer(), jsonString)
// val uISchema                    = json.parse(UISchema.serializer(), jsonString)
// val uISchemaGroup               = json.parse(UISchemaGroup.serializer(), jsonString)
// val zibAdministrationAgreement  = json.parse(ZibAdministrationAgreement.serializer(), jsonString)
// val zibInstructionsForUse       = json.parse(ZibInstructionsForUse.serializer(), jsonString)
// val zibAdministrationSchedule   = json.parse(ZibAdministrationSchedule.serializer(), jsonString)
// val zibAlcoholUse               = json.parse(ZibAlcoholUse.serializer(), jsonString)
// val zibAlert                    = json.parse(ZibAlert.serializer(), jsonString)
// val zibAllergyIntolerance       = json.parse(ZibAllergyIntolerance.serializer(), jsonString)
// val zibDrugUse                  = json.parse(ZibDrugUse.serializer(), jsonString)
// val zibFunctionalOrMentalStatus = json.parse(ZibFunctionalOrMentalStatus.serializer(), jsonString)
// val zibLivingSituation          = json.parse(ZibLivingSituation.serializer(), jsonString)
// val zibMedicalDevice            = json.parse(ZibMedicalDevice.serializer(), jsonString)
// val zibMedicalDeviceProduct     = json.parse(ZibMedicalDeviceProduct.serializer(), jsonString)
// val zibMedicationAgreement      = json.parse(ZibMedicationAgreement.serializer(), jsonString)
// val zibMedicationUse            = json.parse(ZibMedicationUse.serializer(), jsonString)
// val zibNutritionAdvice          = json.parse(ZibNutritionAdvice.serializer(), jsonString)
// val zibPayer                    = json.parse(ZibPayer.serializer(), jsonString)
// val zibProblem                  = json.parse(ZibProblem.serializer(), jsonString)
// val zibProduct                  = json.parse(ZibProduct.serializer(), jsonString)
// val zibProductIngredient        = json.parse(ZibProductIngredient.serializer(), jsonString)
// val zibProductPackage           = json.parse(ZibProductPackage.serializer(), jsonString)
// val zibTobaccoUse               = json.parse(ZibTobaccoUse.serializer(), jsonString)
// val zibTreatmentDirective       = json.parse(ZibTreatmentDirective.serializer(), jsonString)

@file:Suppress("ktlint:standard:no-wildcard-imports")

package nl.rijksoverheid.mgo.data.uiSchema

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.*
import kotlinx.serialization.json.*

typealias MgoBoolean = Boolean
typealias MgoCode = String
typealias MgoCodeableConcept = JsonArray
typealias MgoDate = String
typealias MgoDateTime = String
typealias MgoDecimal = Double
typealias MgoInteger = Double
typealias MgoInteger64 = Double
typealias MgoPositiveInt = Double
typealias MgoString = String
typealias MgoUnsignedInt = Double

@Serializable
data class MultipleGroupValue(
    val display: List<List<String>>? = null,
    val label: String,
    val summary: Boolean? = null,
    val type: String,
)

@Serializable
data class ValueOptions(
    val summary: Boolean? = null,
)

@Serializable
data class MultipleValue(
    val display: List<String>? = null,
    val label: String,
    val summary: Boolean? = null,
    val type: String,
)

@Serializable
data class NlCoreObservation(
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val profile: NlCoreObservationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
data class MgoCoding(
    val code: String? = null,
    val display: String? = null,
    val system: String? = null,
)

@Serializable
data class MgoReference(
    val display: String? = null,
    val reference: String? = null,
)

@Serializable
data class MgoPeriod(
    val end: String? = null,
    val start: String? = null,
)

@Serializable
data class MgoIdentifier(
    val system: String? = null,
    val type: List<MgoCoding>? = null,
    val use: String? = null,
    val value: String? = null,
)

@Serializable
enum class NlCoreObservationProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-observation")
    HTTPFhirNlFhirStructureDefinitionNlCoreObservation("http://fhir.nl/fhir/StructureDefinition/nl-core-observation"),
}

@Serializable
data class NlCorePatient(
    val active: Boolean? = null,
    val address: List<NlCoreAddress>? = null,
    val birthDate: String? = null,
    val communication: List<Communication>? = null,
    val contact: List<Contact>? = null,
    val deceased: Boolean? = null,
    val deceasedDateTime: String? = null,
    val gender: String? = null,
    val generalPractitioner: List<MgoReference>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val link: List<Link>? = null,
    val managingOrganization: MgoReference? = null,
    val maritalStatus: List<MgoCoding>? = null,
    val multipleBirth: Boolean? = null,
    val multipleBirthInteger: Double? = null,
    val name: List<NlCoreHumanname>? = null,
    val photo: List<Photo>? = null,
    val profile: NlCorePatientProfile,
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
data class Communication(
    val language: List<MgoCoding>? = null,
    val preferred: Boolean? = null,
)

@Serializable
data class Contact(
    val address: NlCoreAddress? = null,
    val gender: String? = null,
    val name: NlCoreHumanname? = null,
    val organization: MgoReference? = null,
    val period: MgoPeriod? = null,
    val relationship: List<List<MgoCoding>>? = null,
    val telecom: List<NlCoreContactpoint>? = null,
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
data class NlCoreContactpoint(
    val period: MgoPeriod? = null,
    val rank: Double? = null,
    val system: String? = null,
    val use: String? = null,
    val value: String? = null,
)

@Serializable
data class Link(
    val other: MgoReference? = null,
    val type: String? = null,
)

@Serializable
data class Photo(
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
enum class NlCorePatientProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-patient")
    HTTPFhirNlFhirStructureDefinitionNlCorePatient("http://fhir.nl/fhir/StructureDefinition/nl-core-patient"),
}

@Serializable
data class ReferenceValue(
    val display: String? = null,
    val label: String,
    val reference: String? = null,
    val summary: Boolean? = null,
    val type: String,
)

@Serializable
data class SingleValue(
    val display: String? = null,
    val label: String,
    val summary: Boolean? = null,
    val type: String,
)

@Serializable
@Parcelize
data class UISchema(
    val label: String? = null,
    val children: List<UISchemaGroup>,
) : Parcelable

@Serializable
@Parcelize
data class TestUISchema(
    val label: String? = null,
) : Parcelable

@Parcelize
@Serializable
data class UISchemaGroup(
    val children: List<Value>,
    val label: String,
) : Parcelable

@Parcelize
@Serializable
data class Value(
    @Serializable(with = ChildDisplaySerializer::class)
    val display: ChildDisplay? = null,
    val label: String,
    val summary: Boolean? = null,
    val type: String,
    val reference: String? = null,
) : Parcelable

@Serializable
sealed class ChildDisplay : Parcelable {
    @Parcelize
    class StringValue(val value: String) : ChildDisplay()

    @Parcelize
    class UnionArrayValue(val value: List<DisplayElement>) : ChildDisplay()
}

@Serializable
sealed class DisplayElement : Parcelable {
    @Parcelize
    class StringArrayValue(val value: List<String>) : DisplayElement()

    @Parcelize
    class StringValue(val value: String) : DisplayElement()
}

@Serializable
data class ZibAdministrationAgreement(
    val additionalInformation: List<MgoCoding>? = null,
    val agreementReason: String? = null,
    val authoredOn: String? = null,
    val category: List<MgoCoding>? = null,
    val daysSupply: MgoQuantity? = null,
    val dossageInstruction: List<ZibInstructionsForUse>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val medicationReference: MgoReference? = null,
    val medicationTreatment: MgoIdentifier? = null,
    val note: List<MgoAnnotation>? = null,
    val profile: ZibAdministrationAgreementProfile,
    val quantity: MgoQuantity? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val repeatPeriodCyclicalSchedule: MgoQuantity? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val stopType: List<MgoCoding>? = null,
    val usageDuration: MgoQuantity? = null,
)

@Serializable
data class MgoQuantity(
    val code: String? = null,
    val comparator: String? = null,
    val system: String? = null,
    val unit: String? = null,
    val value: Double? = null,
)

@Serializable
data class ZibInstructionsForUse(
    val additionalInstruction: List<List<MgoCoding>>? = null,
    val asNeeded: List<MgoCoding>? = null,
    val doseQuantity: MgoQuantity? = null,
    val doseRange: MgoRange? = null,
    val maxDosePerPeriod: MgoRatio? = null,
    val rateQuantity: MgoQuantity? = null,
    val rateRange: MgoRange? = null,
    val rateRatio: MgoRatio? = null,
    val timing: ZibAdministrationSchedule,
)

@Serializable
data class MgoRange(
    val high: MgoQuantity? = null,
    val low: MgoQuantity? = null,
)

@Serializable
data class MgoRatio(
    val denominator: MgoQuantity? = null,
    val numerator: MgoQuantity? = null,
)

@Serializable
data class ZibAdministrationSchedule(
    val dayOfWeek: List<String>? = null,
    val duration: Double? = null,
    val durationUnit: String? = null,
    val frequency: Double? = null,
    val frequencyMax: Double? = null,
    val period: Double? = null,
    val periodUnit: String? = null,
    val timeOfDay: List<String>? = null,
    @SerialName("when")
    val zibAdministrationScheduleWhen: List<String>? = null,
)

@Serializable
data class MgoAnnotation(
    val author: MgoReference? = null,
    val text: String? = null,
    val time: String? = null,
)

@Serializable
enum class ZibAdministrationAgreementProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement")
    HTTPNictizNlFhirStructureDefinitionZibAdministrationAgreement("http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement"),
}

@Serializable
data class ZibAlcoholUse(
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val profile: ZibAlcoholUseProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
enum class ZibAlcoholUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AlcoholUse")
    HTTPNictizNlFhirStructureDefinitionZibAlcoholUse("http://nictiz.nl/fhir/StructureDefinition/zib-AlcoholUse"),
}

@Serializable
data class ZibAlert(
    val author: MgoReference? = null,
    val category: List<MgoCoding>? = null,
    val code: List<MgoCoding>? = null,
    val encounter: MgoReference? = null,
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
    val code: List<MgoCoding>? = null,
    val criticality: String? = null,
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
data class ZibDrugUse(
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val profile: ZibDrugUseProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
enum class ZibDrugUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-DrugUse")
    HTTPNictizNlFhirStructureDefinitionZibDrugUse("http://nictiz.nl/fhir/StructureDefinition/zib-DrugUse"),
}

@Serializable
data class ZibFunctionalOrMentalStatus(
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val profile: ZibFunctionalOrMentalStatusProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
enum class ZibFunctionalOrMentalStatusProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-FunctionalOrMentalStatus")
    HTTPNictizNlFhirStructureDefinitionZibFunctionalOrMentalStatus(
        "http://nictiz.nl/fhir/StructureDefinition/zib-FunctionalOrMentalStatus",
    ),
}

@Serializable
data class ZibLivingSituation(
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val profile: ZibLivingSituationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
enum class ZibLivingSituationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LivingSituation")
    HTTPNictizNlFhirStructureDefinitionZibLivingSituation("http://nictiz.nl/fhir/StructureDefinition/zib-LivingSituation"),
}

@Serializable
data class ZibMedicalDevice(
    val bodySite: List<MgoCoding>? = null,
    val device: MgoReference? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val laterality: List<MgoCoding>? = null,
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
data class ZibMedicationAgreement(
    val basedOn: List<MgoReference>? = null,
    val category: List<MgoCoding>? = null,
    val definition: List<MgoReference>? = null,
    val dossageInstruction: List<ZibInstructionsForUse>? = null,
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
    val repeatPeriodCyclicalSchedule: MgoQuantity? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val stopType: List<MgoCoding>? = null,
    val usageDuration: MgoQuantity? = null,
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
    val category: List<MgoCoding>? = null,
    val dateAsserted: String? = null,
    val dosage: List<ZibInstructionsForUse>? = null,
    val effectiveDuration: MgoQuantity? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val informationSource: MgoReference? = null,
    val medication: MgoReference? = null,
    val medicationTreatment: MgoIdentifier? = null,
    val note: List<MgoAnnotation>? = null,
    val prescriber: MgoReference? = null,
    val profile: ZibMedicationUseProfile,
    val reasonCode: List<List<MgoCoding>>? = null,
    val reasonForChangeOrDiscontinuationOfUse: List<MgoCoding>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val repeatPeriodCyclicalSchedule: MgoQuantity? = null,
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
    val foodPreferenceModifier: List<List<MgoCoding>>? = null,
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
    val relationship: List<MgoCoding>? = null,
    val resourceType: String? = null,
    val sequence: String? = null,
    val status: String? = null,
    val subscriber: MgoReference? = null,
    @SerialName("subscriberId")
    val subscriberID: String? = null,
    val type: List<MgoCoding>? = null,
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
    val bodySite: List<List<MgoCoding>>? = null,
    val category: List<List<MgoCoding>>? = null,
    val clinicalStatus: String? = null,
    val code: List<MgoCoding>? = null,
    val context: MgoReference? = null,
    val evidence: List<Evidence>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val note: List<MgoAnnotation>? = null,
    val onsetDateTime: String? = null,
    val profile: ZibProblemProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val severity: List<MgoCoding>? = null,
    val stage: Stage,
    val subject: MgoReference? = null,
    val verificationStatus: String? = null,
)

@Serializable
data class Evidence(
    val code: List<List<MgoCoding>>? = null,
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
    val summary: List<MgoCoding>? = null,
)

@Serializable
data class ZibProduct(
    val code: List<MgoCoding>? = null,
    val description: String? = null,
    val form: List<MgoCoding>? = null,
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
    val item: List<MgoCoding>? = null,
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
    val item: List<MgoCoding>? = null,
    val reference: MgoReference? = null,
)

@Serializable
data class ZibProductPackage(
    val content: List<ZibProductPackageContent>? = null,
)

@Serializable
data class ZibProductPackageContent(
    val item: List<MgoCoding>? = null,
    val reference: MgoReference? = null,
)

@Serializable
data class ZibTobaccoUse(
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val profile: ZibTobaccoUseProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
)

@Serializable
enum class ZibTobaccoUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-TobaccoUse")
    HTTPNictizNlFhirStructureDefinitionZibTobaccoUse("http://nictiz.nl/fhir/StructureDefinition/zib-TobaccoUse"),
}

@Serializable
data class ZibTreatmentDirective(
    val action: List<List<MgoCoding>>? = null,
    val actor: List<ZibTreatmentDirectiveActor>? = null,
    val category: List<List<MgoCoding>>? = null,
    val consentingParty: List<MgoReference>? = null,
    val data: List<ZibTreatmentDirectiveDatum>? = null,
    val dataPeriod: MgoPeriod? = null,
    val dateTime: String? = null,
    val except: List<Except>? = null,
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
    val sourceAttachment: SourceAttachment,
    val sourceIdentifier: MgoIdentifier? = null,
    val sourceReference: MgoReference? = null,
    val status: String? = null,
)

@Serializable
data class ZibTreatmentDirectiveActor(
    val reference: MgoReference? = null,
    val role: List<MgoCoding>? = null,
)

@Serializable
data class ZibTreatmentDirectiveDatum(
    val meaning: String? = null,
    val reference: MgoReference? = null,
)

@Serializable
data class Except(
    val action: List<List<MgoCoding>>? = null,
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
    val role: List<MgoCoding>? = null,
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
data class SourceAttachment(
    val contentType: String? = null,
    val creation: String? = null,
    val data: String? = null,
    val hash: String? = null,
    val language: String? = null,
    val size: Double? = null,
    val title: String? = null,
    val url: String? = null,
)
