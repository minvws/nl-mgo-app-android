package nl.rijksoverheid.mgo.component.mgo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.content.ContextCompat

/**
 * Defines vibration duration options for haptic feedback.
 *
 * @property millis The duration of the vibration in milliseconds.
 */
enum class MgoVibrateDuration(val millis: Long) {
  SHORT(100L),
  LONG(350L),
}

/**
 * Triggers a vibration for the specified duration if the required permission is granted.
 *
 * @receiver The [Context] used to access the device's vibration service.
 * @param duration The [MgoVibrateDuration] specifying how long the vibration should last.
 *
 * This function checks for vibration permission before attempting to vibrate. If the permission
 * is not granted, the function does nothing.
 *
 * **Note:** Suppresses the "MissingPermission" lint warning, assuming `hasVibrationPermission()`
 * properly handles permission checks.
 */
@SuppressLint("MissingPermission")
fun Context.vibrate(duration: MgoVibrateDuration) {
  if (hasVibrationPermission()) {
    val vibrator = getVibrator()
    vibrator.vibrate(
      VibrationEffect.createOneShot(
        duration.millis,
        VibrationEffect.DEFAULT_AMPLITUDE,
      ),
    )
  }
}

/**
 * Checks whether the app has the required permission to use the device's vibrator.
 *
 * @receiver The [Context] used to check the permission status.
 * @return `true` if the [Manifest.permission.VIBRATE] permission is granted, `false` otherwise.
 */
private fun Context.hasVibrationPermission() =
  ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.VIBRATE,
  ) == PackageManager.PERMISSION_GRANTED

/**
 * Retrieves the appropriate [Vibrator] instance for the device, handling API level differences.
 *
 * @receiver The [Context] used to access the vibrator service.
 * @return The [Vibrator] instance for the current device.
 *
 * On Android 12 (API level 31) and above, it uses the [VibratorManager] to get the default vibrator.
 * For older versions, it directly retrieves the [Vibrator] service.
 *
 * **Note:** Suppresses the "DEPRECATION" warning for older APIs that still use the legacy vibrator service.
 */
private fun Context.getVibrator(): Vibrator {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    val vibratorManager =
      getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
    vibratorManager.defaultVibrator
  } else {
    @Suppress("DEPRECATION")
    getSystemService(VIBRATOR_SERVICE) as Vibrator
  }
}
