package nl.rijksoverheid.mgo.component.theme.samples.theme

import androidx.compose.ui.test.junit4.createComposeRule
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.component.theme.theme.getAppTheme
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class AppThemeTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun `isDarkTheme returns true for DARK mode`() {
    composeTestRule.setContent {
      val result = AppTheme.DARK.isDarkTheme()
      assertEquals(true, result)
    }
  }

  @Test
  fun `isDarkTheme returns false for LIGHT mode`() {
    composeTestRule.setContent {
      val result = AppTheme.LIGHT.isDarkTheme()
      assertEquals(false, result)
    }
  }

  @Test
  fun `isDarkTheme returns false for SYSTEM mode`() {
    composeTestRule.setContent {
      val result = AppTheme.SYSTEM.isDarkTheme()
      assertEquals(false, result)
    }
  }

  @Test
  fun `getAppTheme returns SYSTEM when input is null`() {
    val result = getAppTheme(null)
    assertEquals(AppTheme.SYSTEM, result)
  }

  @Test
  fun `getAppTheme returns correct AppTheme when input is valid`() {
    assertEquals(AppTheme.LIGHT, getAppTheme("LIGHT"))
    assertEquals(AppTheme.DARK, getAppTheme("DARK"))
    assertEquals(AppTheme.SYSTEM, getAppTheme("SYSTEM"))
  }
}
