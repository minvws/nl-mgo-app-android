package nl.rijksoverheid.mgo.component.pincode.pincode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme

@Composable
fun PinCode(
    pinCode: List<Int>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        PinCodeItemInstance(fill = pinCode.isNotEmpty())
        PinCodeItemInstance(fill = pinCode.size > 1)
        PinCodeItemInstance(fill = pinCode.size > 2)
        PinCodeItemInstance(fill = pinCode.size > 3)
        PinCodeItemInstance(fill = pinCode.size > 4)
    }
}

@Composable
private fun PinCodeItemInstance(
    fill: Boolean,
    modifier: Modifier = Modifier,
) {
    PinCodeItem(modifier = modifier.size(32.dp), fill = fill)
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
