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
//   val reference = Reference.fromJson(jsonString)
//   val singleValue = SingleValue.fromJson(jsonString)
//   val uISchema = UISchema.fromJson(jsonString)
//   val uISchemaGroup = UISchemaGroup.fromJson(jsonString)
//   val zibAdministrationSchedule = ZibAdministrationSchedule.fromJson(jsonString)
//   val zibInstructionsForUse = ZibInstructionsForUse.fromJson(jsonString)
//   val zibMedicationUse = ZibMedicationUse.fromJson(jsonString)
//   val zibProduct = ZibProduct.fromJson(jsonString)
//   val zibProductIngredient = ZibProductIngredient.fromJson(jsonString)
//   val zibProductPackage = ZibProductPackage.fromJson(jsonString)

@file:Suppress("ktlint")

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
private fun <T> ObjectMapper.convert(
    k: kotlin.reflect.KClass<*>,
    fromJson: (JsonNode) -> T,
    toJson: (T) -> String,
    isUnion: Boolean = false,
) = registerModule(
    SimpleModule().apply {
        addSerializer(
            k.java as Class<T>,
            object : StdSerializer<T>(k.java as Class<T>) {
                override fun serialize(
                    value: T,
                    gen: JsonGenerator,
                    provider: SerializerProvider,
                ) = gen.writeRawValue(toJson(value))
            },
        )
        addDeserializer(
            k.java as Class<T>,
            object : StdDeserializer<T>(k.java as Class<T>) {
                override fun deserialize(
                    p: JsonParser,
                    ctxt: DeserializationContext,
                ) = fromJson(p.readValueAsTree())
            },
        )
    },
)

val mapper =
    jacksonObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        convert(ChildDisplay::class, { ChildDisplay.fromJson(it) }, { it.toJson() }, true)
        convert(DisplayElement::class, { DisplayElement.fromJson(it) }, { it.toJson() }, true)
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

data class MultipleGroupValue(
    val display: List<List<String>>? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val label: String,
    val summary: Boolean? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val type: String,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MultipleGroupValue>(json)
    }
}

data class ValueOptions(
    val summary: Boolean? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ValueOptions>(json)
    }
}

data class MultipleValue(
    val display: List<String>? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val label: String,
    val summary: Boolean? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val type: String,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MultipleValue>(json)
    }
}

data class Reference(
    val display: String? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val label: String,
    val reference: String? = null,
    val summary: Boolean? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val type: String,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<Reference>(json)
    }
}

data class SingleValue(
    val display: String? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val label: String,
    val summary: Boolean? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val type: String,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<SingleValue>(json)
    }
}

data class UISchema(
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val children: List<UISchemaGroup>,
    val label: String? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<UISchema>(json)
    }
}

data class UISchemaGroup(
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val children: List<ChildElement>,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val label: String,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<UISchemaGroup>(json)
    }
}

data class ChildElement(
    val display: ChildDisplay? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val label: String,
    val summary: Boolean? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val type: String,
    val reference: String? = null,
)

sealed class ChildDisplay {
    class StringValue(val value: String) : ChildDisplay()

    class UnionArrayValue(val value: List<DisplayElement>) : ChildDisplay()

    fun toJson(): String =
        mapper.writeValueAsString(
            when (this) {
                is StringValue -> this.value
                is UnionArrayValue -> this.value
            },
        )

    companion object {
        fun fromJson(jn: JsonNode): ChildDisplay =
            when (jn) {
                is TextNode -> StringValue(mapper.treeToValue(jn))
                is ArrayNode -> UnionArrayValue(mapper.treeToValue(jn))
                else -> throw IllegalArgumentException()
            }
    }
}

sealed class DisplayElement {
    class StringArrayValue(val value: List<String>) : DisplayElement()

    class StringValue(val value: String) : DisplayElement()

    fun toJson(): String =
        mapper.writeValueAsString(
            when (this) {
                is StringArrayValue -> this.value
                is StringValue -> this.value
            },
        )

