@file:Suppress("ktlint")

// To parse the JSON, install jackson-module-kotlin and do:
//
//   val mgoAnnotation = MgoAnnotation.fromJson(jsonString)
//   val mgoReference = MgoReference.fromJson(jsonString)
//   val mgoBoolean = MgoBoolean.fromJson(jsonString)
//   val mgoCode = MgoCode.fromJson(jsonString)
//   val mgoCodeableConcept = MgoCodeableConcept.fromJson(jsonString)
//   val mgoCoding = MgoCoding.fromJson(jsonString)
//   val mgoDate = MgoDate.fromJson(jsonString)
//   val mgoDateTime = MgoDateTime.fromJson(jsonString)
//   val mgoDecimal = MgoDecimal.fromJson(jsonString)
//   val mgoDuration = MgoDuration.fromJson(jsonString)
//   val mgoQuantity = MgoQuantity.fromJson(jsonString)
//   val mgoIdentifier = MgoIdentifier.fromJson(jsonString)
//   val mgoInteger = MgoInteger.fromJson(jsonString)
//   val mgoInteger64 = MgoInteger64.fromJson(jsonString)
//   val mgoPeriod = MgoPeriod.fromJson(jsonString)
//   val mgoPositiveInt = MgoPositiveInt.fromJson(jsonString)
//   val mgoRange = MgoRange.fromJson(jsonString)
//   val mgoRatio = MgoRatio.fromJson(jsonString)
//   val mgoString = MgoString.fromJson(jsonString)
//   val mgoUnsignedInt = MgoUnsignedInt.fromJson(jsonString)
//   val multipleGroupValue = MultipleGroupValue.fromJson(jsonString)
//   val valueOptions = ValueOptions.fromJson(jsonString)
//   val multipleValue = MultipleValue.fromJson(jsonString)
//   val nlCoreAddress = NlCoreAddress.fromJson(jsonString)
//   val nlCoreContactpoint = NlCoreContactpoint.fromJson(jsonString)
//   val nlCoreHumanname = NlCoreHumanname.fromJson(jsonString)
//   val nlCoreObservation = NlCoreObservation.fromJson(jsonString)
//   val nlCorePatient = NlCorePatient.fromJson(jsonString)
//   val referenceValue = ReferenceValue.fromJson(jsonString)
//   val singleValue = SingleValue.fromJson(jsonString)
//   val uISchema = UISchema.fromJson(jsonString)
//   val uISchemaGroup = UISchemaGroup.fromJson(jsonString)
//   val zibAdministrationAgreement = ZibAdministrationAgreement.fromJson(jsonString)
//   val zibInstructionsForUse = ZibInstructionsForUse.fromJson(jsonString)
//   val zibAdministrationSchedule = ZibAdministrationSchedule.fromJson(jsonString)
//   val zibAlcoholUse = ZibAlcoholUse.fromJson(jsonString)
//   val zibAlert = ZibAlert.fromJson(jsonString)
//   val zibAllergyIntolerance = ZibAllergyIntolerance.fromJson(jsonString)
//   val zibDrugUse = ZibDrugUse.fromJson(jsonString)
//   val zibFunctionalOrMentalStatus = ZibFunctionalOrMentalStatus.fromJson(jsonString)
//   val zibLivingSituation = ZibLivingSituation.fromJson(jsonString)
//   val zibMedicalDevice = ZibMedicalDevice.fromJson(jsonString)
//   val zibMedicalDeviceProduct = ZibMedicalDeviceProduct.fromJson(jsonString)
//   val zibMedicationAgreement = ZibMedicationAgreement.fromJson(jsonString)
//   val zibMedicationUse = ZibMedicationUse.fromJson(jsonString)
//   val zibNutritionAdvice = ZibNutritionAdvice.fromJson(jsonString)
//   val zibPayer = ZibPayer.fromJson(jsonString)
//   val zibProblem = ZibProblem.fromJson(jsonString)
//   val zibProduct = ZibProduct.fromJson(jsonString)
//   val zibProductIngredient = ZibProductIngredient.fromJson(jsonString)
//   val zibProductPackage = ZibProductPackage.fromJson(jsonString)
//   val zibTobaccoUse = ZibTobaccoUse.fromJson(jsonString)
//   val zibTreatmentDirective = ZibTreatmentDirective.fromJson(jsonString)

