package nl.rijksoverheid.mgo.data.fhir

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DiskFhirResponseJsonStoreTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val diskStore = DiskFhirResponseJsonStore(context = context)

  @Test
  fun testStore() =
    runTest {
      // When: Storing json
      val jsonSource =
        diskStore.store(
          organizationId = "1",
          endpointId = "1",
          json = "",
        )

      // Then: When getting, file is retrieved that is stored
      val expectedJsonSource = diskStore.get(organizationId = "1", endpointId = "1")
      assertEquals(expectedJsonSource, jsonSource)
    }
}
