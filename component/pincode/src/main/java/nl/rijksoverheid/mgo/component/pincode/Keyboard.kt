package nl.rijksoverheid.mgo.component.pincode

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme

@Composable
fun Keyboard(
    onPressNumber: (number: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(modifier = modifier.wrapContentWidth().height(IntrinsicSize.Min), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KeyboardItemNumber(number = 1)
                KeyboardItemNumber(number = 2)
                KeyboardItemNumber(number = 3)
            }
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KeyboardItemNumber(number = 4)
                KeyboardItemNumber(number = 5)
                KeyboardItemNumber(number = 6)
            }
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KeyboardItemNumber(number = 7)
                KeyboardItemNumber(number = 8)
                KeyboardItemNumber(number = 9)
            }
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KeyboardItemIcon(icon = R.drawable.ic_keyboard_fingerprint)
                KeyboardItemNumber(number = 0)
                KeyboardItemIcon(icon = R.drawable.ic_keyboard_backspace)
            }
        }
    }
}

@Composable
private fun RowScope.KeyboardItemNumber(number: Int) {
    KeyboardItem(
        modifier = Modifier.weight(1f).aspectRatio(2.25f),
        onClick = {},
        type = KeyboardItemType.Number(number),
    )
}

@Composable
private fun RowScope.KeyboardItemIcon(
    @DrawableRes icon: Int,
) {
    KeyboardItem(
        modifier = Modifier.weight(1f).aspectRatio(2.25f),
        onClick = {},
        type = KeyboardItemType.Icon(icon),
    )
}

@PreviewLightDark
@Composable
internal fun PinCodeWithKeyboardPreview() {
    MgoTheme {
        Keyboard(onPressNumber = {})
    }
}
