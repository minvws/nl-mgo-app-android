package nl.rijksoverheid.mgo.component.pincode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.pincode.keyboard.Keyboard
import nl.rijksoverheid.mgo.component.pincode.pincode.PinCode
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.bodySmall

@Composable
fun PinCodeWithKeyboard(
    onPinCodeEntered: (pinCode: List<Int>) -> Unit,
    onResetError: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    onClickHint: (() -> Unit)? = null,
    hasBiometric: Boolean = false,
    onPressBiometric: (() -> Unit)? = null,
    error: Boolean = false,
) {
    var pinCode by remember { mutableStateOf(listOf<Int>()) }
    PinCodeWithKeyboardContent(
        modifier = modifier,
        pinCode = pinCode,
        onSetPinCode = { newPinCode ->
            pinCode = newPinCode
        },
        onPinCodeEntered = onPinCodeEntered,
        onResetError = onResetError,
        hint = hint,
        onClickHint = onClickHint,
        hasBiometric = hasBiometric,
        onPressBiometric = onPressBiometric,
        error = error,
    )
}

@Composable
private fun PinCodeWithKeyboardContent(
    pinCode: List<Int>,
    onSetPinCode: (pinCode: List<Int>) -> Unit,
    onPinCodeEntered: (pinCode: List<Int>) -> Unit,
    onResetError: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    onClickHint: (() -> Unit)? = null,
    hasBiometric: Boolean = false,
    onPressBiometric: (() -> Unit)? = null,
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
            onErrorAnimationFinished = {
                onSetPinCode(listOf())
                onResetError()
            },
        )
        Spacer(modifier = Modifier.weight(1f))
        if (hint != null) {
            TextButton(onClick = { onClickHint?.invoke() }) {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.actionTertiaryDefaultText(),
                )
            }
        }
        Keyboard(
            onPressNumber = { number ->
                onSetPinCode(
                    pinCode.toMutableList().also { list -> list.add(number) },
                )
                if (pinCode.size == 4) {
                    onPinCodeEntered(pinCode)
                }
            },
            onPressBackspace = {
                if (pinCode.isNotEmpty()) {
                    onSetPinCode(
                        pinCode.toMutableList().also { list -> list.removeAt(list.size - 1) },
                    )
                }
            },
            hasBiometric = hasBiometric,
            onPressBiometric = onPressBiometric,
        )
    }
}

@PreviewLightDark
@Composable
internal fun PinCodeWithKeyboardPreview() {
    MgoTheme {
        PinCodeWithKeyboardContent(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
            pinCode = listOf(1, 2, 3),
            onSetPinCode = {},
            onPinCodeEntered = {},
            onResetError = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun PinCodeWithKeyboardAndHintPreview() {
    MgoTheme {
        PinCodeWithKeyboardContent(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
            pinCode = listOf(1, 2, 3),
            onSetPinCode = {},
            onPinCodeEntered = {},
            onResetError = {},
            hint = "Klik hier",
        )
    }
}
