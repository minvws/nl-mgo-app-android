package nl.rijksoverheid.mgo.data.healthCategories

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AndroidGetDataSetsFromDiskTest {
  private val context: Context = ApplicationProvider.getApplicationContext()
  private val usecase = AndroidGetDataSetsFromDisk(context)

  @Test
  fun testInvoke() {
    val dataSets = usecase.invoke()
    assertEquals(6, dataSets.size)
  }
}
