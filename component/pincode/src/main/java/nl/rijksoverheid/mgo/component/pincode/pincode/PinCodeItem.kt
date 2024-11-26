package nl.rijksoverheid.mgo.component.pincode.pincode

import android.view.animation.OvershootInterpolator
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
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.MgoVibrateDuration
import nl.rijksoverheid.mgo.component.theme.actionPrimaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.vibrate
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PinCodeItem(
    position: Int,
    color: Color,
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
            val vibrationDuration = if (error) MgoVibrateDuration.LONG else MgoVibrateDuration.SHORT
            context.vibrate(vibrationDuration)

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

@PreviewLightDark
@Composable
internal fun PinCodeItemNotFilledPreview() {
    MgoTheme {
        PinCodeItem(
            modifier = Modifier.size(32.dp),
            color = MaterialTheme.colorScheme.actionPrimaryDefaultBackground(),
            position = 1,
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
        )
    }
}
