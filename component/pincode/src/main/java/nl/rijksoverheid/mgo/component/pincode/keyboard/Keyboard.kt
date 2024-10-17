package nl.rijksoverheid.mgo.component.pincode.keyboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.pincode.R
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun Keyboard(
    onPressNumber: (number: Int) -> Unit,
    onPressBackspace: () -> Unit,
    modifier: Modifier = Modifier,
    hasBiometric: Boolean = false,
    onPressBiometric: (() -> Unit)? = null,
) {
    Box(modifier = modifier) {
        Column(
            modifier =
                modifier
                    .wrapContentWidth()
                    .height(IntrinsicSize.Min),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KeyboardItemNumber(number = 1, onPressNumber = onPressNumber)
                KeyboardItemNumber(number = 2, onPressNumber = onPressNumber)
                KeyboardItemNumber(number = 3, onPressNumber = onPressNumber)
            }
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KeyboardItemNumber(number = 4, onPressNumber = onPressNumber)
                KeyboardItemNumber(number = 5, onPressNumber = onPressNumber)
                KeyboardItemNumber(number = 6, onPressNumber = onPressNumber)
            }
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KeyboardItemNumber(number = 7, onPressNumber = onPressNumber)
                KeyboardItemNumber(number = 8, onPressNumber = onPressNumber)
                KeyboardItemNumber(number = 9, onPressNumber = onPressNumber)
            }
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val biometricIconAlpha = if (hasBiometric) 1f else 0f
                KeyboardItemIcon(
                    modifier = Modifier.alpha(biometricIconAlpha),
                    icon = R.drawable.ic_keyboard_fingerprint,
                    talkBack = CopyR.string.pincode_biometric_voiceover,
                    onPressIcon = {
                        onPressBiometric?.invoke()
                    },
                )
                KeyboardItemNumber(number = 0, onPressNumber = onPressNumber)
                KeyboardItemIcon(
                    icon = R.drawable.ic_keyboard_backspace,
                    talkBack = CopyR.string.pincode_erase_voiceover,
                    onPressIcon = onPressBackspace,
                )
            }
        }
    }
}

@Composable
private fun RowScope.KeyboardItemNumber(
    number: Int,
    onPressNumber: (number: Int) -> Unit,
) {
    KeyboardItem(
        modifier =
            Modifier
                .weight(1f)
                .aspectRatio(2.25f),
        onClick = { onPressNumber(number) },
        type = KeyboardItemType.Number(number),
    )
}

@Composable
private fun RowScope.KeyboardItemIcon(
    @DrawableRes icon: Int,
    @StringRes talkBack: Int,
    onPressIcon: () -> Unit,
    modifier: Modifier = Modifier,
) {
    KeyboardItem(
        modifier =
            modifier
                .weight(1f)
                .aspectRatio(2.25f),
        onClick = onPressIcon,
        type = KeyboardItemType.Icon(icon = icon, talkBack = talkBack),
    )
}

@PreviewLightDark
@Composable
internal fun KeyboardPreview() {
    MgoTheme {
        Keyboard(onPressNumber = {}, onPressBackspace = {})
    }
}

@PreviewLightDark
@Composable
internal fun KeyboardWithBiometricPreview() {
    MgoTheme {
        Keyboard(onPressNumber = {}, onPressBackspace = {}, hasBiometric = true)
    }
}
