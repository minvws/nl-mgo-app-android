package nl.rijksoverheid.mgo.component.pincode.pincode

import android.Manifest
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.animation.OvershootInterpolator
import androidx.annotation.RequiresPermission
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionPrimaryDefaultBackground
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@RequiresPermission(Manifest.permission.VIBRATE)
@Composable
fun PinCodeItem(
    position: Int,
    color: Color,
    onErrorAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier,
    error: Boolean = false,
    fill: Boolean = false,
) {
    val context = LocalContext.current
    val fillColor by animateColorAsState(
        if (fill) color else Color.Transparent,
        label = "color",
    )
    val animatedScale = remember { Animatable(1f) }
    LaunchedEffect(fill, error) {
        if (fill || error) {
            val hasVibrationPermission =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.VIBRATE,
                ) == PackageManager.PERMISSION_GRANTED
            if (hasVibrationPermission) {
                val vibrator = context.getVibrator()
                val vibrationMillis = if (fill) 150L else 300L
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        vibrationMillis,
                        VibrationEffect.DEFAULT_AMPLITUDE,
                    ),
                )
            }

            animatedScale.animateTo(
                targetValue = 1.25f,
                animationSpec =
                    tween(durationMillis = 175, easing = {
                        OvershootInterpolator().getInterpolation(it)
                    }),
            )
            animatedScale.animateTo(
                targetValue = 1f,
                animationSpec =
                    tween(durationMillis = 175, easing = {
                        OvershootInterpolator().getInterpolation(it)
                    }),
            )
            if (error) {
                onErrorAnimationFinished()
            }
        }
    }

    val stateString =
        if (fill) stringResource(CopyR.string.pincode_filled_voiceover) else stringResource(CopyR.string.pincode_empty_voiceover)
    val contentDescriptionLabel = stringResource(id = CopyR.string.pincode_voiceover, position.toString(), "5", stateString)
    Box(
        modifier =
            modifier
                .semantics { contentDescription = contentDescriptionLabel }
                .scale(animatedScale.value),
    ) {
        Box(
            modifier =
                modifier
                    .border(2.dp, color, CircleShape)
                    .clip(CircleShape),
        )
        Box(
            modifier =
                modifier
                    .border(2.dp, color, CircleShape)
                    .clip(CircleShape)
                    .background(fillColor),
        )
    }
}

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

@PreviewLightDark
@Composable
internal fun PinCodeItemNotFilledPreview() {
    MgoTheme {
        PinCodeItem(
            modifier = Modifier.size(32.dp),
            color = MaterialTheme.colorScheme.actionPrimaryDefaultBackground(),
            position = 1,
            onErrorAnimationFinished = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun PinCodeItemFilledPreview() {
    MgoTheme {
        PinCodeItem(
            modifier = Modifier.size(32.dp),
            color = MaterialTheme.colorScheme.actionPrimaryDefaultBackground(),
            position = 1,
            fill = true,
            onErrorAnimationFinished = {},
        )
    }
}
