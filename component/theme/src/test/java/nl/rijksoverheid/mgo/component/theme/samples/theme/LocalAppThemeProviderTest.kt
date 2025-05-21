package nl.rijksoverheid.mgo.component.theme.samples.theme

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createComposeRule
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.component.theme.theme.DefaultLocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class LocalAppThemeProviderTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun testCompositionLocal() {
    composeTestRule.setContent {
      CompositionLocalProvider(
        LocalAppThemeProvider provides DefaultLocalAppThemeProvider(AppTheme.SYSTEM),
      ) {
        val appTheme = LocalAppThemeProvider.current.appTheme
        assertEquals(AppTheme.SYSTEM, appTheme)
      }
    }
  }
}
