package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import nl.rijksoverheid.mgo.component.pdf.MgoPdfStore
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.TEST_MGO_RESOURCE
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
internal class OnClearScreenTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val mgoResourceStore = MgoResourceStore()
  private val mgoPdfStore = MgoPdfStore(context)

  private val usecase = OnClearScreen(mgoResourceStore = mgoResourceStore, mgoPdfStore = mgoPdfStore)

  @Test
  fun testInvoke() {
    // Given: Mgo resource is stored
    mgoResourceStore.store(TEST_MGO_RESOURCE)

    // Given: Pdf is stored
    mgoPdfStore.get("test.pdf").createNewFile()

    // When: Calling invoke
    usecase.invoke()

    // Then: Mgo resource no longer exists
    assertNull(getMgoResource())

    // Then: Pdf no longer exists
    assertFalse(mgoPdfStore.get("test.pdf").exists())
  }

  private fun getMgoResource(): MgoResource? {
    return try {
      mgoResourceStore.get("1")
    } catch (_: Exception) {
      return null
    }
  }
}
