package nl.rijksoverheid.mgo.data.uiSchema

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
import kotlinx.serialization.json.jsonPrimitive

object ChildDisplaySerializer : KSerializer<ChildDisplay> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ChildDisplay", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ChildDisplay {
        val input = decoder as? JsonDecoder ?: error("This serializer only works with JSON format")
        return when (val element = input.decodeJsonElement()) {
            is JsonPrimitive -> ChildDisplay.StringValue(element.content) // If it's a single string
            is JsonArray -> ChildDisplay.UnionArrayValue(element.map { it.jsonPrimitive.content }.map { DisplayElement.StringValue(it) })
            else -> throw SerializationException("Unexpected JSON element type: ${element::class}")
        }
    }

    override fun serialize(encoder: Encoder, value: ChildDisplay) {
        val output = encoder as? JsonEncoder ?: error("This serializer only works with JSON format")
        when (value) {
            is ChildDisplay.StringValue -> output.encodeString(value.value)
            is ChildDisplay.UnionArrayValue -> {
                val elements = value.value.filterIsInstance<DisplayElement.StringValue>().map { it.value }
                output.encodeSerializableValue(ListSerializer(String.serializer()), elements)
            }
        }
    }
}
