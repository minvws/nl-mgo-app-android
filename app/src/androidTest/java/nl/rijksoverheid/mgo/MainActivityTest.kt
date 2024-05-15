package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ActivityScenario
import dagger.hilt.android.testing.HiltAndroidRule
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.robots.DashboardScreenRobot
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

internal open class MainActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Inject
    lateinit var setHasSeenOnboarding: SetHasSeenOnboarding

    internal fun launchApp(
        skipOnboarding: Boolean,
        block: DashboardScreenRobot.() -> Unit,
    ) {
        setHasSeenOnboarding(skipOnboarding)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity {
            block(DashboardScreenRobot(composeTestRule))
        }
    }
}
