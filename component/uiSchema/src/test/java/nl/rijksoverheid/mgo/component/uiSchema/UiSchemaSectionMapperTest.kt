package nl.rijksoverheid.mgo.component.uiSchema

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
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UiSchemaSectionMapperTest {
  private val mgoResourceStore = MgoResourceStore()
  private val mapper = UISchemaSectionMapper(mgoResourceStore)

  @Test
  fun testDownloadBinary() {
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
                    reference = null,
                  ),
                  DownloadBinary(
                    label = "Label",
                    reference = "Reference",
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertThat(sections[0].rows[0], instanceOf(UISchemaRow.Binary.Empty::class.java))
    assertThat(sections[0].rows[1], instanceOf(UISchemaRow.Binary.NotDownloaded.Idle::class.java))
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
                  ),
                  DownloadLink(
                    label = "Label",
                    url = "Url",
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertThat(sections[0].rows[0], instanceOf(UISchemaRow.Link::class.java))
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
                    value = null,
                  ),
                  MultipleGroupedValues(
                    label = "Label",
                    value = null,
                  ),
                ),
            ),
            HealthUiGroup(
              children =
                listOf(
                  MultipleGroupedValues(
                    label = "Label",
                    value = listOf(listOf(DisplayValue(system = "http://snomed.info/sct", code = "123", display = "Display"))),
                  ),
                  MultipleGroupedValues(
                    label = "Label",
                    value = listOf(listOf(DisplayValue(display = null))),
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertTrue(sections[0].rows.isEmpty())
    assertThat(sections[1].rows[0], instanceOf(UISchemaRow.Static::class.java))
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
                    value = null,
                  ),
                  MultipleValues(
                    label = "Label",
                    value = null,
                  ),
                ),
            ),
            HealthUiGroup(
              children =
                listOf(
                  MultipleValues(
                    label = "Label",
                    value = listOf(DisplayValue(system = "http://snomed.info/sct", code = "123", display = "Display")),
                  ),
                  MultipleValues(
                    label = "Label",
                    value = listOf(DisplayValue(display = null)),
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertTrue(sections[0].rows.isEmpty())
    assertThat(sections[1].rows[0], instanceOf(UISchemaRow.Static::class.java))
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
  fun testReferenceValueEmptyReference() {
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
  fun testReferenceValueEmptyDisplay() {
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
                    reference = "Reference",
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
                    value = null,
                  ),
                  SingleValue(
                    label = "Label",
                    value = null,
                  ),
                ),
            ),
            HealthUiGroup(
              children =
                listOf(
                  SingleValue(
                    label = "Label",
                    value = DisplayValue(display = "Display"),
                  ),
                  SingleValue(
                    label = "Label",
                    value = DisplayValue(display = null),
                  ),
                ),
            ),
          ),
      )

    // When: Calling map
    val sections = mapper.map(uiSchema)

    // Then: Sections are returned
    assertTrue(sections[0].rows.isEmpty())
    assertThat(sections[1].rows[0], instanceOf(UISchemaRow.Static::class.java))
  }
}
