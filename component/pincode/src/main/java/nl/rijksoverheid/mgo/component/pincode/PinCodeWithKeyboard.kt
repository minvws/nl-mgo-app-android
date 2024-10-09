package nl.rijksoverheid.mgo.component.pincode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.rijksoverheid.mgo.component.pincode.keyboard.Keyboard
import nl.rijksoverheid.mgo.component.pincode.pincode.PinCode
import nl.rijksoverheid.mgo.component.theme.MgoTheme

@Composable
fun PinCodeWithKeyboard(
    pinCode: List<Int>,
    onPressNumber: (number: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        PinCode(pinCode = pinCode)
        Spacer(modifier = Modifier.weight(1f))
        Keyboard(
            onPressNumber = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun PinCodeWithKeyboardPreview() {
    MgoTheme {
        PinCodeWithKeyboard(
            modifier = Modifier.fillMaxSize(),
            pinCode = listOf(1, 2, 3),
            onPressNumber = {},
        )
    }
}
