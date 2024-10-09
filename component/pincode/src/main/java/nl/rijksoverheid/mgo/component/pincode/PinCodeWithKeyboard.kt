package nl.rijksoverheid.mgo.component.pincode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.pincode.keyboard.Keyboard
import nl.rijksoverheid.mgo.component.pincode.pincode.PinCode
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme

@Composable
fun PinCodeWithKeyboard(
    pinCode: List<Int>,
    onPressNumber: (number: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(bottom = 4.dp),
        horizontalAlignment =
            Alignment
                .CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        PinCode(modifier = Modifier.padding(vertical = 64.dp), pinCode = pinCode)
        Spacer(modifier = Modifier.weight(1f))
        Keyboard(onPressNumber = onPressNumber)
    }
}

@DefaultPreviews
@Composable
internal fun PinCodeWithKeyboardPreview() {
    MgoTheme {
        PinCodeWithKeyboard(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            pinCode = listOf(1, 2, 3),
            onPressNumber = {},
        )
    }
}
