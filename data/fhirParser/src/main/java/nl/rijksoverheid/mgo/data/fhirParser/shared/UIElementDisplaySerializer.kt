package nl.rijksoverheid.mgo.data.fhirParser.shared

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive

object UIElementDisplaySerializer : KSerializer<UIElementDisplay> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ChildDisplay", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UIElementDisplay {
        val input = decoder as? JsonDecoder ?: error("This serializer only works with JSON format")
        return when (val element = input.decodeJsonElement()) {
            is JsonPrimitive -> UIElementDisplay.StringValue(element.content) // If it's a single string
            is JsonArray ->
                UIElementDisplay.UnionArrayValue(
                    element.filterIsInstance<JsonPrimitive>().map { jsonElement ->
                        DisplayElement.StringValue(jsonElement.content)
                    },
                )

            else -> throw SerializationException("Unexpected JSON element type: ${element::class}")
        }
    }

    override fun serialize(
        encoder: Encoder,
        value: UIElementDisplay,
    ) {
        val output = encoder as? JsonEncoder ?: error("This serializer only works with JSON format")
        when (value) {
            is UIElementDisplay.StringValue -> output.encodeString(value.value)
            is UIElementDisplay.UnionArrayValue -> {
                val stringValues = value.value.map { displayElement -> displayElement.toStrings() }.flatten()
                output.encodeSerializableValue(ListSerializer(String.serializer()), stringValues)
            }
        }
    }

    private fun DisplayElement.toStrings(): List<String> {
        val values = mutableListOf<String>()
        when (this) {
            is DisplayElement.StringArrayValue -> {
                values.addAll(this.value)
            }

            is DisplayElement.StringValue -> values.add(this.value)
        }
        return values
    }
}
