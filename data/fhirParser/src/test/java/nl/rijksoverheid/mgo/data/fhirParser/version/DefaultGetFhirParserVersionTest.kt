package nl.rijksoverheid.mgo.data.fhirParser.version

import android.content.Context
import android.content.res.AssetManager
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

class DefaultGetFhirParserVersionTest {
  private lateinit var getFhirParserVersion: DefaultGetFhirParserVersion

  @MockK
  private lateinit var context: Context

  @MockK
  private lateinit var assetManager: AssetManager

  private val mockedFhirVersion =
    "{\"version\": \"main\", \"git_ref\": \"d2c2081aefcaa7c0e8c413a1b8c654bcdcbe7705\", \"created\": " +
      "\"2025-03-21T16:01:38\"}"

  @Before
  fun setUp() {
    MockKAnnotations.init(this, relaxUnitFun = true)
    every { context.assets } returns assetManager
    getFhirParserVersion = DefaultGetFhirParserVersion(context)
  }

  @Test
  fun testGetFhirVersion() {
    // Given: Fhir parser returns version
    val mockInputStream =
      ByteArrayInputStream(mockedFhirVersion.toByteArray(StandardCharsets.UTF_8))
    every { assetManager.open("mgo-fhir-data.iife.version.json") } returns mockInputStream

    // When: Calling use case
    val fhirVersion = getFhirParserVersion.invoke()

    // Then: Expect fhir version
    assertEquals(mockedFhirVersion, fhirVersion)
  }

  @Test
  fun testGetFhirVersionFailed() {
    // Given: Fhir parser returns version
    val mockInputStream =
      ByteArrayInputStream(mockedFhirVersion.toByteArray(StandardCharsets.UTF_8))
    every { assetManager.open("mgo-fhir-data.iife.version.json") } throws IOException()

    // When: Calling use case
    val fhirVersion = getFhirParserVersion.invoke()

    // Then: Expect empty fhir version
    assertEquals("", fhirVersion)
  }
}
