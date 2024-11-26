package nl.rijksoverheid.mgo.component.pincode.pincode

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionPrimaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.notificationError

@Composable
fun PinCode(
    pinCode: List<Int>,
    modifier: Modifier = Modifier,
    error: Boolean = false,
) {
    val color = if (error) MaterialTheme.colorScheme.notificationError() else MaterialTheme.colorScheme.actionPrimaryDefaultBackground()
    val animatedScale = remember { Animatable(1f) }
    LaunchedEffect(error) {
        if (error) {
            animatedScale.animateTo(
                targetValue = 1.25f,
                animationSpec =
                    tween(
                        durationMillis = 175,
                        easing = {
                            OvershootInterpolator().getInterpolation(it)
                        },
                    ),
            )
            animatedScale.animateTo(
                targetValue = 1f,
                animationSpec =
                    tween(
                        durationMillis = 175,
                        easing = {
                            OvershootInterpolator().getInterpolation(it)
                        },
                    ),
            )
        }
    }

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        PinCodeItemInstance(
            color = color,
            scale = animatedScale.value,
            position = 1,
            error = error,
            fill = pinCode.isNotEmpty(),
        )
        PinCodeItemInstance(
            color = color,
            scale = animatedScale.value,
            position = 2,
            error = error,
            fill = pinCode.size > 1,
        )
        PinCodeItemInstance(
            color = color,
            scale = animatedScale.value,
            position = 3,
            error = error,
            fill = pinCode.size > 2,
        )
        PinCodeItemInstance(
            color = color,
            scale = animatedScale.value,
            position = 4,
            error = error,
            fill = pinCode.size > 3,
        )
        PinCodeItemInstance(
            color = color,
            scale = animatedScale.value,
            position = 5,
            error = error,
            fill = pinCode.size > 4,
        )
    }
}

@Composable
private fun PinCodeItemInstance(
    color: Color,
    scale: Float,
    error: Boolean,
    fill: Boolean,
    position: Int,
    modifier: Modifier = Modifier,
) {
    PinCodeItem(
        modifier = modifier.size(32.dp),
        color = color,
        error = error,
        position = position,
        fill = fill,
    )
}

@PreviewLightDark
@Composable
internal fun PinCodeEmptyPreview() {
    MgoTheme {
        PinCode(pinCode = listOf())
    }
}

@PreviewLightDark
@Composable
internal fun PinCodeHalfFilledPreview() {
    MgoTheme {
        PinCode(pinCode = listOf(1, 2, 3))
    }
}