package nl.rijksoverheid.mgo.data.uiSchema

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.core.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.*
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.*


@Suppress("UNCHECKED_CAST")
private fun <T> ObjectMapper.convert(k: kotlin.reflect.KClass<*>, fromJson: (JsonNode) -> T, toJson: (T) -> String, isUnion: Boolean = false) = registerModule(SimpleModule().apply {
    addSerializer(k.java as Class<T>, object : StdSerializer<T>(k.java as Class<T>) {
        override fun serialize(value: T, gen: JsonGenerator, provider: SerializerProvider) = gen.writeRawValue(toJson(value))
    })
    addDeserializer(k.java as Class<T>, object : StdDeserializer<T>(k.java as Class<T>) {
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext) = fromJson(p.readValueAsTree())
    })
})

val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    convert(NlCoreObservationProfile::class,           { NlCoreObservationProfile.fromValue(it.asText()) },           { "\"${it.value}\"" })
    convert(NlCorePatientProfile::class,               { NlCorePatientProfile.fromValue(it.asText()) },               { "\"${it.value}\"" })
    convert(ZibAdministrationAgreementProfile::class,  { ZibAdministrationAgreementProfile.fromValue(it.asText()) },  { "\"${it.value}\"" })
    convert(ZibAlcoholUseProfile::class,               { ZibAlcoholUseProfile.fromValue(it.asText()) },               { "\"${it.value}\"" })
    convert(ZibAlertProfile::class,                    { ZibAlertProfile.fromValue(it.asText()) },                    { "\"${it.value}\"" })
    convert(ZibAllergyIntoleranceProfile::class,       { ZibAllergyIntoleranceProfile.fromValue(it.asText()) },       { "\"${it.value}\"" })
    convert(ZibDrugUseProfile::class,                  { ZibDrugUseProfile.fromValue(it.asText()) },                  { "\"${it.value}\"" })
    convert(ZibFunctionalOrMentalStatusProfile::class, { ZibFunctionalOrMentalStatusProfile.fromValue(it.asText()) }, { "\"${it.value}\"" })
    convert(ZibLivingSituationProfile::class,          { ZibLivingSituationProfile.fromValue(it.asText()) },          { "\"${it.value}\"" })
    convert(ZibMedicalDeviceProfile::class,            { ZibMedicalDeviceProfile.fromValue(it.asText()) },            { "\"${it.value}\"" })
    convert(ZibMedicalDeviceProductProfile::class,     { ZibMedicalDeviceProductProfile.fromValue(it.asText()) },     { "\"${it.value}\"" })
    convert(ZibMedicationAgreementProfile::class,      { ZibMedicationAgreementProfile.fromValue(it.asText()) },      { "\"${it.value}\"" })
    convert(ZibMedicationUseProfile::class,            { ZibMedicationUseProfile.fromValue(it.asText()) },            { "\"${it.value}\"" })
    convert(ZibNutritionAdviceProfile::class,          { ZibNutritionAdviceProfile.fromValue(it.asText()) },          { "\"${it.value}\"" })
    convert(ZibPayerProfile::class,                    { ZibPayerProfile.fromValue(it.asText()) },                    { "\"${it.value}\"" })
    convert(ZibProblemProfile::class,                  { ZibProblemProfile.fromValue(it.asText()) },                  { "\"${it.value}\"" })
    convert(ZibProductProfile::class,                  { ZibProductProfile.fromValue(it.asText()) },                  { "\"${it.value}\"" })
    convert(ZibTobaccoUseProfile::class,               { ZibTobaccoUseProfile.fromValue(it.asText()) },               { "\"${it.value}\"" })
    convert(ZibTreatmentDirectiveProfile::class,       { ZibTreatmentDirectiveProfile.fromValue(it.asText()) },       { "\"${it.value}\"" })
    convert(ChildDisplay::class,                       { ChildDisplay.fromJson(it) },                                 { it.toJson() }, true)
    convert(DisplayElement::class,                     { DisplayElement.fromJson(it) },                               { it.toJson() }, true)
}

typealias MgoBoolean = Boolean
typealias MgoCode = String
class MgoCodeableConcept(elements: Collection<MgoCoding>) : ArrayList<MgoCoding>(elements) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoCodeableConcept>(json)
    }
}
typealias MgoDate = String
typealias MgoDateTime = String
typealias MgoDecimal = Double
typealias MgoInteger = Double
typealias MgoInteger64 = Double
typealias MgoPositiveInt = Double
typealias MgoString = String
typealias MgoUnsignedInt = Double

