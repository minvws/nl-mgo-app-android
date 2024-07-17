package nl.rijksoverheid.mgo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.robots.DashboardScreenRobot
import nl.rijksoverheid.mgo.robots.OnboardingScreenRobot
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

open class BaseTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Inject
    lateinit var setHasSeenOnboarding: SetHasSeenOnboarding

    internal fun launchAppSkipOnboarding(block: DashboardScreenRobot.() -> Unit) {
        composeTestRule.launch<MainActivity>(
            onBefore = {
                setHasSeenOnboarding(true)
            },
            onAfterLaunched = {
                block(DashboardScreenRobot(this))
            },
        )
    }

    internal fun launchApp(block: OnboardingScreenRobot.() -> Unit) {
        composeTestRule.launch<MainActivity>(
            onAfterLaunched = {
                block(OnboardingScreenRobot(this))
            },
        )
    }
}

/**
 * Uses a [ComposeTestRule] created via [createEmptyComposeRule] that allows setup before the activity
 * is launched via [onBefore]. Assertions on the view can be made in [onAfterLaunched].
 */
inline fun <reified A : Activity> ComposeTestRule.launch(
    onBefore: () -> Unit = {},
    intentFactory: (Context) -> Intent = { Intent(ApplicationProvider.getApplicationContext(), A::class.java) },
    onAfterLaunched: ComposeTestRule.() -> Unit,
) {
    onBefore()

    val context = ApplicationProvider.getApplicationContext<Context>()
    ActivityScenario.launch<A>(intentFactory(context))

    onAfterLaunched()
}
