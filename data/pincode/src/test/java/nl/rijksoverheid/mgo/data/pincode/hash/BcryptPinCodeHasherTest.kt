package nl.rijksoverheid.mgo.data.pincode.hash

import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mindrot.jbcrypt.BCrypt

internal class BcryptPinCodeHasherTest {
  @Test
  fun `Given hasher, When calling hash, Hash the pin code`() {
    // Given
    val hasher = BcryptPinCodeHasher()

    // When
    mockkStatic(BCrypt::hashpw)
    every { BCrypt.hashpw(any(), any()) } answers { "321" }
    val hash = hasher.hash("123")

    // Then
    assertEquals("321", hash)
  }

  @Test
  fun `Given hasher, When calling validate, Validate the pin code`() {
    // Given
    val hasher = BcryptPinCodeHasher()

    // When
    mockkStatic(BCrypt::hashpw)
    every { BCrypt.checkpw(any(), any()) } answers { true }
    val validate = hasher.validate("123", "123")

    // Then
    assertEquals(true, validate)
  }
}