data class MultipleGroupValue (
    val display: List<List<String>>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val label: String,

    val summary: Boolean? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val type: String
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MultipleGroupValue>(json)
    }
}

data class ValueOptions (
    val summary: Boolean? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ValueOptions>(json)
    }
}

data class MultipleValue (
    val display: List<String>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val label: String,

    val summary: Boolean? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val type: String
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MultipleValue>(json)
    }
}

data class NlCoreObservation (
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: NlCoreObservationProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<NlCoreObservation>(json)
    }
}

data class MgoCoding (
    val code: String? = null,
    val display: String? = null,
    val system: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoCoding>(json)
    }
}

data class MgoReference (
    val display: String? = null,
    val reference: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoReference>(json)
    }
}

data class MgoPeriod (
    val end: String? = null,
    val start: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoPeriod>(json)
    }
}

data class MgoIdentifier (
    val system: String? = null,
    val type: List<MgoCoding>? = null,
    val use: String? = null,
    val value: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoIdentifier>(json)
    }
}

enum class NlCoreObservationProfile(val value: String) {
    HTTPFhirNlFhirStructureDefinitionNlCoreObservation("http://fhir.nl/fhir/StructureDefinition/nl-core-observation");

    companion object {
        fun fromValue(value: String): NlCoreObservationProfile = when (value) {
            "http://fhir.nl/fhir/StructureDefinition/nl-core-observation" -> HTTPFhirNlFhirStructureDefinitionNlCoreObservation
            else                                                          -> throw IllegalArgumentException()
        }
    }
}

data class NlCorePatient (
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

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: NlCorePatientProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val telecom: List<NlCoreContactpoint>? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<NlCorePatient>(json)
    }
}

data class NlCoreAddress (
    val city: String? = null,
    val country: String? = null,
    val district: String? = null,
    val line: List<String>? = null,
    val period: MgoPeriod? = null,
    val postalCode: String? = null,
    val state: String? = null,
    val text: String? = null,
    val type: String? = null,
    val use: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<NlCoreAddress>(json)
    }
}

data class Communication (
    val language: List<MgoCoding>? = null,
    val preferred: Boolean? = null
)

data class Contact (
    val address: NlCoreAddress? = null,
    val gender: String? = null,
    val name: NlCoreHumanname? = null,
    val organization: MgoReference? = null,
    val period: MgoPeriod? = null,
    val relationship: List<List<MgoCoding>>? = null,
    val telecom: List<NlCoreContactpoint>? = null
)

data class NlCoreHumanname (
    val family: String? = null,
    val given: List<String>? = null,
    val period: MgoPeriod? = null,
    val prefix: List<String>? = null,
    val suffix: List<String>? = null,
    val text: String? = null,
    val use: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<NlCoreHumanname>(json)
    }
}

data class NlCoreContactpoint (
    val period: MgoPeriod? = null,
    val rank: Double? = null,
    val system: String? = null,
    val use: String? = null,
    val value: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<NlCoreContactpoint>(json)
    }
}

data class Link (
    val other: MgoReference? = null,
    val type: String? = null
)

data class Photo (
    val contentType: String? = null,
    val creation: String? = null,
    val data: String? = null,
    val hash: String? = null,
    val language: String? = null,
    val size: Double? = null,
    val title: String? = null,
    val url: String? = null
)

enum class NlCorePatientProfile(val value: String) {
    HTTPFhirNlFhirStructureDefinitionNlCorePatient("http://fhir.nl/fhir/StructureDefinition/nl-core-patient");

    companion object {
        fun fromValue(value: String): NlCorePatientProfile = when (value) {
            "http://fhir.nl/fhir/StructureDefinition/nl-core-patient" -> HTTPFhirNlFhirStructureDefinitionNlCorePatient
            else                                                      -> throw IllegalArgumentException()
        }
    }
}

data class ReferenceValue (
    val display: String? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val label: String,

    val reference: String? = null,
    val summary: Boolean? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val type: String
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ReferenceValue>(json)
    }
}

data class SingleValue (
    val display: String? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val label: String,

    val summary: Boolean? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val type: String
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<SingleValue>(json)
    }
}

data class UISchema (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val children: List<UISchemaGroup>,

    val label: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<UISchema>(json)
    }
}

