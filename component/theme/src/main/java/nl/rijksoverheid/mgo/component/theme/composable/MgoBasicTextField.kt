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
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.R
import nl.rijksoverheid.mgo.component.theme.bodySmall

@Composable
fun MgoBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    header: String? = null,
    error: String? = null,
) {
    val localContentColor = if (error == null) MaterialTheme.colors.onBackground else MaterialTheme.colors.error
    val localStyle = LocalTextStyle.current
    val mergedStyle = localStyle.merge(TextStyle(color = LocalContentColor.current))
    CompositionLocalProvider(LocalContentColor provides localContentColor) {
        Column(modifier = modifier) {
            if (header != null) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = header,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                textStyle = mergedStyle,
                cursorBrush = SolidColor(LocalContentColor.current),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        modifier =
                            Modifier
                                .heightIn(40.dp)
                                .border(
                                    BorderStroke(1.dp, SolidColor(LocalContentColor.current)),
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
        MgoBasicTextField(
            value = "",
            header = "Naam (verplicht)",
            onValueChange = {},
            modifier = Modifier.width(300.dp).padding(16.dp),
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoBasicTextFieldFilledPreview() {
    MgoTheme {
        MgoBasicTextField(
            value = "Jan Jansen",
            header = "Naam (verplicht)",
            onValueChange = {},
            modifier = Modifier.width(300.dp).padding(16.dp),
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoBasicTextFieldErrorPreview() {
    MgoTheme {
        MgoBasicTextField(
            value = "",
            header = "Naam (verplicht)",
            error = "Vul een naam in",
            onValueChange = {},
            modifier = Modifier.width(300.dp).padding(16.dp),
        )
    }
}
