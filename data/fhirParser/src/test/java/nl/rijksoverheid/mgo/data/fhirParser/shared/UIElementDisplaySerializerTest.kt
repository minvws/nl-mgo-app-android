package nl.rijksoverheid.mgo.data.fhirParser.shared

import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.serialization.json.Json

class UIElementDisplaySerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun validateSerializerString() {
        val testJson = UIElementDisplay.StringValue("Value")
        val jsonString = json.encodeToString(UIElementDisplaySerializer, testJson)
        val deserialized = json.decodeFromString(UIElementDisplaySerializer, jsonString)
        assertTrue(deserialized is UIElementDisplay.StringValue)
    }

    @Test
    fun validateSerializerUnionArray() {
        val testJson = UIElementDisplay.UnionArrayValue(listOf(DisplayElement.StringValue("Value")))
        val jsonString = json.encodeToString(UIElementDisplaySerializer, testJson)
        val deserialized = json.decodeFromString(UIElementDisplaySerializer, jsonString)
        assertTrue(deserialized is UIElementDisplay.UnionArrayValue)
    }

    @Test
    fun validateSerializerUnionArrayWithArray() {
        val testJson = UIElementDisplay.UnionArrayValue(listOf(DisplayElement.StringArrayValue(listOf("Value"))))
        val jsonString = json.encodeToString(UIElementDisplaySerializer, testJson)
        val deserialized = json.decodeFromString(UIElementDisplaySerializer, jsonString)
        assertTrue(deserialized is UIElementDisplay.UnionArrayValue)
    }
}
