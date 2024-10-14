package nl.rijksoverheid.mgo.component.pincode

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.pincode.keyboard.Keyboard
import nl.rijksoverheid.mgo.component.pincode.pincode.PinCode
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.bodySmall

@Composable
fun PinCodeWithKeyboard(
    pinCode: List<Int>,
    onPressNumber: (number: Int) -> Unit,
    onPressBackspace: () -> Unit,
    onErrorAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    onClickHint: (() -> Unit)? = null,
    error: Boolean = false,
) {
    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        PinCode(
            modifier = Modifier.padding(vertical = 64.dp),
            pinCode = pinCode,
            error = error,
            onErrorAnimationFinished = onErrorAnimationFinished,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (hint != null) {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { onClickHint?.invoke() }
                        .padding(16.dp),
                text = hint,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.actionTertiaryDefaultText(),
            )
        }
        Keyboard(onPressNumber = onPressNumber, onPressBackspace = onPressBackspace)
    }
}

@PreviewLightDark
@Composable
internal fun PinCodeWithKeyboardPreview() {
    MgoTheme {
        PinCodeWithKeyboard(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
            pinCode = listOf(1, 2, 3),
            onPressNumber = {},
            onPressBackspace = {},
            onErrorAnimationFinished = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun PinCodeWithKeyboardAndHintPreview() {
    MgoTheme {
        PinCodeWithKeyboard(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
            pinCode = listOf(1, 2, 3),
            onPressNumber = {},
            onPressBackspace = {},
            onErrorAnimationFinished = {},
            hint = "Klik hier",
        )
    }
}