data class UISchemaGroup (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val children: List<Value>,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val label: String
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<UISchemaGroup>(json)
    }
}

data class Value (
    val display: ChildDisplay? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val label: String,

    val summary: Boolean? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val type: String,

    val reference: String? = null
)

sealed class ChildDisplay {
    class StringValue(val value: String)                   : ChildDisplay()
    class UnionArrayValue(val value: List<DisplayElement>) : ChildDisplay()

    fun toJson(): String = mapper.writeValueAsString(when (this) {
        is StringValue -> this.value
        is UnionArrayValue -> this.value
    })

    companion object {
        fun fromJson(jn: JsonNode): ChildDisplay = when (jn) {
            is TextNode  -> StringValue(mapper.treeToValue(jn))
            is ArrayNode -> UnionArrayValue(mapper.treeToValue(jn))
            else         -> throw IllegalArgumentException()
        }
    }
}

sealed class DisplayElement {
    class StringArrayValue(val value: List<String>) : DisplayElement()
    class StringValue(val value: String)            : DisplayElement()

    fun toJson(): String = mapper.writeValueAsString(when (this) {
        is StringArrayValue -> this.value
        is StringValue -> this.value
    })

    companion object {
        fun fromJson(jn: JsonNode): DisplayElement = when (jn) {
            is ArrayNode -> StringArrayValue(mapper.treeToValue(jn))
            is TextNode  -> StringValue(mapper.treeToValue(jn))
            else         -> throw IllegalArgumentException()
        }
    }
}

data class ZibAdministrationAgreement (
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

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibAdministrationAgreementProfile,

    val quantity: MgoQuantity? = null,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val repeatPeriodCyclicalSchedule: MgoQuantity? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val stopType: List<MgoCoding>? = null,
    val usageDuration: MgoQuantity? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibAdministrationAgreement>(json)
    }
}

data class MgoQuantity (
    val code: String? = null,
    val comparator: String? = null,
    val system: String? = null,
    val unit: String? = null,
    val value: Double? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoQuantity>(json)
    }
}

data class ZibInstructionsForUse (
    val additionalInstruction: List<List<MgoCoding>>? = null,
    val asNeeded: List<MgoCoding>? = null,
    val doseQuantity: MgoQuantity? = null,
    val doseRange: MgoRange? = null,
    val maxDosePerPeriod: MgoRatio? = null,
    val rateQuantity: MgoQuantity? = null,
    val rateRange: MgoRange? = null,
    val rateRatio: MgoRatio? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val timing: ZibAdministrationSchedule
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibInstructionsForUse>(json)
    }
}

data class MgoRange (
    val high: MgoQuantity? = null,
    val low: MgoQuantity? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoRange>(json)
    }
}

data class MgoRatio (
    val denominator: MgoQuantity? = null,
    val numerator: MgoQuantity? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoRatio>(json)
    }
}

data class ZibAdministrationSchedule (
    val dayOfWeek: List<String>? = null,
    val duration: Double? = null,
    val durationUnit: String? = null,
    val frequency: Double? = null,
    val frequencyMax: Double? = null,
    val period: Double? = null,
    val periodUnit: String? = null,
    val timeOfDay: List<String>? = null,

    @get:JsonProperty("when")@field:JsonProperty("when")
    val zibAdministrationScheduleWhen: List<String>? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibAdministrationSchedule>(json)
    }
}

data class MgoAnnotation (
    val author: MgoReference? = null,
    val text: String? = null,
    val time: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoAnnotation>(json)
    }
}

enum class ZibAdministrationAgreementProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibAdministrationAgreement("http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement");

    companion object {
        fun fromValue(value: String): ZibAdministrationAgreementProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement" -> HTTPNictizNlFhirStructureDefinitionZibAdministrationAgreement
            else                                                                    -> throw IllegalArgumentException()
        }
    }
}

data class ZibAlcoholUse (
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibAlcoholUseProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibAlcoholUse>(json)
    }
}

enum class ZibAlcoholUseProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibAlcoholUse("http://nictiz.nl/fhir/StructureDefinition/zib-AlcoholUse");

    companion object {
        fun fromValue(value: String): ZibAlcoholUseProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-AlcoholUse" -> HTTPNictizNlFhirStructureDefinitionZibAlcoholUse
            else                                                       -> throw IllegalArgumentException()
        }
    }
}

