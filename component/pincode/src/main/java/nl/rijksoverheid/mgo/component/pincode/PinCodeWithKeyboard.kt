package nl.rijksoverheid.mgo.component.pincode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.pincode.keyboard.Keyboard
import nl.rijksoverheid.mgo.component.pincode.pincode.PinCode
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.accessibilityAnnounce
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.notificationError
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PinCodeWithKeyboard(
    onPinCodeEntered: (pinCode: List<Int>) -> Unit,
    onResetError: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    onClickHint: (() -> Unit)? = null,
    hasBiometric: Boolean = false,
    onPressBiometric: (() -> Unit)? = null,
    error: String? = null,
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
    error: String? = null,
) {
    val context = LocalContext.current
    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        PinCode(
            modifier = Modifier.padding(vertical = 32.dp),
            pinCode = pinCode,
            error = error != null,
        )
        PinCodeError(modifier = Modifier.alpha(if (error == null) 0f else 1f), error = error)
        Spacer(modifier = Modifier.weight(1f))
        if (hint != null) {
            TextButton(onClick = { onClickHint?.invoke() }) {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                )
            }
        }

        val accessibilityAnnouncePinCodeAdded =
            stringResource(
                CopyR.string.pincode_voiceover,
                (pinCode.size + 1).toString(),
                "5",
                stringResource(id = CopyR.string.pincode_filled_voiceover),
            )

        val accessibilityAnnouncePinCodeRemoved =
            stringResource(
                CopyR.string.pincode_voiceover,
                (pinCode.size).toString(),
                "5",
                stringResource(id = CopyR.string.pincode_empty_voiceover),
            )

        Keyboard(
            onPressNumber = { number ->
                if (error != null) {
                    // When there is a error showing and the user presses the keyboard, clear the error state
                    onSetPinCode(listOf())
                    onResetError()
                } else {
                    // Announce that pin code has changed
                    context.accessibilityAnnounce(accessibilityAnnouncePinCodeAdded)

                    val newPinCode = pinCode.toMutableList().also { list -> list.add(number) }
                    onSetPinCode(newPinCode)
                    if (newPinCode.size == 5) {
                        onPinCodeEntered(newPinCode)
                    }
                }
            },
            onPressBackspace = {
                if (error != null) {
                    // When there is a error showing and the user presses the keyboard, clear the error state
                    onSetPinCode(listOf())
                    onResetError()
                } else {
                    // Announce that the pin code has changed
                    context.accessibilityAnnounce(accessibilityAnnouncePinCodeRemoved)

                    if (pinCode.isNotEmpty()) {
                        onSetPinCode(
                            pinCode.toMutableList().also { list -> list.removeAt(list.size - 1) },
                        )
                    }
                }
            },
            hasBiometric = hasBiometric,
            onPressBiometric = onPressBiometric,
        )
    }
}

@Composable
private fun PinCodeError(
    error: String?,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.notificationError()) {
        Row(modifier = modifier) {
            Icon(painterResource(id = R.drawable.ic_error), contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = error ?: "",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.notificationError(),
            )
        }
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
internal fun PinCodeWithKeyboardErrorPreview() {
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
            error = "Dit is een foutmelding",
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
