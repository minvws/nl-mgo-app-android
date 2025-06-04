package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.mapper

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadLink
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.fhirParser.models.ReferenceLink
import nl.rijksoverheid.mgo.data.fhirParser.models.UiElement
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.TestMgoResourceRepository
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaRow
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
