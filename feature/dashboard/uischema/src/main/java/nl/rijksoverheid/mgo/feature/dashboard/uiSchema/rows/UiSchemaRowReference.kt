package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.symbolsSecondary
import nl.rijksoverheid.mgo.data.healthcare.models.UISchemaRow
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a list item that represents a reference to another screen with health care data.
 *
 * @param row The [UISchemaRow.Reference].
 * @param onClick Called when is requested to open the reference.
 * @param modifier The [Modifier] to be applied.
 */
@Composable
internal fun UiSchemaRowReference(
  row: UISchemaRow.Reference,
  onClick: (reference: UISchemaRow.Reference) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(modifier = Modifier.clickable { onClick(row) }, verticalAlignment = Alignment.CenterVertically) {
    Column(
      modifier = modifier.weight(1f).padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
      verticalArrangement = Arrangement.Center,
    ) {
      val heading = row.heading
      if (heading != null) {
        Text(
          text = heading,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.contentSecondary(),
        )
      }
      Text(
        modifier = Modifier.padding(top = 4.dp),
        text = row.value,
        style = MaterialTheme.typography.bodyMedium,
      )
    }

    IconButton(onClick = {}) {
      Icon(
        painter = painterResource(R.drawable.ic_chevron),
        contentDescription = stringResource(CopyR.string.common_next),
        tint = MaterialTheme.colorScheme.symbolsSecondary(),
      )
    }
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
