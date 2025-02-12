package nl.rijksoverheid.mgo.data.fhirParser.models

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object UiElementSerializer : JsonContentPolymorphicSerializer<UiElement>(UiElement::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<UiElement> {
        return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            "SINGLE_VALUE" -> SingleValue.serializer()
            "MULTIPLE_VALUES" -> MultipleValues.serializer()
            else -> throw SerializationException("Unknown type: ${element.jsonObject["type"]}")
        }
    }
}