    companion object {
        fun fromJson(jn: JsonNode): DisplayElement =
            when (jn) {
                is ArrayNode -> StringArrayValue(mapper.treeToValue(jn))
                is TextNode -> StringValue(mapper.treeToValue(jn))
                else -> throw IllegalArgumentException()
            }
    }
}

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
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val profile: String,
    val reasonCode: List<List<MgoCoding>>? = null,
    val reasonForChangeOrDiscontinuationOfUse: List<MgoCoding>? = null,
    val repeatPeriodCyclicalSchedule: MgoQuantity? = null,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val taken: String? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibMedicationUse>(json)
    }
}

data class MgoReference(
    val display: String? = null,
    val reference: String? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoReference>(json)
    }
}

data class MgoCoding(
    val code: String? = null,
    val display: String? = null,
    val system: String? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoCoding>(json)
    }
}

data class ZibInstructionsForUse(
    val additionalInstruction: List<List<MgoCoding>>? = null,
    val asNeeded: List<MgoCoding>? = null,
    val doseQuantity: MgoQuantity? = null,
    val doseRange: MgoRange? = null,
    val maxDosePerPeriod: MgoRatio? = null,
    val rateQuantity: MgoQuantity? = null,
    val rateRange: MgoRange? = null,
    val rateRatio: MgoRatio? = null,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val timing: ZibAdministrationSchedule,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibInstructionsForUse>(json)
    }
}

data class MgoQuantity(
    val code: String? = null,
    val comparator: String? = null,
    val system: String? = null,
    val unit: String? = null,
    val value: Double? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoQuantity>(json)
    }
}

data class MgoRange(
    val high: MgoQuantity? = null,
    val low: MgoQuantity? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoRange>(json)
    }
}

data class MgoRatio(
    val denominator: MgoQuantity? = null,
    val numerator: MgoQuantity? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoRatio>(json)
    }
}

data class ZibAdministrationSchedule(
    val dayOfWeek: List<String>? = null,
    val duration: Double? = null,
    val durationUnit: String? = null,
    val frequency: Double? = null,
    val frequencyMax: Double? = null,
    val period: Double? = null,
    val periodUnit: String? = null,
    val timeOfDay: List<String>? = null,
    @get:JsonProperty("when")@field:JsonProperty("when")
    val zibAdministrationScheduleWhen: List<String>? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibAdministrationSchedule>(json)
    }
}

data class MgoPeriod(
    val end: String? = null,
    val start: String? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoPeriod>(json)
    }
}

data class MgoIdentifier(
    val system: String? = null,
    val type: List<MgoCoding>? = null,
    val use: String? = null,
    val value: String? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoIdentifier>(json)
    }
}

data class MgoAnnotation(
    val author: MgoReference? = null,
    val text: String? = null,
    val time: String? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<MgoAnnotation>(json)
    }
}

data class ZibProduct(
    val code: List<MgoCoding>? = null,
    val description: String? = null,
    val form: List<MgoCoding>? = null,
    val id: String? = null,
    val ingredient: List<ZibProductIngredient>? = null,
    @get:JsonProperty("package", required = true)@field:JsonProperty("package", required = true)
    val zibProductPackage: Package,
    @get:JsonProperty(required = true)@field:JsonProperty(required = true)
    val profile: String,
    val resourceType: String? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibProduct>(json)
    }
}

data class ZibProductIngredient(
    val amount: MgoRatio? = null,
    val item: List<MgoCoding>? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibProductIngredient>(json)
    }
}

data class Package(
    val content: List<PackageContent>? = null,
)

data class PackageContent(
    val item: List<MgoCoding>? = null,
    val reference: MgoReference? = null,
)

data class ZibProductPackage(
    val content: List<ZibProductPackageContent>? = null,
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ZibProductPackage>(json)
    }
}

data class ZibProductPackageContent(
    val item: List<MgoCoding>? = null,
    val reference: MgoReference? = null,
)
