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
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRow
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRowStaticValue

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
        color = MaterialTheme.colorScheme.LabelsSecondary(),
      )
    }
    row.value.forEach { value ->
      SelectionContainer {
        Text(
          modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
          text = value.value,
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowStaticSingleValuePreview() {
  MgoTheme {
    UiSchemaRowStatic(
      row = UISchemaRow.Static(heading = "Heading", value = listOf(UISchemaRowStaticValue("Value"))),
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowStaticMultipleValuePreview() {
  MgoTheme {
    UiSchemaRowStatic(
      row = UISchemaRow.Static(heading = "Heading", value = listOf(UISchemaRowStaticValue("Value 1"), UISchemaRowStaticValue("Value 2", snomedCode = "123"))),
    )
  }
}