data class ZibAlert (
    val author: MgoReference? = null,
    val category: List<MgoCoding>? = null,
    val code: List<MgoCoding>? = null,
    val encounter: MgoReference? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val period: MgoPeriod? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibAlertProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibAlert>(json)
    }
}

enum class ZibAlertProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibAlert("http://nictiz.nl/fhir/StructureDefinition/zib-Alert");

    companion object {
        fun fromValue(value: String): ZibAlertProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-Alert" -> HTTPNictizNlFhirStructureDefinitionZibAlert
            else                                                  -> throw IllegalArgumentException()
        }
    }
}

data class ZibAllergyIntolerance (
    val category: List<String>? = null,
    val clinicalStatus: String? = null,
    val code: List<MgoCoding>? = null,
    val criticality: String? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val patient: MgoReference? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibAllergyIntoleranceProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val type: String? = null,
    val verificationStatus: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibAllergyIntolerance>(json)
    }
}

enum class ZibAllergyIntoleranceProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibAllergyIntolerance("http://nictiz.nl/fhir/StructureDefinition/zib-AllergyIntolerance");

    companion object {
        fun fromValue(value: String): ZibAllergyIntoleranceProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-AllergyIntolerance" -> HTTPNictizNlFhirStructureDefinitionZibAllergyIntolerance
            else                                                               -> throw IllegalArgumentException()
        }
    }
}

data class ZibDrugUse (
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibDrugUseProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibDrugUse>(json)
    }
}

enum class ZibDrugUseProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibDrugUse("http://nictiz.nl/fhir/StructureDefinition/zib-DrugUse");

    companion object {
        fun fromValue(value: String): ZibDrugUseProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-DrugUse" -> HTTPNictizNlFhirStructureDefinitionZibDrugUse
            else                                                    -> throw IllegalArgumentException()
        }
    }
}

data class ZibFunctionalOrMentalStatus (
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibFunctionalOrMentalStatusProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibFunctionalOrMentalStatus>(json)
    }
}

enum class ZibFunctionalOrMentalStatusProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibFunctionalOrMentalStatus("http://nictiz.nl/fhir/StructureDefinition/zib-FunctionalOrMentalStatus");

    companion object {
        fun fromValue(value: String): ZibFunctionalOrMentalStatusProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-FunctionalOrMentalStatus" -> HTTPNictizNlFhirStructureDefinitionZibFunctionalOrMentalStatus
            else                                                                     -> throw IllegalArgumentException()
        }
    }
}

data class ZibLivingSituation (
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibLivingSituationProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibLivingSituation>(json)
    }
}

enum class ZibLivingSituationProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibLivingSituation("http://nictiz.nl/fhir/StructureDefinition/zib-LivingSituation");

    companion object {
        fun fromValue(value: String): ZibLivingSituationProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-LivingSituation" -> HTTPNictizNlFhirStructureDefinitionZibLivingSituation
            else                                                            -> throw IllegalArgumentException()
        }
    }
}

data class ZibMedicalDevice (
    val bodySite: List<MgoCoding>? = null,
    val device: MgoReference? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val laterality: List<MgoCoding>? = null,
    val note: List<MgoAnnotation>? = null,
    val organization: MgoReference? = null,
    val patient: MgoReference? = null,
    val practitioner: MgoReference? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibMedicalDeviceProfile,

    val reason: MgoReference? = null,
    val recordedOn: String? = null,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val source: MgoReference? = null,
    val status: String? = null,
    val whenUsed: MgoPeriod? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibMedicalDevice>(json)
    }
}

enum class ZibMedicalDeviceProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibMedicalDevice("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDevice");

    companion object {
        fun fromValue(value: String): ZibMedicalDeviceProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDevice" -> HTTPNictizNlFhirStructureDefinitionZibMedicalDevice
            else                                                          -> throw IllegalArgumentException()
        }
    }
}

data class ZibMedicalDeviceProduct (
    val expirationDate: String? = null,
    val id: String? = null,
    val note: List<MgoAnnotation>? = null,
    val patient: MgoReference? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibMedicalDeviceProductProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibMedicalDeviceProduct>(json)
    }
}

enum class ZibMedicalDeviceProductProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibMedicalDeviceProduct("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceProduct");

    companion object {
        fun fromValue(value: String): ZibMedicalDeviceProductProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceProduct" -> HTTPNictizNlFhirStructureDefinitionZibMedicalDeviceProduct
            else                                                                 -> throw IllegalArgumentException()
        }
    }
}

data class ZibMedicationAgreement (
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

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibMedicationAgreementProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val repeatPeriodCyclicalSchedule: MgoQuantity? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val stopType: List<MgoCoding>? = null,
    val usageDuration: MgoQuantity? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibMedicationAgreement>(json)
    }
}

enum class ZibMedicationAgreementProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibMedicationAgreement("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationAgreement");

    companion object {
        fun fromValue(value: String): ZibMedicationAgreementProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationAgreement" -> HTTPNictizNlFhirStructureDefinitionZibMedicationAgreement
            else                                                                -> throw IllegalArgumentException()
        }
    }
}

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

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibMedicationUseProfile,

    val reasonCode: List<List<MgoCoding>>? = null,
    val reasonForChangeOrDiscontinuationOfUse: List<MgoCoding>? = null,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val repeatPeriodCyclicalSchedule: MgoQuantity? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val taken: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibMedicationUse>(json)
    }
}

enum class ZibMedicationUseProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibMedicationUse("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse");

    companion object {
        fun fromValue(value: String): ZibMedicationUseProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse" -> HTTPNictizNlFhirStructureDefinitionZibMedicationUse
            else                                                          -> throw IllegalArgumentException()
        }
    }
}

data class ZibNutritionAdvice (
    val comment: String? = null,
    val dateTime: String? = null,
    val foodPreferenceModifier: List<List<MgoCoding>>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val patient: MgoReference? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibNutritionAdviceProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val status: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibNutritionAdvice>(json)
    }
}

enum class ZibNutritionAdviceProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibNutritionAdvice("http://nictiz.nl/fhir/StructureDefinition/zib-NutritionAdvice");

    companion object {
        fun fromValue(value: String): ZibNutritionAdviceProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-NutritionAdvice" -> HTTPNictizNlFhirStructureDefinitionZibNutritionAdvice
            else                                                            -> throw IllegalArgumentException()
        }
    }
}

data class ZibPayer (
    val beneficiary: MgoReference? = null,
    val contract: List<MgoReference>? = null,
    val dependent: String? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val grouping: Grouping,

    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val network: String? = null,
    val order: Double? = null,
    val payor: List<MgoReference>? = null,
    val period: MgoPeriod? = null,
    val policyHolder: MgoReference? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibPayerProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val relationship: List<MgoCoding>? = null,
    val resourceType: String? = null,
    val sequence: String? = null,
    val status: String? = null,
    val subscriber: MgoReference? = null,

    @get:JsonProperty("subscriberId")@field:JsonProperty("subscriberId")
    val subscriberID: String? = null,

    val type: List<MgoCoding>? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibPayer>(json)
    }
}

data class Grouping (
    @get:JsonProperty("class")@field:JsonProperty("class")
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
    val subPlanDisplay: String? = null
)

enum class ZibPayerProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibPayer("http://nictiz.nl/fhir/StructureDefinition/zib-Payer");

    companion object {
        fun fromValue(value: String): ZibPayerProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-Payer" -> HTTPNictizNlFhirStructureDefinitionZibPayer
            else                                                  -> throw IllegalArgumentException()
        }
    }
}

data class ZibProblem (
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

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibProblemProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val severity: List<MgoCoding>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val stage: Stage,

    val subject: MgoReference? = null,
    val verificationStatus: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibProblem>(json)
    }
}

data class Evidence (
    val code: List<List<MgoCoding>>? = null,
    val detail: List<MgoReference>? = null
)

enum class ZibProblemProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibProblem("http://nictiz.nl/fhir/StructureDefinition/zib-Problem");

    companion object {
        fun fromValue(value: String): ZibProblemProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-Problem" -> HTTPNictizNlFhirStructureDefinitionZibProblem
            else                                                    -> throw IllegalArgumentException()
        }
    }
}

data class Stage (
    val assessment: List<MgoReference>? = null,
    val summary: List<MgoCoding>? = null
)

