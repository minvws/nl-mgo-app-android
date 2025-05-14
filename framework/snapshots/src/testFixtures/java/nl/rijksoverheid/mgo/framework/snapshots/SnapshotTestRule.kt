package nl.rijksoverheid.mgo.framework.snapshots

import android.util.Size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.detectEnvironment
import com.android.ide.common.rendering.api.SessionParams.RenderingMode
import com.android.resources.NightMode
import com.android.resources.ScreenOrientation
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Represents a device from which to create a snapshot.
 */
enum class SnapshotDevice {
  /**
   * Represents a portrait phone in light mode.
   */
  PHONE_PORTRAIT_LIGHT,

  /**
   * Represents a portrait phone in light mode with the font size increased.
   */
  PHONE_PORTRAIT_LIGHT_FONT_INCREASED,

  /**
   * Represents a landscape phone in light mode.
   */
  PHONE_LANDSCAPE_LIGHT,

  /**
   * Represents a landscape phone in light mode with the font size increased.
   */
  PHONE_LANDSCAPE_LIGHT_FONT_INCREASED,

  /**
   * Represents a portrait phone in dark mode.
   */
  PHONE_PORTRAIT_DARK,

  /**
   * Represents a portrait tablet in light mode.
   */
  TABLET_PORTRAIT_LIGHT,

  /**
   * Represents a landscape tablet in light mode.
   */
  TABLET_LANDSCAPE_LIGHT,
}

/**
 * Represents a list of devices to create snapshots for.
 *
 * @param devices A list of [SnapshotDevice].
 */
sealed class SnapshotDevices(
  val devices: List<SnapshotDevice>,
) {
  /**
   * Creates snapshots of all devices that are available.
   */
  data object All : SnapshotDevices(SnapshotDevice.entries)

  /**
   * Creates snapshots of:
   * - Portrait phone in light mode.
   * - Portrait phone in dark mode.
   * - Landscape phone in light mode.
   */
  data object Default : SnapshotDevices(
    listOf(
      SnapshotDevice.PHONE_PORTRAIT_LIGHT,
      SnapshotDevice.PHONE_PORTRAIT_DARK,
      SnapshotDevice.PHONE_LANDSCAPE_LIGHT,
    ),
  )

  /**
   * Creates snapshots of a portrait phone in light and dark mode.
   */
  data object PhoneLightDarkPortrait : SnapshotDevices(listOf(SnapshotDevice.PHONE_PORTRAIT_LIGHT, SnapshotDevice.PHONE_PORTRAIT_DARK))
}

/**
 *
 */
class SnapshotTestRule(
  deviceConfig: DeviceConfig = DeviceConfig.PIXEL_5,
  renderingMode: RenderingMode = RenderingMode.SHRINK,
  useDeviceResolution: Boolean = false,
) : TestRule {
  @get:Rule
  val rule =
    Paparazzi(
      environment = detectEnvironment().copy(compileSdkVersion = 34), // See: https://github.com/cashapp/paparazzi/issues/1866,
      deviceConfig = deviceConfig,
      renderingMode = renderingMode,
      useDeviceResolution = useDeviceResolution,
    )

  /**
   * Captures UI snapshots for different device configurations.
   *
   * This function iterates over a list of predefined snapshot devices
   * and takes a screenshot of the provided Composable content in various
   * screen sizes, orientations, and themes (light/dark mode, font scaling).
   *
   * @param devices The set of devices for which snapshots should be taken. Defaults to [SnapshotDevices.Default].
   * @param content The Composable UI to be rendered and captured in snapshots.
   */
  fun snapshots(
    devices: SnapshotDevices = SnapshotDevices.Default,
    content: @Composable () -> Unit,
  ) = rule.apply {
    devices.devices.forEach { device ->
      when (device) {
        SnapshotDevice.PHONE_PORTRAIT_LIGHT -> {
          setPhone(nightMode = NightMode.NOTNIGHT)
          previewSnapshot(fileName = "phone-portrait-light") { content() }
        }

        SnapshotDevice.PHONE_PORTRAIT_LIGHT_FONT_INCREASED -> {
          setPhone(nightMode = NightMode.NOTNIGHT, fontScale = 2f)
          previewSnapshot(fileName = "phone-portrait-light-font-increased") { content() }
        }

        SnapshotDevice.PHONE_LANDSCAPE_LIGHT -> {
          setPhone(nightMode = NightMode.NOTNIGHT, orientation = ScreenOrientation.LANDSCAPE)
          previewSnapshot(fileName = "phone-landscape-light") { content() }
        }

        SnapshotDevice.PHONE_LANDSCAPE_LIGHT_FONT_INCREASED -> {
          setPhone(nightMode = NightMode.NOTNIGHT, orientation = ScreenOrientation.LANDSCAPE, fontScale = 1.5f)
          previewSnapshot(fileName = "phone-landscape-light-font-increased") { content() }
        }

        SnapshotDevice.PHONE_PORTRAIT_DARK -> {
          setPhone(nightMode = NightMode.NIGHT)
          previewSnapshot(fileName = "phone-portrait-dark") { content() }
        }

        SnapshotDevice.TABLET_PORTRAIT_LIGHT -> {
          setTablet(nightMode = NightMode.NOTNIGHT, orientation = ScreenOrientation.PORTRAIT)
          previewSnapshot(fileName = "tablet-portrait-light") { content() }
        }

        SnapshotDevice.TABLET_LANDSCAPE_LIGHT -> {
          setTablet(nightMode = NightMode.NOTNIGHT, orientation = ScreenOrientation.LANDSCAPE)
          previewSnapshot(fileName = "tablet-landscape-light") { content() }
        }
      }
    }
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
          orientation = orientation,
        ),
    )
  }

  private fun setTablet(
    nightMode: NightMode = NightMode.NOTNIGHT,
    orientation: ScreenOrientation = ScreenOrientation.PORTRAIT,
  ) {
    val deviceConfig = DeviceConfig.PIXEL_C
    val screenSize = deviceConfig.getScreenSize(orientation = orientation)
    rule.unsafeUpdateConfig(
      deviceConfig =
        deviceConfig.copy(
          screenWidth = screenSize.width,
          screenHeight = screenSize.height,
          nightMode = nightMode,
        ),
    )
  }

  private fun DeviceConfig.getScreenSize(orientation: ScreenOrientation): Size {
    val screenWidthForOrientation = if (orientation == ScreenOrientation.PORTRAIT) screenWidth else screenHeight
    val screenHeightForOrientation = if (orientation == ScreenOrientation.PORTRAIT) screenHeight else screenWidth
    return Size(screenWidthForOrientation, screenHeightForOrientation)
  }

  private fun Paparazzi.previewSnapshot(
    fileName: String,
    content: @Composable () -> Unit,
  ) {
    snapshot(name = fileName) {
      CompositionLocalProvider(LocalInspectionMode provides true) {
        content()
      }
    }
  }

  override fun apply(
    base: Statement?,
    description: Description?,
  ): Statement = RuleChain.outerRule(rule).apply(base, description)
}
