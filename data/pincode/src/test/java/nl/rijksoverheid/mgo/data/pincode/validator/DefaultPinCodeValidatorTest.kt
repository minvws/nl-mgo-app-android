package nl.rijksoverheid.mgo.data.pincode.validator

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DefaultPinCodeValidatorTest {
    @Test
    fun `Given pin code is frequently used, When calling use case, Then return false`() {
        // Given
        val pinCode = listOf(1, 2, 3, 4, 5)
        val validator = DefaultPinCodeValidator()

        // When
        val valid = validator.invoke(pinCode)

        // Then
        assertFalse(valid)
    }

    @Test
    fun `Given pin code has only 3 unique numbers, When calling use case, Then return false`() {
        // Given
        val pinCode = listOf(0, 0, 0, 2, 3)
        val validator = DefaultPinCodeValidator()

        // When
        val valid = validator.invoke(pinCode)

        // Then
        assertFalse(valid)
    }

    @Test
    fun `Given pin code is not frequently used and has 4 unique numbers, When calling use case, Then return false`() {
        // Given
        val pinCode = listOf(0, 5, 3, 1, 4)
        val validator = DefaultPinCodeValidator()

        // When
        val valid = validator.invoke(pinCode)

        // Then
        assertTrue(valid)
    }
}
