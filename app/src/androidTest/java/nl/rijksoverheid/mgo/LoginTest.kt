package nl.rijksoverheid.mgo

import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginTest {
  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val activityRule = ActivityScenarioRule(MainActivity::class.java)

  @Before
  fun setup() {
    hiltRule.inject()
  }

  @Test
  fun launchApp() {
    Thread.sleep(5000)
  }
}
