package nl.rijksoverheid.mgo.data.healthcare.mapper

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadBinary
import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadLink
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.fhirParser.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.fhirParser.models.MultipleValues
import nl.rijksoverheid.mgo.data.fhirParser.models.ReferenceLink
import nl.rijksoverheid.mgo.data.fhirParser.models.ReferenceValue
import nl.rijksoverheid.mgo.data.fhirParser.models.SingleValue
import nl.rijksoverheid.mgo.data.fhirParser.models.UiElement
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.TestMgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.models.UISchemaRow
import nl.rijksoverheid.mgo.data.healthcare.models.mapper.DefaultUISchemaSectionMapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DefaultUISchemaSectionMapperTest {
  private val mgoResourceRepository = TestMgoResourceRepository()
  private val uiSchemaMapper = TestUiSchemaMapper()
  private val mapper =
    DefaultUISchemaSectionMapper(
      mgoResourceRepository = mgoResourceRepository,
      uiSchemaMapper = uiSchemaMapper,
    )

  @Test
  fun testReferenceLinkCacheSuccess() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              ReferenceLink(
                reference = "reference",
                label = "label",
              ),
            ),
        )

      // Given: reference is cached
      mgoResourceRepository.setMgoResources(Result.success(listOf(MgoResource(referenceId = "reference", profile = "profile", jsonBase64 = ""))))
      uiSchemaMapper.setDetail(uiSchema)

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Reference(heading = null, value = "label", referenceId = "reference")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testReferenceLinkCacheFailed() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              ReferenceLink(
                reference = "reference",
                label = "label",
              ),
            ),
        )

      // Given: getting ui schema errors
      mgoResourceRepository.setMgoResource(Result.failure(IllegalStateException("Something went wrong")))
      uiSchemaMapper.setDetailError(IllegalStateException("Something went wrong"))

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Static(heading = "label", value = "reference")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testReferenceLinkErrorGettingUiSchema() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              ReferenceLink(
                reference = "reference",
                label = "label",
              ),
            ),
        )

      // Given: reference failed to cache
      mgoResourceRepository.setMgoResources(Result.success(listOf(MgoResource(referenceId = "reference", profile = "profile", jsonBase64 = ""))))
      uiSchemaMapper.setDetailError(IllegalStateException("Something went wrong"))

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Static(heading = "label", value = "reference")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testDownloadLinkWithUrl() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              DownloadLink(
                label = "label",
                url = "https://www.google.com",
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Link(heading = null, value = "label", url = "https://www.google.com")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testDownloadLinkWithoutUrl() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              DownloadLink(
                label = "label",
                url = null,
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      assertTrue(sections[0].rows.isEmpty())
    }

  @Test
  fun testSingleValueWithDisplay() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              SingleValue(
                label = "label",
                display = "display",
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Static(heading = "label", value = "display")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testSingleValueWithoutDisplay() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              SingleValue(
                label = "label",
                display = null,
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      assertTrue(sections[0].rows.isEmpty())
    }

  @Test
  fun testMultipleValuesWithDisplay() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              MultipleValues(
                label = "label",
                display = listOf("display 1", "display 2"),
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Static(heading = "label", value = "display 1, display 2")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testMultipleValuesWithoutDisplay() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              MultipleValues(
                label = "label",
                display = null,
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      assertTrue(sections[0].rows.isEmpty())
    }

  @Test
  fun testMultipleGroupedValuesWithDisplay() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              MultipleGroupedValues(
                label = "label",
                display = listOf(listOf("display 1"), listOf("display 2")),
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Static(heading = "label", value = "display 1, display 2")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testMultipleGroupedValuesWithoutDisplay() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              MultipleGroupedValues(
                label = "label",
                display = null,
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      assertTrue(sections[0].rows.isEmpty())
    }

  @Test
  fun testReferenceValueCacheSuccess() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              ReferenceValue(
                reference = "reference",
                label = "label",
                display = "display",
              ),
            ),
        )

      // Given: reference is cached
      mgoResourceRepository.setMgoResources(Result.success(listOf(MgoResource(referenceId = "reference", profile = "profile", jsonBase64 = ""))))
      uiSchemaMapper.setDetail(uiSchema)

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Reference(heading = "label", value = "display", referenceId = "reference")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testReferenceValueCacheFailed() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              ReferenceValue(
                reference = "reference",
                label = "label",
                display = "display",
              ),
            ),
        )

      // Given: getting ui schema errors
      mgoResourceRepository.setMgoResource(Result.failure(IllegalStateException("Something went wrong")))
      uiSchemaMapper.setDetailError(IllegalStateException("Something went wrong"))

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Static(heading = "label", value = "display")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testReferenceValueErrorGettingUiSchema() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              ReferenceValue(
                reference = "reference",
                label = "label",
                display = "display",
              ),
            ),
        )

      // Given: reference failed to cache
      mgoResourceRepository.setMgoResources(Result.success(listOf(MgoResource(referenceId = "reference", profile = "profile", jsonBase64 = ""))))
      uiSchemaMapper.setDetailError(IllegalStateException("Something went wrong"))

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Static(heading = "label", value = "display")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testReferenceValueWithoutReference() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              ReferenceValue(
                reference = null,
                label = "label",
                display = "display",
              ),
            ),
        )

      // Given: reference failed to cache
      mgoResourceRepository.setMgoResources(Result.success(listOf(MgoResource(referenceId = "reference", profile = "profile", jsonBase64 = ""))))
      uiSchemaMapper.setDetailError(IllegalStateException("Something went wrong"))

      // When
      val sections = mapper.map(uiSchema)

      // Then
      assertTrue(sections[0].rows.isEmpty())
    }

  @Test
  fun testReferenceValueWithoutDisplay() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              ReferenceValue(
                reference = "reference",
                label = "label",
                display = null,
              ),
            ),
        )

      // Given: reference failed to cache
      mgoResourceRepository.setMgoResources(Result.success(listOf(MgoResource(referenceId = "reference", profile = "profile", jsonBase64 = ""))))
      uiSchemaMapper.setDetailError(IllegalStateException("Something went wrong"))

      // When
      val sections = mapper.map(uiSchema)

      // Then
      assertTrue(sections[0].rows.isEmpty())
    }

  @Test
  fun testDownloadBinaryWithoutReference() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              DownloadBinary(
                label = "label",
                reference = null,
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Binary.Empty(heading = null, value = "label")
      assertEquals(expected, sections[0].rows[0])
    }

  @Test
  fun testDownloadBinaryWithReference() =
    runTest {
      // Given
      val uiSchema =
        getUiSchema(
          elements =
            listOf(
              DownloadBinary(
                label = "label",
                reference = "reference",
              ),
            ),
        )

      // When
      val sections = mapper.map(uiSchema)

      // Then
      val expected = UISchemaRow.Binary.NotDownloaded.Idle(heading = null, value = "label", binary = "reference")
      assertEquals(expected, sections[0].rows[0])
    }
}

private fun getUiSchema(elements: List<UiElement>) =
  HealthUiSchema(
    label = "Label",
    children =
      listOf(
        HealthUiGroup(
          children = elements,
        ),
      ),
  )
