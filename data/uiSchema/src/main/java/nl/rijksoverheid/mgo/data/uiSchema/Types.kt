// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json                      = Json { allowStructuredMapKeys = true }
// val mgoAnnotation             = json.parse(MgoAnnotation.serializer(), jsonString)
// val mgoReference              = json.parse(MgoReference.serializer(), jsonString)
// val mgoBoolean                = json.parse(MgoBoolean.serializer(), jsonString)
// val mgoCode                   = json.parse(MgoCode.serializer(), jsonString)
// val mgoCodeableConcept        = json.parse(MgoCodeableConcept.serializer(), jsonString)
// val mgoCoding                 = json.parse(MgoCoding.serializer(), jsonString)
// val mgoDate                   = json.parse(MgoDate.serializer(), jsonString)
// val mgoDateTime               = json.parse(MgoDateTime.serializer(), jsonString)
// val mgoDecimal                = json.parse(MgoDecimal.serializer(), jsonString)
// val mgoDuration               = json.parse(MgoDuration.serializer(), jsonString)
// val mgoQuantity               = json.parse(MgoQuantity.serializer(), jsonString)
// val mgoIdentifier             = json.parse(MgoIdentifier.serializer(), jsonString)
// val mgoInteger                = json.parse(MgoInteger.serializer(), jsonString)
// val mgoInteger64              = json.parse(MgoInteger64.serializer(), jsonString)
// val mgoPeriod                 = json.parse(MgoPeriod.serializer(), jsonString)
// val mgoPositiveInt            = json.parse(MgoPositiveInt.serializer(), jsonString)
// val mgoRange                  = json.parse(MgoRange.serializer(), jsonString)
// val mgoRatio                  = json.parse(MgoRatio.serializer(), jsonString)
// val mgoString                 = json.parse(MgoString.serializer(), jsonString)
// val mgoUnsignedInt            = json.parse(MgoUnsignedInt.serializer(), jsonString)
// val multipleGroupValue        = json.parse(MultipleGroupValue.serializer(), jsonString)
// val valueOptions              = json.parse(ValueOptions.serializer(), jsonString)
// val multipleValue             = json.parse(MultipleValue.serializer(), jsonString)
// val reference                 = json.parse(Reference.serializer(), jsonString)
// val singleValue               = json.parse(SingleValue.serializer(), jsonString)
// val uISchema                  = json.parse(UISchema.serializer(), jsonString)
// val uISchemaGroup             = json.parse(UISchemaGroup.serializer(), jsonString)
// val valueDescription          = json.parse(ValueDescription.serializer(), jsonString)
// val zibAdministrationSchedule = json.parse(ZibAdministrationSchedule.serializer(), jsonString)
// val zibInstructionsForUse     = json.parse(ZibInstructionsForUse.serializer(), jsonString)
// val zibMedicationUse          = json.parse(ZibMedicationUse.serializer(), jsonString)
// val zibProductIngredient      = json.parse(ZibProductIngredient.serializer(), jsonString)
// val zibProductPackage         = json.parse(ZibProductPackage.serializer(), jsonString)

package nl.rijksoverheid.mgo.data.uiSchema

import kotlinx.serialization.*

typealias MgoBoolean = Boolean
typealias MgoCode = String
typealias MgoDate = String
typealias MgoDateTime = String
typealias MgoDecimal = Double
typealias MgoInteger = Double
typealias MgoInteger64 = Double
typealias MgoPositiveInt = Double
typealias MgoString = String
typealias MgoUnsignedInt = Double

@Serializable
data class MultipleGroupValue (
    val display: List<List<String>>? = null,
    val label: String,
    val summary: Boolean? = null,
    val type: String
)

@Serializable
data class ValueOptions (
    val summary: Boolean? = null
)

@Serializable
data class MultipleValue (
    val display: List<String>? = null,
    val label: String,
    val summary: Boolean? = null,
    val type: String
)

@Serializable
data class Reference (
    val display: String? = null,
    val label: String,
    val reference: String? = null,
    val summary: Boolean? = null,
    val type: String
)

@Serializable
data class SingleValue (
    val display: String? = null,
    val label: String,
    val summary: Boolean? = null,
    val type: String
)

@Serializable
data class UISchema (
    val children: List<UISchemaGroup>,
    val label: String? = null
)

@Serializable
data class UISchemaGroup (
    val children: List<ValueDescription>,
    val label: String
)

@Serializable
data class ValueDescription (
    val display: ValueDescriptionDisplay? = null,
    val label: String,
    val summary: Boolean? = null,
    val type: String,
    val reference: String? = null
)

@Serializable
sealed class ValueDescriptionDisplay {
    class StringValue(val value: String)                   : ValueDescriptionDisplay()
    class UnionArrayValue(val value: List<DisplayElement>) : ValueDescriptionDisplay()
}

@Serializable
sealed class DisplayElement {
    class StringArrayValue(val value: List<String>) : DisplayElement()
    class StringValue(val value: String)            : DisplayElement()
}

@Serializable
data class ZibMedicationUse (
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
    val profile: String,
    val reasonCode: List<List<MgoCoding>>? = null,
    val reasonForChangeOrDiscontinuationOfUse: List<MgoCoding>? = null,
    val repeatPeriodCyclicalSchedule: MgoQuantity? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val taken: String? = null
)

@Serializable
data class MgoReference (
    val display: String? = null,
    val reference: String? = null
)

@Serializable
data class MgoCoding (
    val code: String? = null,
    val display: String? = null,
    val system: String? = null
)

@Serializable
data class ZibInstructionsForUse (
    val additionalInstruction: List<List<MgoCoding>>? = null,
    val asNeeded: List<MgoCoding>? = null,
    val doseQuantity: MgoQuantity? = null,
    val doseRange: MgoRange? = null,
    val maxDosePerPeriod: MgoRatio? = null,
    val rateQuantity: MgoQuantity? = null,
    val rateRange: MgoRange? = null,
    val rateRatio: MgoRatio? = null,
    val timing: ZibAdministrationSchedule
)

@Serializable
data class MgoQuantity (
    val code: String? = null,
    val system: String? = null,
    val unit: String? = null,
    val value: Double? = null
)

@Serializable
data class MgoRange (
    val high: MgoQuantity? = null,
    val low: MgoQuantity? = null
)

@Serializable
data class MgoRatio (
    val denominator: MgoQuantity? = null,
    val numerator: MgoQuantity? = null
)

@Serializable
data class ZibAdministrationSchedule (
    val dayOfWeek: List<String>? = null,
    val duration: Double? = null,
    val durationUnit: String? = null,
    val frequency: Double? = null,
    val frequencyMax: Double? = null,
    val period: Double? = null,
    val periodUnit: String? = null,
    val timeOfDay: List<String>? = null,

    @SerialName("when")
    val zibAdministrationScheduleWhen: List<String>? = null
)

@Serializable
data class MgoPeriod (
    val end: String? = null,
    val start: String? = null
)

@Serializable
data class MgoIdentifier (
    val system: String? = null,
    val type: List<MgoCoding>? = null,
    val use: String? = null,
    val value: String? = null
)

@Serializable
data class MgoAnnotation (
    val author: MgoReference? = null,
    val text: String? = null,
    val time: String? = null
)

@Serializable
data class ZibProductIngredient (
    val amount: MgoRatio? = null,
    val item: List<MgoCoding>? = null
)

@Serializable
data class ZibProductPackage (
    val content: List<Content>? = null
)

@Serializable
data class Content (
    val item: List<MgoCoding>? = null,
    val reference: MgoReference? = null
)
