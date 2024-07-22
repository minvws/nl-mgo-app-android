package nl.rijksoverheid.mgo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import nl.rijksoverheid.mgo.data.config.api.ConfigResponse
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import nl.rijksoverheid.mgo.framework.test.TestServer
import nl.rijksoverheid.mgo.framework.test.toJsonString
import nl.rijksoverheid.mgo.robots.DashboardScreenRobot
import nl.rijksoverheid.mgo.robots.OnboardingScreenRobot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

open class BaseTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Inject
    lateinit var setHasSeenOnboarding: SetHasSeenOnboarding

    @Inject
    lateinit var environmentRepository: EnvironmentRepository

    private val testServer = TestServer()

    @Before
    fun before() {
        hiltRule.inject()
    }

    @After
    fun after() {
        testServer.stop()
    }

    internal fun launchAppSkipOnboarding(
        environment: Environment = Environment.Tst(999),
        block: DashboardScreenRobot.() -> Unit,
    ) {
        composeTestRule.launch<MainActivity>(
            onBefore = {
                (environmentRepository as TestEnvironmentRepository).setEnvironment(environment)
                setHasSeenOnboarding(true)
            },
            onAfterLaunched = {
                block(DashboardScreenRobot(this))
            },
        )
    }

    internal fun launchApp(
        environment: Environment = Environment.Tst(999),
        block: OnboardingScreenRobot.() -> Unit,
    ) {
        composeTestRule.launch<MainActivity>(
            onBefore = {
                (environmentRepository as TestEnvironmentRepository).setEnvironment(environment)
            },
            onAfterLaunched = {
                block(OnboardingScreenRobot(this))
            },
        )
    }

    fun getCustomEnvironment(versionCode: Int): Environment.Custom {
        testServer.start()
        return Environment.Custom(versionCode = versionCode, url = testServer.url())
    }

    fun mockConfigResponse(configResponse: ConfigResponse) {
        testServer.enqueueJson(json = configResponse.toJsonString())
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
