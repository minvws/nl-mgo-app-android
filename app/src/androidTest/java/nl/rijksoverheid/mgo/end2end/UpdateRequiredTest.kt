package nl.rijksoverheid.mgo.end2end

import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import nl.rijksoverheid.mgo.MainActivityTest
import nl.rijksoverheid.mgo.feature.config.TEST_TAG_UPDATE_REQUIRED_TITLE
import nl.rijksoverheid.mgo.framework.environment.AppFlavor
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.test.TestServer
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import nl.rijksoverheid.mgo.modules.AppInfoModule
import org.junit.Test
import org.junit.runner.RunWith

@UninstallModules(AppInfoModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
internal class UpdateRequiredTest : MainActivityTest() {
    // Set app version to 999
    @BindValue
    @JvmField
    val appInfo: AppInfo = AppInfo(versionCode = 999, appFlavor = AppFlavor.TEST)

    private val testServer = TestServer().also { it.start() }

    @BindValue
    @JvmField
    val environment: Environment = Environment.Custom(url = testServer.url())

    @Test
    fun updateRequiredScreenShown() {
        testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "response/config.json"))
        launchAppSkipOnboarding {
            composeTestRule.onNodeWithTag(TEST_TAG_UPDATE_REQUIRED_TITLE).assertExists()
        }
    }
}
