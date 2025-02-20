package nl.rijksoverheid.mgo.component.theme.samples

import app.cash.paparazzi.DeviceConfig
import com.android.ide.common.rendering.api.SessionParams
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ColorSampleScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule =
        SnapshotTestRule(
            deviceConfig = DeviceConfig.PIXEL_5.copy(screenHeight = 6000),
            renderingMode = SessionParams.RenderingMode.V_SCROLL,
            useDeviceResolution = true,
        )

    @Test
    fun launchView() {
        snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
            ColorSampleScreenPreview()
        }
    }
}
