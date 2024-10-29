package nl.rijksoverheid.mgo.component.theme

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

enum class MgoVibrateDuration(val millis: Long) {
    SHORT(150L),
    LONG(300L),
}

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

private fun Context.hasVibrationPermission() =
    ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.VIBRATE,
    ) == PackageManager.PERMISSION_GRANTED

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
