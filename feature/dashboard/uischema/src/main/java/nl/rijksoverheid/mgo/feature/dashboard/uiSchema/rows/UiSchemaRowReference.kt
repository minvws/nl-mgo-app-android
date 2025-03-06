package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaRow

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
    Text(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onClick(row) }
                .padding(16.dp),
        text = row.value,
        style = MaterialTheme.typography.bodySmall,
    )
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowReferencePreview() {
    MgoTheme {
        UiSchemaRowReference(
            row = UISchemaRow.Reference(heading = "Heading", value = "Value", referenceId = "1"),
            onClick = {},
        )
    }
}
