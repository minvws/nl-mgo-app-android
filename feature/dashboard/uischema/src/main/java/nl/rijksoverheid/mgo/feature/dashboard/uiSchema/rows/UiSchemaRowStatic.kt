package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.data.healthcare.models.UISchemaRow

/**
 * Composable that shows a list item that displays some data.
 *
 * @param row The [UISchemaRow.Static].
 * @param modifier The [Modifier] to be applied.
 */
@Composable
internal fun UiSchemaRowStatic(
  row: UISchemaRow.Static,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier.fillMaxWidth()) {
    val heading = row.heading
    if (heading != null) {
      Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        text = heading,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.contentSecondary(),
      )
    }
    SelectionContainer {
      Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
        text = row.value,
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowStaticPreview() {
  MgoTheme {
    UiSchemaRowStatic(
      row = UISchemaRow.Static(heading = "Heading", value = "Value"),
    )
  }
}
