package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.mapper

import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRow
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaSectionMapper
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DisplayValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadBinary
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.SingleValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UISchemaSectionMapperTest {
  private val mgoResourceStore = MgoResourceStore()
  private val mapper = UISchemaSectionMapper(mgoResourceStore = mgoResourceStore)

  @Test
  fun testSingleValue() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  SingleValue(
                    label = "Label",
                    value = DisplayValue(display = "Display"),
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Static
    assertEquals("Label", firstRow.heading)
    assertEquals("Display", firstRow.value.first().value)
  }

  @Test
  fun testMultipleValues() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  MultipleValues(
                    label = "Label",
                    value = listOf(DisplayValue(display = "Display 1"), DisplayValue(display = "Display 2")),
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Static
    assertEquals("Label", firstRow.heading)
    assertEquals("Display 1", firstRow.value[0].value)
    assertEquals("Display 2", firstRow.value[1].value)
  }

  @Test
  fun testMultipleGroupedValues() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  MultipleGroupedValues(
                    label = "Label",
                    value =
                      listOf(
                        listOf(DisplayValue(display = "Display 1"), DisplayValue(display = "Display 2")),
                        listOf(DisplayValue(display = "Display 3"), DisplayValue(display = "Display 4")),
                      ),
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Static
    assertEquals("Label", firstRow.heading)
    assertEquals("Display 1", firstRow.value[0].value)
    assertEquals("Display 2", firstRow.value[1].value)
    assertEquals("Display 3", firstRow.value[2].value)
    assertEquals("Display 4", firstRow.value[3].value)
  }

  @Test
  fun testReferenceValueClickable() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  ReferenceValue(
                    label = "Label",
                    display = "Display",
                    reference = "1",
                  ),
                ),
            ),
          ),
      )

    // Given: The mgo resource exists in the store
    val mgoResource = MgoResource(referenceId = "1", profile = "", json = "")
    mgoResourceStore.store(mgoResource)

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Reference
    assertEquals("Label", firstRow.heading)
    assertEquals("Display", firstRow.value)
  }

  @Test
  fun testReferenceValueNotClickable() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  ReferenceValue(
                    label = "Label",
                    display = "Display",
                    reference = "1",
                  ),
                ),
            ),
          ),
      )

    // Given: The mgo resource does not exist in the store

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Static
    assertEquals("Label", firstRow.heading)
    assertEquals("Display", firstRow.value.first().value)
  }

  @Test
  fun testReferenceValueEmpty() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  ReferenceValue(
                    label = "Label",
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    assertEquals(0, sections[0].rows.size)
  }

  @Test
  fun testDownloadBinaryEmptyReference() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  DownloadBinary(
                    label = "Label",
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Binary.Empty
    assertNull(firstRow.heading)
    assertEquals("Label", firstRow.value)
  }

  @Test
  fun testDownloadBinaryReference() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  DownloadBinary(
                    label = "Label",
                    reference = "1",
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Binary.NotDownloaded.Idle
    assertNull(firstRow.heading)
    assertEquals("Label", firstRow.value)
    assertEquals("1", firstRow.binary)
  }

  @Test
  fun testReferenceLinkClickable() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  ReferenceLink(
                    label = "Label",
                    reference = "1",
                  ),
                ),
            ),
          ),
      )

    // Given: The mgo resource exists in the store
    val mgoResource = MgoResource(referenceId = "1", profile = "", json = "")
    mgoResourceStore.store(mgoResource)

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Reference
    assertNull(firstRow.heading)
    assertEquals("Label", firstRow.value)
  }

  @Test
  fun testReferenceLinkNotClickable() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  ReferenceLink(
                    label = "Label",
                    reference = "1",
                  ),
                ),
            ),
          ),
      )

    // Given: The mgo resource does not exist in the store

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Static
    assertEquals("Label", firstRow.heading)
    assertEquals("1", firstRow.value.first().value)
  }

  @Test
  fun testDownloadLink() {
    // Given: Ui schema
    val uiSchema =
      HealthUiSchema(
        label = "Label",
        children =
          listOf(
            HealthUiGroup(
              children =
                listOf(
                  DownloadLink(
                    label = "Label",
                    url = "https://www.google.com",
                  ),
                ),
            ),
          ),
      )

    // Given: The mgo resource does not exist in the store

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertEquals(1, sections.size)
    val firstRow = sections[0].rows[0] as UISchemaRow.Link
    assertNull(firstRow.heading)
    assertEquals("Label", firstRow.value)
  }
}
