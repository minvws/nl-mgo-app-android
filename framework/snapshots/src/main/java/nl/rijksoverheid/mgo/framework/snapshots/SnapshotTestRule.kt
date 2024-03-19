package nl.rijksoverheid.mgo.framework.snapshots

import android.util.Size
import androidx.compose.runtime.Composable
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.NightMode
import com.android.resources.ScreenOrientation
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class SnapshotTestRule : TestRule {
    private val defaultDeviceConfig = DeviceConfig.PIXEL_5

    @get:Rule
    val rule = Paparazzi(deviceConfig = defaultDeviceConfig)

    fun snapshots(content: @Composable () -> Unit) =
        rule.apply {
            setPhone()
            snapshot(name = "phone-portrait-light") { content() }
            setPhone(fontScale = 2f)
            snapshot(name = "phone-portrait-light-font-increased") { content() }
            setPhone(nightMode = NightMode.NIGHT)
            snapshot(name = "phone-portrait-dark") { content() }
            setPhone(orientation = ScreenOrientation.LANDSCAPE)
            snapshot(name = "phone-landscape-light") { content() }
            setPhone(fontScale = 1.5f, orientation = ScreenOrientation.LANDSCAPE)
            snapshot(name = "phone-landscape-light-font-increased") { content() }
            setTablet()
            snapshot(name = "tablet-portrait-light") { content() }
            setTablet(orientation = ScreenOrientation.LANDSCAPE)
            snapshot(name = "tablet-landscape-light") { content() }
        }

    private fun setPhone(
        nightMode: NightMode = NightMode.NOTNIGHT,
        orientation: ScreenOrientation = ScreenOrientation.PORTRAIT,
        fontScale: Float = 1f,
    ) {
        val deviceConfig = DeviceConfig.PIXEL_5
        val screenSize = deviceConfig.getScreenSize(orientation = orientation)
        rule.unsafeUpdateConfig(
            deviceConfig =
                deviceConfig.copy(
                    screenWidth = screenSize.width,
                    screenHeight = screenSize.height,
                    nightMode = nightMode,
                    fontScale = fontScale,
                ),
        )
    }

    private fun setTablet(orientation: ScreenOrientation = ScreenOrientation.PORTRAIT) {
        val deviceConfig = DeviceConfig.PIXEL_C
        val screenSize = deviceConfig.getScreenSize(orientation = orientation)
        rule.unsafeUpdateConfig(
            deviceConfig =
                deviceConfig.copy(
                    screenWidth = screenSize.width,
                    screenHeight = screenSize.height,
                ),
        )
    }

    private fun DeviceConfig.getScreenSize(orientation: ScreenOrientation): Size {
        val screenWidthForOrientation = if (orientation == ScreenOrientation.PORTRAIT) screenWidth else screenHeight
        val screenHeightForOrientation = if (orientation == ScreenOrientation.PORTRAIT) screenHeight else screenWidth
        return Size(screenWidthForOrientation, screenHeightForOrientation)
    }

    override fun apply(
        base: Statement?,
        description: Description?,
    ): Statement {
        return RuleChain.outerRule(rule).apply(base, description)
    }
}
