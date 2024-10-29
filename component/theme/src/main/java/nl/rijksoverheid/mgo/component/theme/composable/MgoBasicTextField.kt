package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.R
import nl.rijksoverheid.mgo.component.theme.fonts

@Composable
fun MgoBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    header: String? = null,
    error: String? = null,
    textFieldTestTag: String? = null,
) {
    var isFocused by remember { mutableStateOf(false) }
    MgoBasicTextFieldContent(
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        value = value,
        onValueChange = onValueChange,
        onFocusChange = { focus ->
            isFocused = focus
        },
        hasFocus = isFocused,
        header = header,
        error = error,
        textFieldTestTag = textFieldTestTag,
    )
}

@Composable
fun MgoBasicTextFieldContent(
    value: String,
    onValueChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    hasFocus: Boolean,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    header: String? = null,
    error: String? = null,
    textFieldTestTag: String? = null,
) {
    val localContentColor = if (error == null) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error
    val localStyle = LocalTextStyle.current
    val mergedStyle = localStyle.merge(TextStyle(color = LocalContentColor.current, fontFamily = fonts))
    CompositionLocalProvider(LocalContentColor provides localContentColor) {
        Column(modifier = modifier) {
            if (header != null) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = header,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            val border = if (hasFocus) 2.dp else 1.dp
            BasicTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .then(if (textFieldTestTag == null) Modifier else Modifier.testTag(textFieldTestTag))
                        .onFocusChanged { state -> onFocusChange(state.hasFocus) },
                value = value,
                onValueChange = onValueChange,
                textStyle = mergedStyle,
                singleLine = true,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                cursorBrush = SolidColor(LocalContentColor.current),
                decorationBox = { innerTextField ->
                    Row(
                        modifier =
                            Modifier
                                .heightIn(40.dp)
                                .border(
                                    BorderStroke(border, SolidColor(LocalContentColor.current)),
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = { innerTextField() },
                    )
                },
            )

            if (error != null) {
                Error(modifier = Modifier.padding(top = 8.dp), text = error)
            }
        }
    }
}

@Composable
private fun Error(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(modifier = modifier) {
        Icon(painter = painterResource(id = R.drawable.ic_input_error), contentDescription = null)
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoBasicTextFieldEmptyPreview() {
    MgoTheme {
        MgoBasicTextFieldContent(
            modifier =
                Modifier
                    .width(300.dp)
                    .padding(16.dp),
            hasFocus = false,
            onFocusChange = {},
            value = "",
            header = "Naam (verplicht)",
            onValueChange = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoBasicTextFieldFocussedPreview() {
    MgoTheme {
        MgoBasicTextFieldContent(
            modifier =
                Modifier
                    .width(300.dp)
                    .padding(16.dp),
            hasFocus = true,
            onFocusChange = {},
            value = "",
            header = "Naam (verplicht)",
            onValueChange = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoBasicTextFieldFilledPreview() {
    MgoTheme {
        MgoBasicTextFieldContent(
            modifier =
                Modifier
                    .width(300.dp)
                    .padding(16.dp),
            hasFocus = false,
            onFocusChange = {},
            value = "Jan Jansen",
            header = "Naam (verplicht)",
            onValueChange = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoBasicTextFieldErrorPreview() {
    MgoTheme {
        MgoBasicTextFieldContent(
            modifier =
                Modifier
                    .width(300.dp)
                    .padding(16.dp),
            hasFocus = false,
            onFocusChange = {},
            value = "",
            header = "Naam (verplicht)",
            error = "Vul een naam in",
            onValueChange = {},
        )
    }
}
