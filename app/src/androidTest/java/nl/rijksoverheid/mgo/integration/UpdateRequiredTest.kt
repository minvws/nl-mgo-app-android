package nl.rijksoverheid.mgo.integration

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidTest
import nl.rijksoverheid.mgo.BaseTest
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
internal class UpdateRequiredTest : BaseTest() {
//    @Test
//    fun updateScreenShown() {
//        val environment = getCustomEnvironment(versionCode = 999)
//        mockConfigResponse(configResponse = ConfigResponse(androidMinimumVersion = 1000))
//
//        launchAppSkipOnboarding(environment = environment) {
//            assertUpdateRequiredScreenVisible()
//        }
//    }
//
//    @Test
//    fun updateScreenNotShown() {
//        val environment = getCustomEnvironment(versionCode = 1001)
//        mockConfigResponse(configResponse = ConfigResponse(androidMinimumVersion = 1000))
//
//        launchAppSkipOnboarding(environment = environment) {
//            assertUpdateRequiredScreenNotVisible()
//        }
//    }
}
