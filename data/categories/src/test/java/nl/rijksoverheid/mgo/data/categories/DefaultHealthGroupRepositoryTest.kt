package nl.rijksoverheid.mgo.data.categories

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class DefaultHealthGroupRepositoryTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val repository = DefaultHealthGroupRepository(context)

  @Test
  fun testGet() {
    // When: Calling get
    val groups = repository.get()

    // Then: Json file is parsed to objects
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
}
