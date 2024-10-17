package nl.rijksoverheid.mgo.component.pincode.pincode

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
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
    onErrorAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier,
    error: Boolean = false,
) {
    val color = if (error) MaterialTheme.colors.notificationError() else MaterialTheme.colors.actionPrimaryDefaultBackground()
    val animatedScale = remember { Animatable(1f) }
    LaunchedEffect(error) {
        if (error) {
            animatedScale.animateTo(
                targetValue = 1.2f,
                animationSpec =
                    tween(durationMillis = 250, easing = {
                        OvershootInterpolator().getInterpolation(it)
                    }),
            )
            animatedScale.animateTo(
                targetValue = 1f,
                animationSpec =
                    tween(durationMillis = 250, easing = {
                        OvershootInterpolator().getInterpolation(it)
                    }),
            )
            onErrorAnimationFinished()
        }
    }

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        PinCodeItemInstance(color = color, scale = animatedScale.value, position = 1, fill = pinCode.isNotEmpty())
        PinCodeItemInstance(color = color, scale = animatedScale.value, position = 2, fill = pinCode.size > 1)
        PinCodeItemInstance(color = color, scale = animatedScale.value, position = 3, fill = pinCode.size > 2)
        PinCodeItemInstance(color = color, scale = animatedScale.value, position = 4, fill = pinCode.size > 3)
        PinCodeItemInstance(color = color, scale = animatedScale.value, position = 5, fill = pinCode.size > 4)
    }
}

@Composable
private fun PinCodeItemInstance(
    color: Color,
    scale: Float,
    fill: Boolean,
    position: Int,
    modifier: Modifier = Modifier,
) {
    PinCodeItem(modifier = modifier.size(32.dp), color = color, scale = scale, position = position, fill = fill)
}

@PreviewLightDark
@Composable
internal fun PinCodeEmptyPreview() {
    MgoTheme {
        PinCode(pinCode = listOf(), onErrorAnimationFinished = {})
    }
}

@PreviewLightDark
@Composable
internal fun PinCodeHalfFilledPreview() {
    MgoTheme {
        PinCode(pinCode = listOf(1, 2, 3), onErrorAnimationFinished = {})
    }
}