data class ZibProduct (
    val code: List<MgoCoding>? = null,
    val description: String? = null,
    val form: List<MgoCoding>? = null,
    val id: String? = null,
    val ingredient: List<ZibProductIngredient>? = null,

    @get:JsonProperty("package", required=true)@field:JsonProperty("package", required=true)
    val zibProductPackage: Package,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibProductProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibProduct>(json)
    }
}

data class ZibProductIngredient (
    val amount: MgoRatio? = null,
    val item: List<MgoCoding>? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibProductIngredient>(json)
    }
}

enum class ZibProductProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibProduct("http://nictiz.nl/fhir/StructureDefinition/zib-Product");

    companion object {
        fun fromValue(value: String): ZibProductProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-Product" -> HTTPNictizNlFhirStructureDefinitionZibProduct
            else                                                    -> throw IllegalArgumentException()
        }
    }
}

data class Package (
    val content: List<PackageContent>? = null
)

data class PackageContent (
    val item: List<MgoCoding>? = null,
    val reference: MgoReference? = null
)

data class ZibProductPackage (
    val content: List<ZibProductPackageContent>? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibProductPackage>(json)
    }
}

data class ZibProductPackageContent (
    val item: List<MgoCoding>? = null,
    val reference: MgoReference? = null
)

data class ZibTobaccoUse (
    val bodySite: List<MgoCoding>? = null,
    val category: List<List<MgoCoding>>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: List<MgoCoding>? = null,
    val effectivePeriod: MgoPeriod? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibTobaccoUseProfile,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibTobaccoUse>(json)
    }
}

enum class ZibTobaccoUseProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibTobaccoUse("http://nictiz.nl/fhir/StructureDefinition/zib-TobaccoUse");

    companion object {
        fun fromValue(value: String): ZibTobaccoUseProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-TobaccoUse" -> HTTPNictizNlFhirStructureDefinitionZibTobaccoUse
            else                                                       -> throw IllegalArgumentException()
        }
    }
}

data class ZibTreatmentDirective (
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

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val profile: ZibTreatmentDirectiveProfile,

    val purpose: List<MgoCoding>? = null,

    @get:JsonProperty("referenceId", required=true)@field:JsonProperty("referenceId", required=true)
    val referenceID: String,

    val resourceType: String? = null,
    val securityLabel: List<MgoCoding>? = null,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val sourceAttachment: SourceAttachment,

    val sourceIdentifier: MgoIdentifier? = null,
    val sourceReference: MgoReference? = null,
    val status: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibTreatmentDirective>(json)
    }
}

data class ZibTreatmentDirectiveActor (
    val reference: MgoReference? = null,
    val role: List<MgoCoding>? = null
)

data class ZibTreatmentDirectiveDatum (
    val meaning: String? = null,
    val reference: MgoReference? = null
)

data class Except (
    val action: List<List<MgoCoding>>? = null,
    val actor: List<ExceptActor>? = null,

    @get:JsonProperty("class")@field:JsonProperty("class")
    val exceptClass: List<MgoCoding>? = null,

    val code: List<MgoCoding>? = null,
    val data: List<ExceptDatum>? = null,
    val dataPeriod: MgoPeriod? = null,
    val period: MgoPeriod? = null,
    val purpose: List<MgoCoding>? = null,
    val securityLabel: List<MgoCoding>? = null,
    val type: String? = null
)

data class ExceptActor (
    val reference: MgoReference? = null,
    val role: List<MgoCoding>? = null
)

data class ExceptDatum (
    val meaning: String? = null,
    val reference: MgoReference? = null
)

data class Policy (
    val authority: String? = null,
    val id: String? = null,
    val uri: String? = null
)

enum class ZibTreatmentDirectiveProfile(val value: String) {
    HTTPNictizNlFhirStructureDefinitionZibTreatmentDirective("http://nictiz.nl/fhir/StructureDefinition/zib-TreatmentDirective");

    companion object {
        fun fromValue(value: String): ZibTreatmentDirectiveProfile = when (value) {
            "http://nictiz.nl/fhir/StructureDefinition/zib-TreatmentDirective" -> HTTPNictizNlFhirStructureDefinitionZibTreatmentDirective
            else                                                               -> throw IllegalArgumentException()
        }
    }
}

data class SourceAttachment (
    val contentType: String? = null,
    val creation: String? = null,
    val data: String? = null,
    val hash: String? = null,
    val language: String? = null,
    val size: Double? = null,
    val title: String? = null,
    val url: String? = null
)
