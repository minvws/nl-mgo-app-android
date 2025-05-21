package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.symbolsPrimary

/**
 * Composable that shows a check box. Same as a [Checkbox] but with some specific values set.
 * @param checked If the check box is checked.
 * @param onCheckedChange Called if the check box is clicked.
 * @param modifier the [Modifier] to be applied.
 */
@Composable
fun MgoCheckbox(
  checked: Boolean,
  onCheckedChange: (checked: Boolean) -> Unit,
  modifier: Modifier = Modifier,
) {
  val checkedColor = MaterialTheme.colorScheme.interactiveTertiaryDefaultText()
  val unCheckedColor = MaterialTheme.colorScheme.symbolsPrimary()
  val checkMarkColor = MaterialTheme.colorScheme.backgroundSecondary()
  Checkbox(
    modifier = modifier,
    checked = checked,
    onCheckedChange = onCheckedChange,
    colors =
      CheckboxDefaults.colors().copy(
        checkedBoxColor = checkedColor,
        checkedBorderColor = checkedColor,
        checkedCheckmarkColor = checkMarkColor,
        uncheckedBorderColor = unCheckedColor,
      ),
  )
}

@PreviewLightDark
@Composable
internal fun MgoCheckboxCheckedPreview() {
  MgoTheme {
    MgoCheckbox(checked = true, onCheckedChange = {})
  }
}

@PreviewLightDark
@Composable
internal fun MgoCheckboxUnCheckedPreview() {
  MgoTheme {
    MgoCheckbox(checked = false, onCheckedChange = {})
  }
}
