package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.BackgroundsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SymbolsPrimary

@Composable
fun MgoCheckbox(
  checked: Boolean,
  onCheckedChange: (checked: Boolean) -> Unit,
  modifier: Modifier = Modifier,
) {
  val checkedColor = MaterialTheme.colorScheme.ActionsGhostText()
  val unCheckedColor = MaterialTheme.colorScheme.SymbolsPrimary()
  val checkMarkColor = MaterialTheme.colorScheme.BackgroundsSecondary()
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
