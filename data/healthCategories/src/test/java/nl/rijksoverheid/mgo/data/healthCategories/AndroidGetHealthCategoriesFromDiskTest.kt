package nl.rijksoverheid.mgo.data.healthCategories

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AndroidGetHealthCategoriesFromDiskTest {
  private val context: Context = ApplicationProvider.getApplicationContext()
  private val usecase = AndroidGetHealthCategoriesFromDisk(context)

  @Test
  fun testInvoke() {
    val groups = usecase.invoke()
    assertEquals(4, groups.size)
  }
}
