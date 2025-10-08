package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.mapper

import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DisplayValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.SingleValue
import org.junit.Assert.assertEquals
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
    assertEquals("Label", sections[0].rows[0].heading)
    assertEquals("Display 1, Display 2", sections[0].rows[0].value)
  }
}
