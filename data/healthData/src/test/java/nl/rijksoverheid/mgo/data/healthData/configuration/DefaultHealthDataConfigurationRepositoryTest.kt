package nl.rijksoverheid.mgo.data.healthData.configuration

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultHealthDataConfigurationRepositoryTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val repository = DefaultHealthDataConfigurationRepository(context)

  @Test
  fun testGetGroups() {
    // When: Getting groups
    val groups = repository.getGroups()

    // Then: Groups are returned from json file
    assertEquals(4, groups.size)
    assertEquals("health", groups.first().id)
    assertEquals(
      "http://nictiz.nl/fhir/StructureDefinition/zib-Problem",
      groups
        .first()
        .categories
        .first()
        .subcategories
        .first()
        .profiles
        .first(),
    )
  }

  @Test
  fun testGetDataSetConfigs() {
    // When: Getting data sets
    val dataSets = repository.getDataSets()

    // Then: Data sets are returned from json file
    assertEquals(4, dataSets.size)
    assertEquals("48", dataSets.first().id)
  }
}
