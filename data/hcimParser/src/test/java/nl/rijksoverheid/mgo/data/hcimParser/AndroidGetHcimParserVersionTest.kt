package nl.rijksoverheid.mgo.data.hcimParser

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import nl.rijksoverheid.mgo.data.hcimParser.version.AndroidGetHcimParserVersion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AndroidGetHcimParserVersionTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val usecase = AndroidGetHcimParserVersion(context)

  @Test
  fun testReadFile() {
    assertNotNull(usecase.invoke())
  }

  fun testError() {
    assertEquals("", usecase.invoke("error.json"))
  }
}
