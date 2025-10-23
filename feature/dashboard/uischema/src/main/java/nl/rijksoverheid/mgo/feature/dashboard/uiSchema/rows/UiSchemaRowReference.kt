package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SymbolsSecondary
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRow
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun UiSchemaRowReference(
  row: UISchemaRow.Reference,
  onClick: (reference: UISchemaRow.Reference) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(modifier = Modifier.clickable { onClick(row) }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
    Column(
      modifier = modifier.weight(1f),
      verticalArrangement = Arrangement.Center,
    ) {
      val heading = row.heading
      if (heading != null) {
        Text(
          text = heading,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
        )
      }
      Text(
        modifier = Modifier.padding(top = 4.dp),
        text = row.value,
        style = MaterialTheme.typography.bodyMedium,
      )
    }

    Icon(
      imageVector = Icons.Rounded.ChevronRight,
      contentDescription = stringResource(CopyR.string.common_next),
      tint = MaterialTheme.colorScheme.SymbolsSecondary(),
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowReferenceWithHeadingPreview() {
  MgoTheme {
    UiSchemaRowReference(
      row = UISchemaRow.Reference(heading = "Heading", value = "Value", referenceId = "1"),
      onClick = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowReferenceWithoutHeadingPreview() {
  MgoTheme {
    UiSchemaRowReference(
      row = UISchemaRow.Reference(heading = null, value = "Value", referenceId = "1"),
      onClick = {},
    )
  }
}
