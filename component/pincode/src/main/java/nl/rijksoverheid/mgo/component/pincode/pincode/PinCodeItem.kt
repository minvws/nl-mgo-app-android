package nl.rijksoverheid.mgo.component.pincode.pincode

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionPrimaryDefaultBackground
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PinCodeItem(
    modifier: Modifier = Modifier,
    position: Int,
    color: Color,
    scale: Float,
    fill: Boolean = false,
) {
    val stateString =
        if (fill) stringResource(CopyR.string.pincode_filled_voiceover) else stringResource(CopyR.string.pincode_empty_voiceover)
    val contentDescriptionLabel = stringResource(id = CopyR.string.pincode_voiceover, position.toString(), "5", stateString)
    Box(
        modifier =
            modifier
                .semantics { contentDescription = contentDescriptionLabel }
                .scale(scale),
    ) {
        Box(
            modifier =
                modifier
                    .border(2.dp, color, CircleShape)
                    .clip(CircleShape),
        )
        if (fill) {
            Box(
                modifier =
                    modifier
                        .border(2.dp, color, CircleShape)
                        .clip(CircleShape)
                        .background(color),
            )
        }
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
            scale = 1f,
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
            scale = 1f,
            fill = true,
        )
    }
}
