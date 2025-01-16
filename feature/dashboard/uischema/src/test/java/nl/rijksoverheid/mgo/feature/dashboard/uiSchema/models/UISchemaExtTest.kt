package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models

import nl.rijksoverheid.mgo.data.fhirParser.shared.DisplayElement
import nl.rijksoverheid.mgo.data.fhirParser.shared.UIElementDisplay
import org.junit.Assert.assertEquals
import org.junit.Test

class UISchemaExtTest {
    @Test
    fun testGetStringStringValue() {
        // Given: UIElementDisplay
        val uiElementDisplay = UIElementDisplay.StringValue("Value")

        // When: Calling getString
        val string = uiElementDisplay.getString()

        // Then
        assertEquals("Value", string)
    }

    @Test
    fun testGetStringUnionArrayValue() {
        // Given: UIElementDisplay
        val uiElementDisplay =
            UIElementDisplay.UnionArrayValue(
                listOf(
                    DisplayElement.StringValue("Value 1"),
                    DisplayElement.StringValue("Value 2"),
                ),
            )

        // When: Calling getString
        val string = uiElementDisplay.getString()

        // Then
        assertEquals("Value 1, Value 2", string)
    }

    @Test
    fun testGetStringUnionArrayValueArray() {
        // Given: UIElementDisplay
        val uiElementDisplay =
            UIElementDisplay.UnionArrayValue(
                listOf(
                    DisplayElement.StringArrayValue(listOf("Value 1", "Value 2")),
                    DisplayElement.StringValue("Value 3"),
                ),
            )

        // When: Calling getString
        val string = uiElementDisplay.getString()

        // Then
        assertEquals("Value 1, Value 2, Value 3", string)
    }

    @Test
    fun testGetStringNull() {
        // Given: UIElementDisplay
        val uiElementDisplay = null

        // When: Calling getString
        val string = uiElementDisplay.getString()

        // Then
        assertEquals("", string)
    }
}
