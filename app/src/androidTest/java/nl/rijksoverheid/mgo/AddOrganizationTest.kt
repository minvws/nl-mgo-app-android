package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import nl.rijksoverheid.mgo.robots.AuthRobot
import nl.rijksoverheid.mgo.robots.OnboardingRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AddOrganizationTest {
  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val composeTestRule = createComposeRule()

  @Inject
  lateinit var authRobot: AuthRobot

  @Inject
  lateinit var onboardingRobot: OnboardingRobot

  @Before
  fun setup() {
    hiltRule.inject()
  }

  @Test
  fun addOrganizationTest() {
  }
}
