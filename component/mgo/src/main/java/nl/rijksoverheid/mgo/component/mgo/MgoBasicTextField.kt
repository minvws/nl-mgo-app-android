package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.fonts
import nl.rijksoverheid.mgo.component.theme.symbolsSecondary
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows text field for text input.
 * @param value The current text inside the text field.
 * @param onValueChange Called when the text is changed inside the text field.
 * @param modifier the [Modifier] to be applied.
 * @param keyboardOptions See [keyboardOptions] in [BasicTextField].
 * @param keyboardActions See [keyboardActions] in [BasicTextField].
 * @param heading Heading that shows above the text field.
 * @param error Error that shows below the text field.
 * @param textFieldTestTag A tag attached to the [BasicTextField] for testing purposes. Since the [BasicTextField]
 * is nested inside this composable, you can set it here.
 **/
@Composable
fun MgoBasicTextField(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  heading: String? = null,
  error: String? = null,
  textFieldTestTag: String? = null,
) {
  MgoBasicTextFieldContent(
    modifier = modifier,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    value = value,
    onValueChange = onValueChange,
    header = heading,
    error = error,
    textFieldTestTag = textFieldTestTag,
  )
}

@Composable
fun MgoBasicTextFieldContent(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  header: String? = null,
  error: String? = null,
  textFieldTestTag: String? = null,
) {
  val localContentColor = if (error == null) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error
  val localStyle = LocalTextStyle.current
  val mergedStyle =
    localStyle.merge(
      TextStyle(color = LocalContentColor.current, fontFamily = fonts),
    )
  CompositionLocalProvider(LocalContentColor provides localContentColor) {
    Column(modifier = modifier) {
      if (header != null) {
        Text(
          modifier = Modifier.padding(bottom = 8.dp),
          text = header,
          color = MaterialTheme.colorScheme.onBackground,
          style = MaterialTheme.typography.bodyMedium,
        )
      }
      BasicTextField(
        modifier =
          Modifier
            .fillMaxWidth()
            .then(
              if (textFieldTestTag == null) {
                Modifier
              } else {
                Modifier.testTag(
                  textFieldTestTag,
                )
              },
            ),
        value = value,
        onValueChange = onValueChange,
        textStyle = mergedStyle,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(LocalContentColor.current),
        decorationBox = { innerTextField ->
          MgoCard {
            Row(
              modifier = Modifier.padding(start = 16.dp),
              verticalAlignment = Alignment.CenterVertically,
            ) {
              Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart,
              ) {
                innerTextField()
              }
              IconButton(
                modifier = Modifier.alpha(if (value.isEmpty()) 0f else 1f),
                onClick = { onValueChange("") },
              ) {
                Icon(
                  painter = painterResource(R.drawable.ic_clear),
                  contentDescription = stringResource(CopyR.string.common_clear),
                  tint = MaterialTheme.colorScheme.symbolsSecondary(),
                )
              }
            }
          }
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
      style = MaterialTheme.typography.bodyMedium,
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
      value = "",
      header = "Naam (verplicht)",
      error = "Vul een naam in",
      onValueChange = {},
    )
  }
}
