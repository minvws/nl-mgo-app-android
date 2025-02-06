package nl.rijksoverheid.mgo.component.pincode

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import nl.rijksoverheid.mgo.component.pincode.keyboard.Keyboard
import nl.rijksoverheid.mgo.component.pincode.pincode.PinCode
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.notificationError
import nl.rijksoverheid.mgo.framework.util.accessibilityAnnounce
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

    // Announce error if needed
    LaunchedEffect(error) {
        if (error != null) {
            context.accessibilityAnnounce(error)
        }
    }

    Column(
        modifier =
            modifier
                .padding(bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ConstraintLayout(modifier = Modifier.weight(1f)) {
            val (pinCodeCircles, errorText) = createRefs()
            PinCode(
                modifier =
                    Modifier
                        .padding(vertical = 32.dp)
                        .constrainAs(pinCodeCircles) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                pinCode = pinCode,
                error = error != null,
            )
            if (error != null) {
                PinCodeError(
                    modifier =
                        Modifier
                            .padding(bottom = 32.dp)
                            .constrainAs(errorText) {
                                top.linkTo(pinCodeCircles.bottom, margin = 8.dp)
                                start.linkTo(pinCodeCircles.start)
                                end.linkTo(pinCodeCircles.end)
                            },
                    error = error,
                )
            }
        }
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

        Keyboard(
            onPressNumber = { number ->
                if (error != null) {
                    // When there is a error showing and the user presses the keyboard, clear the error state
                    onSetPinCode(listOf(number))
                    onResetError()

                    // Announce that pin code has changed
                    context.accessibilityAnnounce(context.accessibilityStringPinCodeAdded(1))
                } else {
                    val newPinCode = pinCode.toMutableList().also { list -> list.add(number) }
                    onSetPinCode(newPinCode)
                    if (newPinCode.size == 5) {
                        onPinCodeEntered(newPinCode)
                    }

                    // Announce that pin code has changed
                    context.accessibilityAnnounce(context.accessibilityStringPinCodeAdded(newPinCode.size))
                }
            },
            onPressBackspace = {
                if (error != null) {
                    // When there is a error showing and the user presses the keyboard, clear the error state
                    onSetPinCode(listOf())
                    onResetError()

                    // Announce that pin code has changed
                    context.accessibilityAnnounce(context.accessibilityStringPinCodeRemoved(0))
                } else {
                    if (pinCode.isNotEmpty()) {
                        onSetPinCode(
                            pinCode.toMutableList().also { list -> list.removeAt(list.size - 1) },
                        )
                    }

                    // Announce that pin code has changed
                    context.accessibilityAnnounce(context.accessibilityStringPinCodeRemoved(pinCode.size))
                }
            },
            hasBiometric = hasBiometric,
            onPressBiometric = onPressBiometric,
            showBackSpace = pinCode.isNotEmpty(),
        )
    }
}

@Composable
private fun PinCodeError(
    error: String?,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.notificationError()) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(id = R.drawable.ic_error), contentDescription = null)
            Text(
                modifier =
                    Modifier
                        .wrapContentWidth()
                        .padding(start = 6.dp),
                text = error ?: "",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.notificationError(),
            )
        }
    }
}

private fun Context.accessibilityStringPinCodeAdded(pinCodeSize: Int): String {
    return getString(CopyR.string.pincode_voiceover, pinCodeSize.toString(), "5", getString(CopyR.string.pincode_filled_voiceover))
}

private fun Context.accessibilityStringPinCodeRemoved(pinCodeSize: Int): String {
    return getString(CopyR.string.pincode_voiceover, pinCodeSize.toString(), "5", getString(CopyR.string.pincode_empty_voiceover))
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
