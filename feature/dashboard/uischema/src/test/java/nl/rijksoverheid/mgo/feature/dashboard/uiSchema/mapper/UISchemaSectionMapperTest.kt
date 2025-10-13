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
import org.junit.Assert.assertTrue
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Static)
    assertEquals("Label", sections[0].rows[0].heading)
    assertEquals("Display", sections[0].rows[0].value)
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Static)
    assertEquals("Label", sections[0].rows[0].heading)
    assertEquals("Display 1, Display 2", sections[0].rows[0].value)
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Static)
    assertEquals("Label", sections[0].rows[0].heading)
    assertEquals("Display 1, Display 2, Display 3, Display 4", sections[0].rows[0].value)
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Reference)
    assertEquals("Label", sections[0].rows[0].heading)
    assertEquals("Display", sections[0].rows[0].value)
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Static)
    assertEquals("Label", sections[0].rows[0].heading)
    assertEquals("Display", sections[0].rows[0].value)
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Binary.Empty)
    assertNull(sections[0].rows[0].heading)
    assertEquals("Label", sections[0].rows[0].value)
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Binary.NotDownloaded.Idle)
    assertNull(sections[0].rows[0].heading)
    assertEquals("Label", sections[0].rows[0].value)
    assertEquals("1", (sections[0].rows[0] as UISchemaRow.Binary.NotDownloaded.Idle).binary)
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Reference)
    assertNull(sections[0].rows[0].heading)
    assertEquals("Label", sections[0].rows[0].value)
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Static)
    assertEquals("Label", sections[0].rows[0].heading)
    assertEquals("1", sections[0].rows[0].value)
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
    assertTrue(sections[0].rows[0] is UISchemaRow.Link)
    assertNull(sections[0].rows[0].heading)
    assertEquals("Label", sections[0].rows[0].value)
  }
}
